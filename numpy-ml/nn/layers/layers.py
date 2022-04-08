#!/usr/bin/python3  
# -*- coding: utf-8 -*-
# @date    : 2022/4/1 11:00
# @author  : zhaochengming

import numpy as np
from abc import ABCMeta, abstractmethod
from typing import *


class LayerBase(metaclass=ABCMeta):

    def __init__(self, optimizer=None):
        self.X = []
        self.act_fn = None
        self.trainable: bool = True

