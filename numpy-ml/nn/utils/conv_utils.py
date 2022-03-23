#!/usr/bin/python3  
# -*- coding: utf-8 -*-
# @date    : 2022/3/22 10:34
# @author  : zhaochengming

import numpy as np
from typing import *


def calc_pad_dims_2d(X_shape: Tuple[int, int, int, int], out_dim: Tuple[int, int],
                     kernel_shape: Tuple[int, int], stride: int, dilation: int = 0) -> Tuple[int, int, int, int]:
    """
    Compute the padding necessary to ensure that convolve `x` with a 2D kernel
    of shape `kernel_shape` and stride `stride` produces outputs with dimension
    `out_dim`.
    Parameters
    ----------
    X_shape : tuple of `(n_ex, in_rows, in_cols, in_ch)`
        Dimensions of the input volume. Padding is applied to `in_rows` and
        `in_cols`.
    out_dim : tuple of `(out_rows, out_cols)`
        The desired dimension of an output example after applying the
        convolution.
    kernel_shape : 2-tuple
        The dimension of the 2D convolution kernel.
    stride : int
        The stride for the convolution kernel.
    dilation : int
        Number of pixels inserted between kernel elements. Default is 0.
    Returns
    -------
    padding_dims : 4-tuple
        Padding dims for `X`. Organized as (left, right, up, down)
    """
    d = dilation
    fr, fc = kernel_shape
    out_rows, out_cols = out_dim
    n_ex, in_rows, in_cols, in_ch = X_shape

    # update effective filter shape based on dilation factor
    _fr, _fc = fr * (d + 1) - d, fc * (d + 1) - d

    pr = (stride * (out_rows - 1) + _fr - in_rows) // 2
    pc = (stride * (out_cols - 1) + _fc - in_cols) // 2

    # add asymmetric padding pixels to right / bottom
    pr1, pr2 = pr, pr
    out_rows1 = 1 + (in_rows + (pr1 + pr2) - _fr) // stride
    if out_rows1 == out_rows - 1:
        pr2 += 1
    else:
        assert out_rows1 == out_rows

    pc1, pc2 = pc, pc
    out_cols1 = 1 + (in_cols + (pc1 + pc2) - _fc) // stride
    if out_cols1 == out_cols - 1:
        pc2 += 1
    else:
        assert out_cols1 == out_cols

    res = (pr1, pr2, pc1, pc2)

    if pr1 < 0 or pr2 < 0 or pc1 < 0 or pc2 < 0:
        raise ValueError(f"Padding cannot be less than 0. Got: {res}")

    return res


def cal_pad_dims_1d(X_shape: Tuple[int, int, int], out_l: int, kernel_width: int,
                    stride: int, dilation: int = 0, causal: bool = False) -> Tuple[int, int]:
    """
    Compute the padding necessary to ensure that convolve `X` with a 1D kernel
    of shape `kernel_shape` and stride `stride` produces outputs with length
    `l_out`.
    Parameters
    ----------
    X_shape : tuple of `(n_ex, l_in, in_ch)`
        Dimensions of the input volume. Padding is applied on either side of
        `l_in`.
    out_l : int
        The desired length an output example after applying the convolution.
    kernel_width : int
        The width of the 1D convolution kernel.
    stride : int
        The stride for the convolution kernel.
    dilation : int
        Number of pixels inserted between kernel elements. Default is 0.
    causal : bool
        Whether to compute the padding dims for a regular or causal
        convolution. If causal, padding is added only to the left side of the
        sequence. Default is False.
    Returns
    -------
    padding_dims : 2-tuple
        Padding dims for X. Organized as (left, right)
    """

    d = dilation
    fw = kernel_width
    n_ex, in_l, in_ch = X_shape

    # update effective filter shape based on dilation factor
    _fw = fw * (d + 1) - d
    total_pad = stride * (out_l - 1) + _fw - in_l
    if not causal:
        pw = total_pad // 2
        pw1, pw2 = pw, pw
        out_l1 = 1 + (in_l + (pw1 + pw2) - _fw) // stride

        # add asymmetric padding pixels to right / bottom
        if out_l1 == out_l - 1:
            pw2 += 1
        else:
            assert out_l1 == out_l
    else:
        pw1, pw2 = total_pad, 0
        out_l1 = 1 + (in_l + (pw1 + pw2) - _fw) // stride
        assert out_l1 == out_l

    res = (pw1, pw2)
    if pw1 < 0 or pw2 < 0:
        raise ValueError(f"Padding cannot be less than 0. Got: {res}")

    return res


