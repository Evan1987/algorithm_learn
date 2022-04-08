#!/usr/bin/python3  
# -*- coding: utf-8 -*-
# @date    : 2022/3/22 10:34
# @author  : zhaochengming

import numpy as np
from typing import *


#######################################################################
#                            Padding Utils                            #
#######################################################################


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
        if len(p) == 2:
            p = (p[0], p[0], p[1], p[1])
        assert len(p) == 4

        X_pad = np.pad(X, pad_width=((0, 0), (p[0], p[1]), (p[2], p[3]), (0, 0)),
                       mode="constant", constant_values=pad_value)

    elif isinstance(p, str) and p == "same":
        if kernel_shape and stride:
            p = calc_pad_dims_2d(X.shape, X.shape[1:3], kernel_shape=kernel_shape, stride=stride, dilation=dilation)
            X_pad, p = pad2D(X, p)
        else:
            raise ValueError("Kernel shape and stride must be given when pad is `same`.")
    else:
        raise TypeError("`Pad` must be `int` or `tuple` or string of `same` .")
    return X_pad, p


def dilate(X: np.ndarray, d: int, value: int = 0) -> np.ndarray:
    """
    Dilate the 4D volume `X` by `d`.
    Notes
    -----
    For a visual depiction of a dilated convolution, see [1].
    References
    ----------
    .. [1] Dumoulin & Visin (2016). "A guide to convolution arithmetic for deep
       learning." https://arxiv.org/pdf/1603.07285v1.pdf
    Parameters
    ----------
    X : :py:class:`ndarray <numpy.ndarray>` of shape `(n_ex, in_rows, in_cols, in_ch)`
        Input volume.
    d : int
        The number of 0-rows to insert between each adjacent row + column in `X`.
    value: int
        The constant value to insert
    Returns
    -------
    Xd : :py:class:`ndarray <numpy.ndarray>` of shape `(n_ex, out_rows, out_cols, out_ch)`
        The dilated array where
        .. math::
            \\text{out_rows}  &=  \\text{in_rows} + d(\\text{in_rows} - 1) \\\\
            \\text{out_cols}  &=  \\text{in_cols} + d (\\text{in_cols} - 1)
    """

    n_ex, n_row, n_col, n_ch = X.shape
    r_ix = np.repeat(np.arange(1, n_row), d)
    c_ix = np.repeat(np.arange(1, n_col), d)
    Xd = np.insert(X, r_ix, value, axis=1)
    Xd = np.insert(Xd, c_ix, value, axis=2)
    return Xd


#######################################################################
#                     Convolution Arithmetic                          #
#######################################################################


def calc_fan(weight_shape: Tuple) -> Tuple[int, int]:
    """
    Compute the fan-in and fan-out for a weight matrix/volume.
    Parameters
    ----------
    weight_shape : tuple
        The dimensions of the weight matrix/volume. The final 2 entries must be
        `in_ch`, `out_ch`.
    Returns
    -------
    fan_in : int
        The number of input units in the weight tensor
    fan_out : int
        The number of output units in the weight tensor
    """
    if len(weight_shape) == 2:
        fan_in, fan_out = weight_shape
    elif len(weight_shape) in [3, 4]:
        in_ch, out_ch = weight_shape[-2:]
        kernel_size = in_ch * out_ch
        fan_in, fan_out = in_ch * kernel_size, out_ch * kernel_size
    else:
        raise ValueError("Unrecognized weight dimension: {}".format(weight_shape))
    return fan_in, fan_out


def _calc_conv2D_out_dims(X_shape: Tuple[int, int, int, int], W_shape: Tuple[int, int, int, int],
                          pad: Tuple[int, int, int, int], stride: int = 1, dilation: int = 0) -> Tuple[int, int, int, int]:
    """
    Compute the dimension of the output volume for the 2D convolution.
    Parameters
    ----------
    X_shape : 4-tuple
        The dimensions of the input volume to the convolution.
        Entries are expected to be (`n_ex`, `in_rows`, `in_cols`, `in_ch`).
    W_shape : 4-tuple
        The dimensions of the weight volume for the convolution.
        Entries are expected to be (`fr`, `fc`, `in_ch`, `out_ch`).
    pad : tuple
        The padding amount specifies the number of padding columns to add on each side of the sequence.
    stride : int
        The stride for the convolution kernel. Default is 1.
    dilation : int
        The dilation of the convolution kernel. Default is 0.
    Returns
    -------
    out_dims : 4-tuple
        The dimensions of the output volume. Entries are (`n_ex`, `out_rows`, `out_cols`, `out_ch`).
    """
    n_ex, in_rows, in_cols, in_ch = X_shape
    fr, fc, in_ch, out_ch = W_shape
    pr1, pr2, pc1, pc2 = pad
    s, d = stride, dilation

    # adjust effective filter size to account for dilation
    _fr, _fc = fr * (d + 1) - d, fc * (d + 1) - d
    out_rows = (in_rows + pr1 + pr2 - _fr) // s + 1
    out_cols = (in_cols + pc1 + pc2 - _fc) // s + 1
    return n_ex, out_rows, out_cols, out_ch


