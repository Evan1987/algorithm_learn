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
                 embedding_length: int = 128, n_experts: int = 3, mmoe_hidden_size: int = 128,
                 hidden_sizes: Tuple = (128, 64), output_size: int = 1, dropout: float = 0.5, n_tasks: int = 2):
        """
        input parameters to build MMoE model
        :param user_feature_dict: {feature_name: (feature_unique_num, feature_index)}
        :param item_feature_dict: {feature_name: (feature_unique_num, feature_index)}
        :param embedding_length: the embedding vector's length
        :param n_experts: num of expert nets in MMoE
        :param mmoe_hidden_size: mmoe layer input dimension
        :param hidden_sizes: `tuple` for task tower hidden size
        :param output_size: task output size
        :param dropout: DNN dropout prob
        :param n_tasks: num of tasks
        """
        super(MMoE, self).__init__()
        assert user_feature_dict and item_feature_dict, "Empty features"
        assert n_tasks == len(hidden_sizes), "Unmatched num of tasks and task tower sizes"

        self.user_feature_dict = user_feature_dict
        self.item_feature_dict = item_feature_dict
        self.n_tasks = n_tasks

        # 共享embedding特征层
        embed_count = 0
        for feature, (feature_num, _) in self.user_feature_dict.items():
            if feature_num > 1:
                embed_count += 1
                self.add_module(feature, nn.Embedding(feature_num, embedding_length))

        for feature, (feature_num, _) in self.item_feature_dict.items():
            if feature_num > 1:
                embed_count += 1
                self.add_module(feature, nn.Embedding(feature_num, embedding_length))

        total_size = embedding_length * embed_count + len(self.user_feature_dict) + len(self.item_feature_dict) - embed_count

        # experts专家层 与experts数量有关
        self.experts = nn.Parameter(torch.empty(total_size, mmoe_hidden_size, n_experts), requires_grad=True)
        nn.init.normal_(self.experts, 0.0, 1.0)
        self.experts_bias = nn.Parameter(torch.empty(total_size, n_experts), requires_grad=True)
        nn.init.normal_(self.experts_bias, 0.0, 1.0)

        # gates门限层 与 task数量有关
        self.gates = nn.ModuleList([nn.Linear(total_size, n_experts, bias=True) for _ in range(n_tasks)])

