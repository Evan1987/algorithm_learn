#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2022/10/17 14:26
# @author  : zhaochengming

import numpy as np
from copy import deepcopy
from ..schedulers import SchedulerBase
from abc import ABC, abstractmethod
from typing import *


class OptimizerBase(ABC):

    def __init__(self, lr: float, scheduler: SchedulerBase):
        self.cache = {}
        self.lr = lr
        self.cur_step = 0
        self.hyper_parameters = {}
        self.scheduler = scheduler

    def step(self):
        self.cur_step += 1

    def reset_step(self):
        self.cur_step = 0

    def copy(self):
        return deepcopy(self)

    def set_params(self, hyper_dict: Dict, cache_dict: Dict):
        if hyper_dict:
            for k, v in hyper_dict.items():
                if k in self.hyper_parameters:
                    self.hyper_parameters[k] = v
                    if k == "scheduler":
                        self.scheduler = v
        if cache_dict:
            for k, v in cache_dict.items():
                if k in self.cache:
                    self.cache[k] = v

    def __call__(self, param, param_grad, param_name, cur_loss=None):
        return self.update(param, param_grad, param_name, cur_loss)

    @abstractmethod
    def update(self, param, param_grad, param_name, cur_loss=None):
        raise NotImplementedError




