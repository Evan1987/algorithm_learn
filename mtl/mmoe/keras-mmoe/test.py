#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2023/7/27 11:13
# @author  : zhaochengming

from tensorflow.keras.callbacks import Callback


class ROCCallback(Callback):

    def __init__(self):
        super(ROCCallback, self).__init__()
