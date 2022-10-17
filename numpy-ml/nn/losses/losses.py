#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2022/10/17 13:57
# @author  : zhaochengming

import numpy as np
from abc import ABC, abstractmethod


class ObjectiveBase(ABC):

    def __call__(self, y_true: np.ndarray, y_pred: np.ndarray):
        return self.loss(y_true, y_pred)

    @abstractmethod
    def loss(self, y_true: np.ndarray, y_pred: np.ndarray, **kwargs) -> float:
        pass

    @abstractmethod
    def grad(self, y_true: np.ndarray, y_pred: np.ndarray, *args, **kwargs) -> np.ndarray:
        pass


class MeanSquaredError(ObjectiveBase):
    """L2 loss"""

    def loss(self, y_true, y_pred, **kwargs):
        return 0.5 * ((y_true - y_pred) ** 2).mean()

    def grad(self, y_true, y_pred, z, act_fn, **kwargs):
        return (y_pred - y_true) * act_fn.fn(z)