def pad1D(X: np.ndarray, pad: Union[Tuple, int, str], kernel_width: int = None, stride: int = None,
          dilation: int = 0, pad_value: int = 0) -> Tuple[np.ndarray, Tuple[int, int]]:
    """
    Pad a 3D input volume `X` along the second dimension.
    Parameters
    ----------
    X : :py:class:`ndarray <numpy.ndarray>` of shape `(n_ex, l_in, in_ch)`
        Input volume. Padding is applied to `l_in`.
    pad : tuple, int, or {'same', 'causal'}
        The padding amount. If 'same', add padding to ensure that the output
        length of a 1D convolution with a kernel of `kernel_shape` and stride
        `stride` is the same as the input length.  If 'causal' compute padding
        such that the output both has the same length as the input AND
        ``output[t]`` does not depend on ``input[t + 1:]``. If 2-tuple,
        specifies the number of padding columns to add on each side of the
        sequence.
    kernel_width : int
        The dimension of the 2D convolution kernel. Only relevant if p='same'
        or 'causal'. Default is None.
    stride : int
        The stride for the convolution kernel. Only relevant if p='same' or
        'causal'. Default is None.
    dilation : int
        The dilation of the convolution kernel. Only relevant if p='same' or
        'causal'. Default is None.
    pad_value: int
        The constant value to padding. Default is `0`.

    Returns
    -------
    X_pad : :py:class:`ndarray <numpy.ndarray>` of shape `(n_ex, padded_seq, in_channels)`
        The padded output volume
    p : 2-tuple
        The number of 0-padded columns added to the (left, right) of the sequences
        in `X`.
    """
    p = pad
    if isinstance(p, int):
        p = (p, p)
    if isinstance(p, tuple):
        assert len(p) >= 2
        X_pad = np.pad(X, pad_width=((0, 0), (p[0], p[1]), (0, 0)), mode="constant", constant_values=pad_value)
    elif isinstance(p, str) and p in ("same", "casual"):
        if kernel_width and stride:
            casual = p == "casual"
            p = cal_pad_dims_1d(X.shape, X.shape[1], kernel_width, stride, dilation, causal=casual)
            X_pad, p = pad1D(X, p)
        else:
            raise ValueError("Kernel and stride must be given when padding set `same` or `casual`.")
    else:
        raise TypeError("`Pad` must be `int` or `tuple` or string of `same` or `casual`.")
    return X_pad, p


def pad2D(X: np.ndarray, pad: Union[Tuple, int, str], kernel_shape: Tuple[int, int] = None,
          stride: int = None, dilation: int = 0, pad_value: int = 0) -> Tuple[np.ndarray, Tuple[int, int, int, int]]:
    """
    Pad a 4D input volume `X` along the second and third dimensions.
    Parameters
    ----------
    X : :py:class:`ndarray <numpy.ndarray>` of shape `(n_ex, in_rows, in_cols, in_ch)`
        Input volume. Padding is applied to `in_rows` and `in_cols`.
    pad : tuple, int, or 'same'
        The padding amount. If 'same', add padding to ensure that the output of
        a 2D convolution with a kernel of `kernel_shape` and stride `stride`
        has the same dimensions as the input.  If 2-tuple, specifies the number
        of padding rows and columns to add *on both sides* of the rows/columns
        in `X`. If 4-tuple, specifies the number of rows/columns to add to the
        top, bottom, left, and right of the input volume.
    kernel_shape : 2-tuple
        The dimension of the 2D convolution kernel. Only relevant if p='same'.
        Default is None.
    stride : int
        The stride for the convolution kernel. Only relevant if p='same'.
        Default is None.
    dilation : int
        The dilation of the convolution kernel. Only relevant if p='same'.
        Default is 0.
    pad_value: int
        The constant value to padding. Default is `0`.

    Returns
    -------
    X_pad : :py:class:`ndarray <numpy.ndarray>` of shape `(n_ex, padded_in_rows, padded_in_cols, in_channels)`
        The padded output volume.
    p : 4-tuple
        The number of 0-padded rows added to the (top, bottom, left, right) of
        `X`.
    """
    p = pad
    if isinstance(p, int):
        p = (p, p, p, p)

    if isinstance(p, tuple):
        assert len(p) == 4



