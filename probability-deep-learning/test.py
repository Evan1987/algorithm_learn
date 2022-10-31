#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @date    : 2022/10/4 16:57
# @author  : Evan

import tensorflow as tf
import tensorflow_probability as tfp
from tensorflow_probability.python.layers import VariableLayer
from tensorflow.keras.layers import Input, Dense
from tensorflow.keras.models import Model
from tensorflow_probability.python.bijectors.real_nvp import RealNVP
from tensorflow_probability.python.bijectors.masked_autoregressive import MaskedAutoregressiveFlow
from tensorflow_probability.python.layers import DistributionLambda
from tensorflow_probability.python.distributions import Normal
from tensorflow.keras.constraints import Constraint

tfb = tfp.bijectors
tfd = tfp.distributions


net = tfb.real_nvp_default_template([32, 32])
b = RealNVP(shift_and_log_scale_fn=net, num_masked=2)
print(b.trainable_variables)