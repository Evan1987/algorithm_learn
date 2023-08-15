#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2023/7/26 16:43
# @author  : zhaochengming
# https://github.com/busesese/MultiTaskModel/blob/main/mmoe.py

import torch
import torch.nn as nn
from typing import *


class MMoE(nn.Module):
    """Multi-gate Mixture-of-Experts for CT-CVR problem"""

    def __init__(self, *, user_feature_dict: Dict[str, Tuple], item_feature_dict: Dict[str, Tuple],
                 embedding_length: int = 128, n_experts: int = 3, n_tasks: int = 2, units: int = 128,
                 use_expert_bias: bool = True, use_gate_bias: bool = True,
                 tower_hidden_sizes: Tuple = (128, 64), output_size: int = 1, dropout: float = 0.5):
        """
        input parameters to build MMoE model
        :param user_feature_dict: {feature_name: (feature_unique_num, feature_index)}
        :param item_feature_dict: {feature_name: (feature_unique_num, feature_index)}
        :param embedding_length: the embedding vector's length
        :param n_experts: num of expert nets in MMoE
        :param n_tasks: num of tasks
        :param units: mmoe layer input dimension
        :param use_expert_bias: Whether to use bias in expert weights
        :param use_gate_bias: Whether to use bias in gate weights
        :param tower_hidden_sizes: `tuple` for task tower hidden size
        :param output_size: task output size
        :param dropout: DNN dropout prob
        """
        super(MMoE, self).__init__()
        assert user_feature_dict and item_feature_dict, "Empty features"

        self.user_feature_dict = user_feature_dict
        self.item_feature_dict = item_feature_dict
        self.n_tasks = n_tasks
        self.use_expert_bias = use_expert_bias
        self.use_gate_bias = use_gate_bias

        # 共享embedding特征层，按特征逐个设置embedding层
        embed_feature_count = 0
        side_feature_count = 0
        for feature, (feature_num, _) in self.user_feature_dict.items():
            if feature_num > 1:
                embed_feature_count += 1
                self.add_module(feature, nn.Embedding(feature_num, embedding_length))
            else:
                side_feature_count += 1

        for feature, (feature_num, _) in self.item_feature_dict.items():
            if feature_num > 1:
                embed_feature_count += 1
                self.add_module(feature, nn.Embedding(feature_num, embedding_length))
            else:
                side_feature_count += 1

        # 特征总数
        feature_size = embedding_length * embed_feature_count + side_feature_count

        # experts专家层 [F, H, N_E]
        self.expert_kernels = self.create_parameter(feature_size, units, n_experts)
        # [H, N_E]
        self.expert_bias = self.create_parameter(units, n_experts) if self.use_expert_bias else None

        # gates门限层 与 task数量有关 [H, N_E] * N_T
        self.gate_kernels = [self.create_parameter(units, n_experts) for _ in range(n_tasks)]

        # [N_E,] * N_T
        self.gate_bias = [nn.Parameter(torch.randn(n_experts), requires_grad=True) for _ in range(n_tasks)] \
            if self.use_gate_bias else []

        # esmm 中的 CTR CTCVR独立tower
        hid_dims = [units, *tower_hidden_sizes]
        for i in range(self.n_tasks):
            name = f"tower_{i + 1}"
            self.add_module(name, self.set_tower(name, units, tower_hidden_sizes, output_size, dropout))

    @staticmethod
    def set_tower(name: str, input_dim: int, hidden_dims: Tuple, output_size: int, dropout: float) -> nn.ModuleList:
        """
        build the task-tower
        input_dim ->
         linear -> hidden_dims[0] -> BN -> Dropout ->
         linear -> hidden_dims[1] ....  -> Dropout ->
         linear -> hidden_dims[-1] -> BN -> Dropout ->
         linear -> output
        """
        tower = nn.ModuleList()
        dims = [input_dim, *hidden_dims]
        for i in range(len(dims) - 1):
            tower.add_module(f"{name}_dnn_{i}", nn.Linear(dims[i], dims[i + 1]))
            tower.add_module(f"{name}_bn_{i}", nn.BatchNorm1d(dims[i + 1]))
            tower.add_module(f"{name}_dropout_{i}", nn.Dropout(dropout))
        tower.add_module(f"{name}_last", nn.Linear(dims[-1], output_size))
        return tower

    @staticmethod
    def create_parameter(*shape):
        p = nn.Parameter(torch.empty(shape), requires_grad=True)
        nn.init.normal_(p, 0.0, 1.0)
        return p


