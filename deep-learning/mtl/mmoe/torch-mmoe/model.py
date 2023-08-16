#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2023/7/26 16:43
# @author  : zhaochengming
# https://github.com/busesese/MultiTaskModel/blob/main/mmoe.py

import torch
import torch.nn as nn
import torch.nn.functional as F
from typing import *


class MMoE(nn.Module):
    """Multi-gate Mixture-of-Experts for CT-CVR problem"""

    def __init__(self, *, feature_dict: Dict[str, Tuple[int, int]], embedding_length: int = 128,
                 n_experts: int = 3, n_tasks: int = 2, units: int = 128, expert_activation: Callable = F.relu,
                 use_expert_bias: bool = True, use_gate_bias: bool = True):
        """
        input parameters to build MMoE model
        :param feature_dict: {feature_name: (feature_unique_num, feature_index)}
        :param embedding_length: the embedding vector's length
        :param n_experts: num of expert nets in MMoE
        :param n_tasks: num of tasks
        :param units: mmoe layer output dimension
        :param expert_activation: the activation function for expert nets like 'relu' or 'sigmoid'
        :param use_expert_bias: Whether to use bias in expert weights
        :param use_gate_bias: Whether to use bias in gate weights
        """
        super(MMoE, self).__init__()
        assert feature_dict, "Empty features"

        self.feature_dict = feature_dict
        self.n_tasks = n_tasks
        self.use_expert_bias = use_expert_bias
        self.use_gate_bias = use_gate_bias
        self.expert_activation = expert_activation
        self.units = units

        # 共享embedding特征层，按特征逐个设置embedding层
        embed_feature_count = 0
        side_feature_count = 0
        for feature, (feature_num, _) in self.feature_dict.items():
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

        # gates门限层 与 task数量有关 [F, N_E] * N_T
        self.gate_kernels = [self.create_parameter(feature_size, n_experts) for _ in range(n_tasks)]

        # [N_E,] * N_T
        self.gate_bias = [nn.Parameter(torch.randn(n_experts), requires_grad=True) for _ in range(n_tasks)] \
            if self.use_gate_bias else []

    def forward(self, x: torch.Tensor) -> List[torch.Tensor]:
        """Compute the mmoe output  [B, H] * N_T """

        assert x.size(1) == len(self.feature_dict)

        # 1. embedding
        embed_list: List[torch.Tensor] = []
        for feature, (feature_num, value) in self.feature_dict.items():
            v: torch.Tensor = x[:, value]
            if feature_num > 1:
                embed_list.append(getattr(self, feature)(v.long()))
            else:
                embed_list.append(v.unsqueeze(1))

        embeds: torch.Tensor = torch.cat(embed_list, dim=1).float()   # [B, F]

        # 2 expert-out  [B, H, N_E]
        experts_out = torch.einsum('ij, jkl -> ikl', embeds, self.expert_kernels)   # [B, H, N_E]
        # experts_out = torch.tensordot(embeds, self.expert_kernels, dims=1)   # equivalent
        if self.use_expert_bias:
            experts_out += self.expert_bias
        if self.expert_activation is not None:
            experts_out = self.expert_activation(experts_out)

        # 3 gate-out   [B, N_E] * N_T
        gates_out = []
        for i in range(self.n_tasks):
            # [B, F] . [F, N_E] -> [B, N_E]
            gate_out = torch.einsum('ij, jk -> ik', embeds, self.gate_kernels[i])
            # gate_out = torch.matmul(embeds, self.gate_kernels[i])   # equivalent
            if self.use_gate_bias:
                gate_out += self.gate_bias[i]
            gate_out = F.softmax(gate_out, dim=-1)
            gates_out.append(gate_out)

        # 4 final mmoe-out   [B, H] * N_T
        outs = []
        for gate_out in gates_out:
            expanded_gate_out = gate_out[:, None, :].expand_as(experts_out)     # [B, N_E] -> [B, 1, N_E] -> [B, H, N_E]
            weighted_expert_out = experts_out * expanded_gate_out               # [B, H, N_E]
            out = torch.sum(weighted_expert_out, dim=-1)                        # [B, H]
            # out = torch.einsum('ij, ikj -> ik', gate_out, experts_out)        # equivalent
            outs.append(out)

        return outs

    @staticmethod
    def create_parameter(*shape):
        p = nn.Parameter(torch.empty(shape), requires_grad=True)
        nn.init.normal_(p, 0.0, 1.0)
        return p


class TaskTower(nn.Module):

    def __init__(self, task_names: List[str], input_dim: int, hidden_sizes: Tuple[int] = (128, 64),
                 output_size: int = 1, dropout: float = 0.5):
        """
        input parameters to build individual task-tower after mmoe
        :param task_names: name to identify each tower
        :param input_dim: the input size for each tower
        :param hidden_sizes: dnn layer for each tower
        :param output_size: output size for each tower output
        :param dropout: dropout
        """
        super().__init__()
        self.n_tasks = len(task_names)
        self.tower_names = [f"tower_{name}" for name in task_names]

        """
        build the task-tower
        input_dim ->
         linear -> hidden_dims[0] -> BN -> Dropout ->
         linear -> hidden_dims[1] ....  -> Dropout ->
         linear -> hidden_dims[-1] -> BN -> Dropout ->
         linear -> output
        """
        for name in self.tower_names:
            tower = nn.ModuleList()
            dims = [input_dim, *hidden_sizes]
            for i in range(len(dims) - 1):
                tower.add_module(f"{name}_dnn_{i}", nn.Linear(dims[i], dims[i + 1]))
                tower.add_module(f"{name}_bn_{i}", nn.BatchNorm1d(dims[i + 1]))
                tower.add_module(f"{name}_dropout_{i}", nn.Dropout(dropout))
            tower.add_module(f"{name}_last", nn.Linear(dims[-1], output_size))
            self.add_module(name, tower)

    def forward(self, inputs: List[torch.Tensor]):
        """compute tower out with mmoe outputs"""
        assert len(inputs) == self.n_tasks

        outputs = []
        for i, name in enumerate(self.tower_names):
            x = inputs[i]
            for module in getattr(self, name):
                x = module(x)
            outputs.append(x)
        return outputs


if __name__ == '__main__':
    import numpy as np
    a = torch.from_numpy(np.array([[1, 2, 4, 2, 0.5, 0.1],
                                   [4, 5, 3, 8, 0.6, 0.43],
                                   [6, 3, 2, 9, 0.12, 0.32],
                                   [9, 1, 1, 1, 0.12, 0.45],
                                   [8, 3, 1, 4, 0.21, 0.67]]))

    features = {"user_id": (11, 0), "item_id": (8, 1), "item_cate": (6, 2),
                "user_list": (12, 3), "user_num": (1, 4), "item_num": (1, 5)}

    mmoe = MMoE(feature_dict=features)
    mmoe_outs = mmoe(a)

    towers = TaskTower(["ctr", "ctcvr"], mmoe.units)
    final_outs = towers(mmoe_outs)

    model = nn.Sequential(mmoe, towers)
    print(model(a)[0].shape)