def calc_conv_out_dims(X_shape: Tuple, W_shape: Tuple, stride: int = 1, pad: Union[Tuple, int, str] = 0, dilation: int = 0) -> Tuple:
    """
    Compute the dimension of the output volume for the specified convolution.
    Parameters
    ----------
    X_shape : 3-tuple or 4-tuple
        The dimensions of the input volume to the convolution. If 3-tuple,
        entries are expected to be (`n_ex`, `in_length`, `in_ch`). If 4-tuple,
        entries are expected to be (`n_ex`, `in_rows`, `in_cols`, `in_ch`).
    W_shape : 3-tuple or 4-tuple
        The dimensions of the weight volume for the convolution. If 3-tuple,
        entries are expected to be (`f_len`, `in_ch`, `out_ch`). If 4-tuple,
        entries are expected to be (`fr`, `fc`, `in_ch`, `out_ch`).
    pad : tuple, int, or {'same', 'causal'}
        The padding amount. If 'same', add padding to ensure that the output
        length of a 1D convolution with a kernel of `kernel_shape` and stride
        `stride` is the same as the input length.  If 'causal' compute padding
        such that the output both has the same length as the input AND
        ``output[t]`` does not depend on ``input[t + 1:]``. If 2-tuple, specifies the
        number of padding columns to add on each side of the sequence. Default
        is 0.
    stride : int
        The stride for the convolution kernel. Default is 1.
    dilation : int
        The dilation of the convolution kernel. Default is 0.
    Returns
    -------
    out_dims : 3-tuple or 4-tuple
        The dimensions of the output volume. If 3-tuple, entries are (`n_ex`,
        `out_length`, `out_ch`). If 4-tuple, entries are (`n_ex`, `out_rows`,
        `out_cols`, `out_ch`).
    """
    dummy = np.zeros(X_shape)
    s, p, d = stride, pad, dilation
    # conv1D
    if len(X_shape) == 3:
        n_ex, in_length, in_ch = X_shape
        fw, in_ch, out_ch = W_shape
        _, p = pad1D(dummy, p)
        pw1, pw2 = p

        _fw = fw * (d + 1) - d
        out_length = (in_length + pw1 + pw2 - _fw) // s + 1
        out_dims = (n_ex, out_length, out_ch)

    # conv2D
    elif len(X_shape) == 4:
        if not isinstance(p, tuple):
            _, p = pad2D(dummy, p)
        out_dims = _calc_conv2D_out_dims(X_shape, W_shape, p, stride, dilation)
    else:
        raise ValueError(f"Invalid number of input dims: {len(X_shape)}")
    return out_dims


#######################################################################
#                   Convolution Vectorization Utils                   #
#######################################################################


def _im2col_indices(X_shape: Tuple[int, int, int, int], W_shape: Tuple[int, int, int, int],
                    pad: Tuple[int, int, int, int], stride: int, dilation: int = 0) -> Tuple[int, int, int]:
    """
    Helper function that computes indices into X in prep for columnize in
    :func:`im2col`.
    Code extended from Andrej Karpathy's `im2col.py`
    """
    n_ex, out_rows, out_cols, out_ch = _calc_conv2D_out_dims(X_shape, W_shape, pad, stride, dilation)
    if out_rows <= 0 or out_cols <= 0:
        raise ValueError(f"Dimension mismatch during conv, out_rows: {out_rows}, out_cols: {out_cols}.")

    # i1/j1 : row/col templates
    # i0/j0 : n. copies (len) and offsets (values) for row/col templates
    fr, fc = W_shape[:2]
    n_ex, in_rows, in_cols, n_in = X_shape
    d = dilation
    i0 = np.repeat(np.arange(fr), fc)
    i0 = np.tile(i0, n_in) * (d + 1)
    i1 = stride * np.repeat(np.arange(out_rows), out_cols)
    j0 = np.tile(np.arange(fc), fr * n_in) * (d + 1)
    j1 = stride * np.tile(np.arange(out_cols), out_rows)

    # i.shape = (fr * fc * n_in, out_height * out_width)
    # j.shape = (fr * fc * n_in, out_height * out_width)
    # k.shape = (fr * fc * n_in, 1)
    i = i0.reshape(-1, 1) + i1.reshape(1, -1)
    j = j0.reshape(-1, 1) + j1.reshape(1, -1)
    k = np.repeat(np.arange(n_in), fr * fc).reshape(-1, 1)
    return k, i, j


