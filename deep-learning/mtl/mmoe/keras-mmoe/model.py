#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2023/7/26 19:09
# @author  : zhaochengming

import tensorflow as tf
from tensorflow.keras import backend as K
from tensorflow.keras import activations, initializers, regularizers, constraints
from tensorflow.keras.layers import Layer, InputSpec


class MMoE(Layer):

    def __init__(self, units: int, n_experts: int, n_tasks: int,
                 use_expert_bias: bool = True,
                 use_gate_bias: bool = True,
                 expert_activation='relu',
                 gate_activation='softmax',
                 expert_bias_initializer='zeros',
                 gate_bias_initializer='zeros',
                 expert_bias_regularizer=None,
                 gate_bias_regularizer=None,
                 expert_bias_constraint=None,
                 gate_bias_constraint=None,
                 expert_kernel_initializer='VarianceScaling',
                 gate_kernel_initializer='VarianceScaling',
                 expert_kernel_regularizer=None,
                 gate_kernel_regularizer=None,
                 expert_kernel_constraint=None,
                 gate_kernel_constraint=None,
                 activity_regularizer=None,
                 **kwargs):
        """
        Method for instantiating MMoE layer
        :param units: Num of hidden units
        :param n_experts: Num of experts
        :param n_tasks: Num of tasks
        :param use_expert_bias: Whether to use bias in expert weights
        :param use_gate_bias: Whether to use bias in gate weights
        :param expert_activation: Activation function of the expert weights
        :param gate_activation: Activation function of the gate weights
        :param expert_bias_initializer: Initializer for the expert bias
        :param gate_bias_initializer: Initializer for the gate bias
        :param expert_bias_regularizer: Regularizer for the expert bias
        :param gate_bias_regularizer: Regularizer for the gate bias
        :param expert_bias_constraint: Constraint for the expert bias
        :param gate_bias_constraint: Constraint for the gate bias
        :param expert_kernel_initializer: Initializer for the expert weights
        :param gate_kernel_initializer: Initializer for the gate weights
        :param expert_kernel_regularizer: Regularizer for the expert weights
        :param gate_kernel_regularizer: Regularizer for the gate weights
        :param expert_kernel_constraint: Constraint for the expert weights
        :param gate_kernel_constraint: Constraint for the gate weights
        :param activity_regularizer: Regularizer for the activity
        """
        super(MMoE, self).__init__(**kwargs)
        # Hidden nodes parameter
        self.units = units
        self.n_experts = n_experts
        self.n_tasks = n_tasks

        # Weight parameter
        self.expert_kernels = None
        self.expert_kernel_initializer = initializers.get(expert_kernel_initializer)
        self.expert_kernel_regularizer = regularizers.get(expert_kernel_regularizer)
        self.expert_kernel_constraint = constraints.get(expert_kernel_constraint)

        self.gate_kernels = None
        self.gate_kernel_initializer = initializers.get(gate_kernel_initializer)
        self.gate_kernel_regularizer = regularizers.get(gate_kernel_regularizer)
        self.gate_kernel_constraint = constraints.get(gate_kernel_constraint)

        # Activation parameter
        self.expert_activation = activations.get(expert_activation)
        self.gate_activation = activations.get(gate_activation)

        # Bias parameter
        self.expert_bias = None
        self.use_expert_bias = use_expert_bias
        self.expert_bias_initializer = initializers.get(expert_bias_initializer)
        self.expert_bias_regularizer = regularizers.get(expert_bias_regularizer)
        self.expert_bias_constraint = constraints.get(expert_bias_constraint)

        self.gate_bias = None
        self.use_gate_bias = use_gate_bias
        self.gate_bias_initializer = initializers.get(gate_bias_initializer)
        self.gate_bias_regularizer = regularizers.get(gate_bias_regularizer)
        self.gate_bias_constraint = constraints.get(gate_bias_constraint)

        # Activity parameter
        self.activity_regularizer = activity_regularizer

        # Keras parameter
        self.input_spec = None
        self.supports_masking = True

    def build(self, input_shape):
        assert input_shape is not None and len(input_shape) >= 2
        input_dimension = input_shape[-1]

        # init expert weights [F, H, N_E]
        self.expert_kernels = self.add_weight(
            name="expert_kernel",
            shape=(input_dimension, self.units, self.n_experts),
            initializer=self.expert_kernel_initializer,
            regularizer=self.expert_kernel_regularizer,
            constraint=self.expert_kernel_constraint,
        )

        # init expert bias [H, N_E]
        if self.use_expert_bias:
            self.expert_bias = self.add_weight(
                name="expert_bias",
                shape=(self.units, self.n_experts),
                initializer=self.expert_bias_initializer,
                regularizer=self.expert_bias_regularizer,
                constraint=self.expert_bias_constraint,
            )

        # init gate weights [F, N_E] * N_T
        self.gate_kernels = [self.add_weight(
            name=f"gate_kernel_task_{i}",
            shape=(input_dimension, self.n_experts),
            initializer=self.gate_kernel_initializer,
            regularizer=self.gate_kernel_regularizer,
            constraint=self.gate_kernel_constraint,
        ) for i in range(self.n_tasks)]

        # init gate bias [N_E, ] * N_T
        if self.use_gate_bias:
            self.gate_bias = [self.add_weight(
                name=f"gate_bias_task_{i}",
                shape=(self.n_experts,),
                initializer=self.gate_bias_initializer,
                regularizer=self.gate_bias_regularizer,
                constraint=self.gate_bias_constraint,
            ) for i in range(self.n_tasks)]

        self.input_spec = InputSpec(min_ndim=2, axes={-1: input_dimension})
        super(MMoE, self).build(input_shape)

    def call(self, inputs, *args, **kwargs):

        # Compute the expert outputs: [B, F] dot [F, H, N_E] = [B, H, N_E]
        expert_outputs = tf.tensordot(inputs, self.expert_kernels, axes=1)   # eq axes=[[1], [0]]
        if self.use_expert_bias:
            expert_outputs = K.bias_add(expert_outputs, self.expert_bias)
        expert_outputs = self.expert_activation(expert_outputs)

        # Compute the gate outputs: [B, N_E] * N_T
        gate_outputs = []
        for i in range(self.n_tasks):
            # [B, F] . [F, N_E] = [B, N_E]
            gate_output = K.dot(inputs, self.gate_kernels[i])
            if self.use_gate_bias:
                gate_output = K.bias_add(gate_output, self.gate_bias[i])
            gate_output = self.gate_activation(gate_output)
            gate_outputs.append(gate_output)

        # Compute the final outputs: [B, H] * N_T
        final_outputs = []
        for gate_output in gate_outputs:
            # [B, N_E] -> [B, 1, N_E]
            gate_output = K.expand_dims(gate_output, axis=1)
            # [B, H, N_E] * [B, 1, N_E] = [B, H, N_E]
            weighted_expert_output = expert_outputs * gate_output  # K.repeat_elements(gate_output, self.units, axis=1)
            # [B, H, N_E] -> [B, H]
            final_output = K.sum(weighted_expert_output, axis=-1)
            final_outputs.append(final_output)

        return final_outputs

    def compute_output_shape(self, input_shape):
        assert input_shape is not None and len(input_shape) >= 2

        output_shape = list(input_shape)
        output_shape[-1] = self.units
        output_shape = tuple(output_shape)

        return [output_shape for _ in range(self.n_tasks)]

    def get_config(self):

        config = {
            ** super(MMoE, self).get_config(),
            'units': self.units,
            'num_experts': self.num_experts,
            'num_tasks': self.num_tasks,
            'use_expert_bias': self.use_expert_bias,
            'use_gate_bias': self.use_gate_bias,
            'expert_activation': activations.serialize(self.expert_activation),
            'gate_activation': activations.serialize(self.gate_activation),
            'expert_bias_initializer': initializers.serialize(self.expert_bias_initializer),
            'gate_bias_initializer': initializers.serialize(self.gate_bias_initializer),
            'expert_bias_regularizer': regularizers.serialize(self.expert_bias_regularizer),
            'gate_bias_regularizer': regularizers.serialize(self.gate_bias_regularizer),
            'expert_bias_constraint': constraints.serialize(self.expert_bias_constraint),
            'gate_bias_constraint': constraints.serialize(self.gate_bias_constraint),
            'expert_kernel_initializer': initializers.serialize(self.expert_kernel_initializer),
            'gate_kernel_initializer': initializers.serialize(self.gate_kernel_initializer),
            'expert_kernel_regularizer': regularizers.serialize(self.expert_kernel_regularizer),
            'gate_kernel_regularizer': regularizers.serialize(self.gate_kernel_regularizer),
            'expert_kernel_constraint': constraints.serialize(self.expert_kernel_constraint),
            'gate_kernel_constraint': constraints.serialize(self.gate_kernel_constraint),
            'activity_regularizer': regularizers.serialize(self.activity_regularizer)
        }

        return config

