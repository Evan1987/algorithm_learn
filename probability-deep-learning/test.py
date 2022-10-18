#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @date    : 2022/10/4 16:57
# @author  : Evan

import tensorflow as tf
import tensorflow_probability as tfp
from tensorflow_probability.python.layers import VariableLayer
from tensorflow.keras.layers import Input, Dense
from tensorflow.keras.models import Model
import tensorflow_probability.python.distributions as tfd
from tensorflow_probability.python.layers import DistributionLambda
from tensorflow_probability.python.distributions import Normal
from tensorflow.keras.constraints import Constraint


tfd.Mixture


