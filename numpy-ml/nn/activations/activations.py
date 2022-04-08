#!/usr/bin/python3  
# -*- coding: utf-8 -*-
# @date    : 2022/4/1 11:05
# @author  : zhaochengming

import numpy as np
from abc import ABCMeta, abstractmethod
from scipy.special import erf
from typing import *


class ActivationBase(metaclass=ABCMeta):

    @abstractmethod
    def fn(self, z: np.ndarray):
        """Apply the activation function to an input"""
        raise NotImplementedError

    @property
    @abstractmethod
    def name(self) -> str:
        raise NotImplementedError

    @abstractmethod
    def grad(self, x: np.ndarray, **kwargs):
        """Compute the gradient of the activation function wrt the input"""
        raise NotImplementedError

    @abstractmethod
    def grad2(self, x: np.ndarray, **kwargs):
        """Compute the second gradient of the activation function wrt the input"""
        raise NotImplementedError

    def __call__(self, z: np.ndarray):
        """Apply the activation function to an input"""
        if z.ndim == 1:
            z = z.reshape(1, -1)
        return self.fn(z)


class Sigmoid(ActivationBase):

    def __init__(self):
        super(Sigmoid, self).__init__()

    @property
    def name(self) -> str:
        return "Sigmoid"

    def fn(self, z: np.ndarray):
        return 1 / (1 + np.exp(-z))

    def grad(self, x: np.ndarray, **kwargs):
        fn_x = self.fn(x)
        return fn_x * (1 - fn_x)

    def grad2(self, x: np.ndarray, **kwargs):
        fn_x = self.fn(x)
        return fn_x * (1 - fn_x) * (1 - 2 * fn_x)


class ReLU(ActivationBase):

    def __init__(self):
        super(ReLU, self).__init__()

    @property
    def name(self) -> str:
        return "ReLU"

    def fn(self, z: np.ndarray):
        return np.clip(z, 0, np.inf)

    def grad(self, x: np.ndarray, **kwargs):
        return (x > 0).astype(int)

    def grad2(self, x: np.ndarray, **kwargs):
        return np.zeros_like(x)


class LeakyReLU(ActivationBase):

    def __init__(self, alpha: float = 0.3):
        self.alpha = alpha
        super(LeakyReLU, self).__init__()

    @property
    def name(self) -> str:
        return f"Leaky ReLU(alpha={self.alpha})"

    def fn(self, z: np.ndarray):
        _z = z.copy()
        _z[z < 0] *= self.alpha
        return _z

    def grad(self, x: np.ndarray, **kwargs):
        g = np.ones_like(x)
        g[x < 0] *= self.alpha
        return g

    def grad2(self, x: np.ndarray, **kwargs):
        return np.zeros_like(x)


class GELU(ActivationBase):
    """Gaussian error linear unit (GELU)"""

    def __init__(self, approximate: bool = True):
        self.approximate = approximate
        super(GELU, self).__init__()

    @property
    def name(self) -> str:
        return f"GELU(approximate={self.approximate})"

    def fn(self, z: np.ndarray):
        if self.approximate:
            return 0.5 * z * (1 + np.tanh(np.sqrt(2 / np.pi) * (z + 0.044715 * z ** 3)))
        return 0.5 * z * (1 + erf(z / np.sqrt(2)))

    def grad(self, x: np.ndarray, **kwargs):
        s = x / np.sqrt(2)

        def erf_prime(z: np.ndarray):
            return (2 / np.sqrt(np.pi)) * np.exp(-(z ** 2))

        if self.approximate:
            approx = np.tanh(np.sqrt(2 / np.pi) * (x + 0.044715 * x ** 3))
            dx = 0.5 + 0.5 * approx + ((0.5 * x * erf_prime(s)) / np.sqrt(2))
        else:
            dx = 0.5 + 0.5 * erf(s) + ((0.5 * x * erf_prime(s)) / np.sqrt(2))
        return dx
