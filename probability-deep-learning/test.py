#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @date    : 2022/10/4 16:57
# @author  : Evan

import tensorflow as tf
import tensorflow_probability as tfp
from tensorflow.keras.layers import Input, Dense
from tensorflow.keras.models import Model
from tensorflow_probability.python.distributions import distribution as tfd
from tensorflow_probability.python.layers import DistributionLambda
from tensorflow_probability.python.distributions import Normal


def my_distr(params):
    return tfp.distributions.Normal(loc=params, scale=0.01)


inputs = Input(shape=(1,))
distr = DistributionLambda(my_distr)(inputs)
model = Model(inputs=inputs, outputs=distr)


