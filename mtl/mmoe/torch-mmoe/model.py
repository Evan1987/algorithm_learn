#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2023/7/26 16:43
# @author  : zhaochengming

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

        # 共享embedding特征层
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

        feature_size = embedding_length * embed_feature_count + side_feature_count

        # experts专家层 与experts数量有关 [F, H, N_E]
        self.expert_kernels = nn.Parameter(torch.empty(feature_size, units, n_experts), requires_grad=True)
        nn.init.normal_(self.expert_kernels, 0.0, 1.0)

        self.expert_bias = None
        if self.use_expert_bias:
            # [H, N_E]
            self.expert_bias = nn.Parameter(torch.empty(units, n_experts), requires_grad=True)
            nn.init.normal_(self.experts_bias, 0.0, 1.0)

        # gates门限层 与 task数量有关 [H, N_E] * N_T
        self.gate_kernels = []
        for _ in range(n_tasks):
            gate_kernel = nn.Parameter(torch.empty(units, n_experts), requires_grad=True)
            nn.init.normal_(gate_kernel, 0.0, 1.0)
            self.gate_kernels.append(gate_kernel)

        self.gate_bias = []
        if self.use_gate_bias:
            self.gate_bias = [nn.Parameter(torch.randn(n_experts), requires_grad=True) for _ in range(n_tasks)]

        # esmm 中的 CTR CTCVR独立tower
        for i in range(self.n_tasks):
            self.add_module(f"", nn.ModuleList())


