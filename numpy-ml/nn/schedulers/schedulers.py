#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2022/10/17 14:27
# @author  : zhaochengming

import numpy as np
import math
from copy import deepcopy
from abc import ABC, abstractmethod


def gaussian_cdf(mean: float, var: float, x: float) -> float:
    eps = np.finfo(float).eps
    x_scaled = (x - mean) / np.sqrt(var + eps)
    return (1 + math.erf(x_scaled / math.sqrt(2))) / 2


class SchedulerBase(ABC):

    def __init__(self):
        self.hyper_parameters = {}

    def copy(self):
        return deepcopy(self)

    @abstractmethod
    def __str__(self) -> str:
        pass

    def set_params(self, **kwargs):
        for k, v in kwargs.items():
            if k in self.hyper_parameters:
                self.hyper_parameters[k] = v

    @abstractmethod
    def learning_rate(self, step: int, cur_loss: None):
        raise NotImplementedError

    def __call__(self, step=None, cur_loss=None):
        return self.learning_rate(step=step, cur_loss=cur_loss)


class ConstantScheduler(SchedulerBase):

    """Returns a fixed learning rate, regardless of the current step."""

    def __init__(self, lr: float = 0.01):
        super(ConstantScheduler, self).__init__()
        self.lr = lr
        self.hyper_parameters["id"] = "ConstantScheduler"
        self.hyper_parameters["lr"] = lr

    def __str__(self):
        return f"ConstantScheduler(lr={self.lr})"

    def learning_rate(self, step=None, cur_loss=None):
        return self.lr


class ExponentialScheduler(SchedulerBase):
    """An exponential learning rate scheduler."""

    def __init__(self, initial_lr: float = 0.01, stage_length: int = 500, staircase: bool = False, decay: float = 0.1):
        super(ExponentialScheduler, self).__init__()
        self.decay = decay
        self.staircase = staircase
        self.initial_lr = initial_lr
        self.stage_length = stage_length
        self.hyper_parameters["id"] = "StepScheduler"
        self.hyper_parameters["decay"] = self.decay
        self.hyper_parameters["staircase"] = self.staircase
        self.hyper_parameters["initial_lr"] = self.initial_lr
        self.hyper_parameters["stage_length"] = stage_length

    def __str__(self):
        return f"ExponentialScheduler(initial_lr={self.initial_lr}, stage_length={self.stage_length}, " \
               f"staircase={self.staircase}, decay={self.decay})"

    def learning_rate(self, step, cur_loss=None):
        cur_stage = step / self.stage_length
        if self.staircase:
            cur_stage = np.floor(cur_stage)
        return self.initial_lr * self.decay ** cur_stage


class NoamScheduler(SchedulerBase):
    """The Noam learning rate scheduler, originally used in conjunction with the Adam optimizer"""

    def __init__(self, model_dim: int = 512, scale_factor: float = 1., warmup_steps: int = 4000):
        super(NoamScheduler, self).__init__()
        self.model_dim = model_dim
        self.scale_factor = scale_factor
        self.warmup_steps = warmup_steps
        self.hyper_parameters["id"] = "NoamScheduler"
        self.hyper_parameters["model_dim"] = self.model_dim
        self.hyper_parameters["scale_factor"] = self.scale_factor
        self.hyper_parameters["warmup_steps"] = self.warmup_steps

    def __str__(self):
        return f"NoamScheduler(model_dim={self.model_dim}, scale_factor={self.scale_factor}, " \
               f"warmup_steps={self.warmup_steps})"

    def learning_rate(self, step: int, cur_loss: None):
        return self.scale_factor * (self.model_dim ** (-0.5) * min(step ** (-0.5), step * self.warmup_steps ** (-1.5)))


class KingScheduler(SchedulerBase):
    """The Davis King / DLib learning rate scheduler."""

    def __init__(self, initial_lr: float = 0.01, patience: int = 1000, decay: float = 0.99):
        super(KingScheduler, self).__init__()
        self.initial_lr = initial_lr
        self.current_lr = initial_lr
        self.patience = patience
        self.decay = decay
        self.max_history = np.ceil(1.1 * (patience + 1)).astype(int)
        self.loss_history = []

        self.hyper_parameters["id"] = "KingScheduler"
        self.hyper_parameters["decay"] = self.decay
        self.hyper_parameters["patience"] = self.patience
        self.hyper_parameters["initial_lr"] = self.initial_lr

    def __str__(self):
        return f"KingScheduler(initial_lr={self.initial_lr}, patience={self.patience}, decay={self.decay})"

    @staticmethod
    def _p_decreasing(loss_history: np.ndarray, i: int):
        """Compute the probability that the slope of the OLS fit to the loss history is negative."""
        loss = loss_history[i:]
        n = len(loss)

        # perform OLS on the loss entries to calc the slope mean
        X = np.c_[np.ones(n), np.arange(i, len(loss_history))]
        intercept, s_mean = np.linalg.inv(X.T @ X) @ X.T @ loss
        loss_pred = s_mean * X[:, 1] + intercept

        # compute the variance of our loss predictions and use this to compute
        # the (unbiased) estimate of the slope variance
        loss_var = n / (n - 2) * np.var(loss - loss_pred)
        s_var = (12 * loss_var) / (n ** 3 - n)

        # compute the probability that a random sample from a Gaussian
        # parameterized by s_mean and s_var is less than or equal to 0, e.g. the slope <= 0
        return gaussian_cdf(s_mean, s_var, 0)

    def _step_without_decrease(self, robust: bool = False, check_all: bool = False):
        """Returns the maximum number of time steps for which `P(loss is decreasing) < 0.51`."""
        lh = np.asarray(self.loss_history)

        # drop top 10% of loss values to filter out large loss spikes
        if robust:
            thresh = np.quantile(lh, 0.9)
            lh = lh[lh <= thresh]
        n = len(lh)
        decreasing_step = n
        if check_all:
            for i in reversed(range(n - 2)):
                if self._p_decreasing(lh, i) < 0.51:
                    decreasing_step = i
        else:
            i = max(0, n - self.patience - 1)
            if self._p_decreasing(lh, i) < 0.51:
                decreasing_step = i
        return n - decreasing_step

    def learning_rate(self, step: int, cur_loss: None):
        if cur_loss is None:
            raise ValueError(f"cur_loss must be a float, but got `{cur_loss}`")

        self.loss_history.append(cur_loss)
        if len(self.loss_history) >= self.patience:
            self.loss_history = self.loss_history[-self.max_history:]
            # if the loss has not decreased for `patience` time steps, decay the learning rate
            if self._step_without_decrease(robust=False) > self.patience \
                    and self._step_without_decrease(robust=True) > self.patience:
                self.current_lr *= self.decay
        return self.current_lr