def im2col(X: np.ndarray, W_shape: Tuple[int, int, int, int], pad: Union[Tuple, str, int],
           stride: int, dilation: int = 0) -> np.ndarray:
    """
    Pads and rearrange overlapping windows of the input volume into column
    vectors, returning the concatenated padded vectors in a matrix `X_col`.
    Notes
    -----
    A NumPy reimagining of MATLAB's ``im2col`` 'sliding' function.
    Code extended from Andrej Karpathy's ``im2col.py``.
    Parameters
    ----------
    X : :py:class:`ndarray <numpy.ndarray>` of shape `(n_ex, in_rows, in_cols, in_ch)`
        Input volume (not padded).
    W_shape: 4-tuple containing `(kernel_rows, kernel_cols, in_ch, out_ch)`
        The dimensions of the weights/kernels in the present convolutional
        layer.
    pad : tuple, int, or 'same'
        The padding amount. If 'same', add padding to ensure that the output of
        a 2D convolution with a kernel of `kernel_shape` and stride `stride`
        produces an output volume of the same dimensions as the input.  If
        2-tuple, specifies the number of padding rows and columns to add *on both
        sides* of the rows/columns in X. If 4-tuple, specifies the number of
        rows/columns to add to the top, bottom, left, and right of the input
        volume.
    stride : int
        The stride of each convolution kernel
    dilation : int
        Number of pixels inserted between kernel elements. Default is 0.
    Returns
    -------
    X_col : :py:class:`ndarray <numpy.ndarray>` of shape (Q, Z)
        The reshaped input volume where where:
        .. math::
            Q  &=  \\text{kernel_rows} \\times \\text{kernel_cols} \\times \\text{n_in} \\\\
            Z  &=  \\text{n_ex} \\times \\text{out_rows} \\times \\text{out_cols}
    """
    fr, fc, n_in, n_out = W_shape
    s, p, d = stride, pad, dilation
    n_ex, in_rows, in_cols, n_in = X.shape

    # zero-pad the input
    X_pad, p = pad2D(X, p, (fr, fc), s, d, 0)
    pr1, pr2, pc1, pc2 = p

    k, i, j = _im2col_indices(X.shape, W_shape, p, s, d)

    # shuffle to have channels as the first dim
    X_pad = X_pad.transpose(0, 3, 1, 2)
    X_col = X_pad[:, k, i, j]
    X_col = X_col.transpose(1, 2, 0).reshape(fr * fc * n_in, -1)
    return X_col


#######################################################################
#                             Convolution                             #
#######################################################################


def conv2D(X: np.ndarray, W: np.ndarray, stride: int, pad: Union[Tuple, int, str], dilation: int = 0) -> np.ndarray:
    """
    A faster (but more memory intensive) implementation of the 2D "convolution"
    (technically, cross-correlation) of input `X` with a collection of kernels in
    `W`.
    Notes
    -----
    Relies on the :func:`im2col` function to perform the convolution as a single
    matrix multiplication.
    For a helpful diagram, see Pete Warden's 2015 blogpost [1].
    References
    ----------
    .. [1] Warden (2015). "Why GEMM is at the heart of deep learning,"
       https://petewarden.com/2015/04/20/why-gemm-is-at-the-heart-of-deep-learning/
    Parameters
    ----------
    X : :py:class:`ndarray <numpy.ndarray>` of shape `(n_ex, in_rows, in_cols, in_ch)`
        Input volume (un-padded).
    W: :py:class:`ndarray <numpy.ndarray>` of shape `(kernel_rows, kernel_cols, in_ch, out_ch)`
        A volume of convolution weights/kernels for a given layer.
    stride : int
        The stride of each convolution kernel.
    pad : tuple, int, or 'same'
        The padding amount. If 'same', add padding to ensure that the output of
        a 2D convolution with a kernel of `kernel_shape` and stride `stride`
        produces an output volume of the same dimensions as the input.  If
        2-tuple, specifies the number of padding rows and columns to add *on both
        sides* of the rows/columns in `X`. If 4-tuple, specifies the number of
        rows/columns to add to the top, bottom, left, and right of the input
        volume.
    dilation : int
        Number of pixels inserted between kernel elements. Default is 0.
    Returns
    -------
    Z : :py:class:`ndarray <numpy.ndarray>` of shape `(n_ex, out_rows, out_cols, out_ch)`
        The convolution of `X` with `W`.
    """
    s, d = stride, dilation
    fr, fc, in_ch, out_ch = W.shape
    n_ex, in_rows, in_cols, in_ch = X.shape
    _, p = pad2D(X, pad, (fr, fc), s, d, 0)
    pr1, pr2, pc1, pc2 = p

    # update effective filter shape based on dilation factor
    _fr, _fc = fr * (d + 1) - d, fc * (d + 1) - d

    # compute the dimensions of the convolution output
    out_rows = int((in_rows + pr1 + pr2 - _fr) / s + 1)
    out_cols = int((in_cols + pc1 + pc2 - _fc) / s + 1)

    # convert X and W into the appropriate 2D matrices and take their product
    X_col = im2col(X, W.shape, p, s, d)
    W_col = W.transpose((3, 2, 0, 1)).reshape(out_ch, -1)

    return (W_col @ X_col).reshape(out_ch, out_rows, out_cols, n_ex).transpose((3, 1, 2, 0))





