#!/usr/bin/python3  
# -*- coding: utf-8 -*-
# @date    : 2022/3/22 10:22
# @author  : zhaochengming

import numpy as np
from typing import *


def mini_batch(X: np.ndarray, batch_size: int = 256, shuffle: bool = True) -> Tuple[Generator, int]:
    """
    Compute the mini batch indices for a training dataset.
    Parameters
    ----------
    X: py:class:`ndarray <numpy.ndarray>` of shape `(N, *)`
        The dataset to divide into mini batches. Assumes the first dimension
        represents the number of training examples.
    batch_size : int
        The desired size of each mini batch. Note, however, that if ``X.shape[0] %
        batch_size > 0`` then the final batch will contain fewer than batch size
        entries. Default is 256.
    shuffle : bool
        Whether to shuffle the entries in the dataset before dividing into
        mini batches. Default is True.
    Returns
    -------
    mb_generator : generator
        A generator which yields the indices into X for each batch
    n_batches: int
        The number of batches
    """
    n = X.shape[0]
    ix = np.arange(n)
    n_batches = int(np.ceil(n / batch_size))

    if shuffle:
        np.random.shuffle(ix)

    def _generator():
        for i in range(n_batches):
            yield ix[i * batch_size: (i + 1) * batch_size]

    return _generator(), n_batches






