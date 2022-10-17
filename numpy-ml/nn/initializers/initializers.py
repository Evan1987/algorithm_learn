#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2022/4/1 11:03
# @author  : zhaochengming

import re
import numpy as np
from functools import partial
from ast import literal_eval as _eval
from ..optimizers import *
from ..activations import *
from ..schedulers import *


class SchedulerInitializer(object):

    def __init__(self, lr: float, param=None):
        self.lr = lr
        self.param = param

    def init_from_str(self, s: str) -> SchedulerBase:
        pass


