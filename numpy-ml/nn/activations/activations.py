#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2022/4/1 11:05
# @author  : zhaochengming

import numpy as np
from abc import ABCMeta, abstractmethod
from scipy.special import erf


class ActivationBase(metaclass=ABCMeta):

    @abstractmethod
    def fn(self, z: np.ndarray):
        """Apply the activation function to an input"""
        raise NotImplementedError

    @property
    @abstractmethod
    def name(self) -> str:
        raise NotImplementedError

    def __str__(self):
        return self.name

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

    @staticmethod
    def erf_prime(x):
        return (2 / np.sqrt(np.pi)) * np.exp(-(x ** 2))

    @staticmethod
    def erf_prime2(x):
        return -4 * x * np.exp(-(x ** 2)) / np.sqrt(np.pi)

    def grad(self, x: np.ndarray, **kwargs):
        s = x / np.sqrt(2)

        if self.approximate:
            approx = np.tanh(np.sqrt(2 / np.pi) * (x + 0.044715 * x ** 3))
            dx = 0.5 + 0.5 * approx + ((0.5 * x * self.erf_prime(s)) / np.sqrt(2))
        else:
            dx = 0.5 + 0.5 * erf(s) + ((0.5 * x * self.erf_prime(s)) / np.sqrt(2))
        return dx

    def grad2(self, x: np.ndarray, **kwargs):
        s = x / np.sqrt(2)
        return (1 / 2 * np.sqrt(2)) * (1 + self.erf_prime(s) + (self.erf_prime2(s) / np.sqrt(2)))


class Tanh(ActivationBase):

    @property
    def name(self):
        return "Tanh"

    def fn(self, z: np.ndarray):
        return np.tanh(z)

    def grad(self, x: np.ndarray, **kwargs):
        return 1 - np.tanh(x) ** 2

    def grad2(self, x: np.ndarray, **kwargs):
        tanh_x = np.tanh(x)
        return -2 * tanh_x * (1 - tanh_x ** 2)


class Affine(ActivationBase):

    def __init__(self, slope: float = 1, intercept: float = 0):
        self.slope = slope
        self.intercept = intercept
        super(Affine, self).__init__()

    @property
    def name(self) -> str:
        return f"Affine(slope={self.slope}, intercept={self.intercept})"

    def fn(self, z: np.ndarray):
        return self.slope * z + self.intercept

    def grad(self, x: np.ndarray, **kwargs):
        return self.slope * np.ones_like(x)

    def grad2(self, x: np.ndarray, **kwargs):
        return np.zeros_like(x)


class Identity(Affine):

    def __init__(self):
        super(Identity, self).__init__(slope=1, intercept=0)

    @property
    def name(self) -> str:
        return "Identity"


class ELU(ActivationBase):
    """
    An exponential linear unit (ELU).

    Notes
    -----
    ELUs are intended to address the fact that ReLUs are strictly non-negative
    and thus have an average activation > 0, increasing the chances of internal
    co-variate shift and slowing down learning. ELU units address this by (1)
    allowing negative values when :math:`x < 0`, which (2) are bounded by a value
    `alpha`. Similar to `LeakyReLU`, the negative activation
    values help to push the average unit activation towards 0. Unlike
    `LeakyReLU`, however, the boundedness of the negative activation
    allows for greater robustness in the face of large negative values,
    allowing the function to avoid conveying the *degree* of "absence"
    (negative activation) in the input.

    Parameters
    ----------
    alpha : float
        Slope of negative segment. Default is 1.

    References
    ----------
    .. [*] Clevert, D. A., Unterthiner, T., Hochreiter, S. (2016). "Fast
       and accurate deep network learning by exponential linear units
       (ELUs)". *4th International Conference on Learning
       Representations*.
    """

    def __init__(self, alpha: float = 1.0):
        self.alpha = alpha

    @property
    def name(self) -> str:
        return f"ELU(alpha={self.alpha})"

    def fn(self, z: np.ndarray):
        return np.where(z > 0, z, self.alpha * (np.exp(z) - 1))

    def grad(self, x: np.ndarray, **kwargs):
        return np.where(x > 0, np.ones_like(x), self.alpha * np.exp(x))

    def grad2(self, x: np.ndarray, **kwargs):
        return np.where(x > 0, np.zeros(x), self.alpha * np.exp(x))


class Exponential(ActivationBase):

    @property
    def name(self) -> str:
        return "Exponential"

    def fn(self, z: np.ndarray):
        return np.exp(z)

    def grad(self, x: np.ndarray, **kwargs):
        return np.exp(x)

    def grad2(self, x: np.ndarray, **kwargs):
        return np.exp(x)


class SELU(ActivationBase):
    """
    Scaled exponential linear unit.

    Notes
    -----
    SELU units, when used in conjunction with proper weight initialization and
    regularization techniques, encourage neuron activations to converge to
    zero-mean and unit variance without explicit use of e.g., batchnorm.

    For SELU units, the :math:`\alpha` and :math:`\text{scale}` values are
    constants chosen so that the mean and variance of the inputs are preserved
    between consecutive layers. As such the authors propose weights be
    initialized using Lecun-Normal initialization: :math:`w_{ij} \sim
    \mathcal{N}(0, 1 / \text{fan_in})`, and to use the dropout variant
    :math:`\alpha`-dropout during regularization. [*]_

    See the reference for more information (especially the appendix ;-) ).

    References
    ----------
    .. [*] Klambauer, G., Unterthiner, T., & Hochreiter, S. (2017).
       "Self-normalizing neural networks." *Advances in Neural Information
       Processing Systems, 30.*
    """
    def __init__(self):
        self.alpha = 1.6732632423543772848170429916717
        self.scale = 1.0507009873554804934193349852946
        self.elu = ELU(alpha=self.alpha)

    @property
    def name(self) -> str:
        return "SELU"

    def fn(self, z: np.ndarray):
        return self.scale * self.elu.fn(z)

    def grad(self, x: np.ndarray, **kwargs):
        return self.elu.grad(x) * self.scale

    def grad2(self, x: np.ndarray, **kwargs):
        return self.elu.grad2(x) * self.scale


class HardSigmoid(ActivationBase):
    """
    A "hard" sigmoid activation function.

    Notes
    -----
    The hard sigmoid is a piecewise linear approximation of the logistic
    sigmoid that is computationally more efficient to compute.
    """

    @property
    def name(self) -> str:
        return "Hard Sigmoid"

    def fn(self, z: np.ndarray):
        return np.clip((0.2 * z) + 0.5, 0.0, 1.0)

    def grad(self, x: np.ndarray, **kwargs):
        return np.where((x >= -2.5) & (x <= 2.5), 0.2, 0)

    def grad2(self, x: np.ndarray, **kwargs):
        return np.zeros_like(x)


class SoftPlus(ActivationBase):

    @property
    def name(self) -> str:
        return "SoftPlus"

    def fn(self, z: np.ndarray):
        return np.log(np.exp(z) + 1)

    def grad(self, x: np.ndarray, **kwargs):
        exp_x = np.exp(x)
        return exp_x / (exp_x + 1)

    def grad2(self, x: np.ndarray, **kwargs):
        exp_x = np.exp(x)
        return exp_x / ((exp_x + 1) ** 2)

