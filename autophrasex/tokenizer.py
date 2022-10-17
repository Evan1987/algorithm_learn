#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2022/8/25 16:43
# @author  : zhaochengming

import abc
import logging
import jieba
from .utils import *
from typing import *


def uniform_text(text: str, to_lower: bool = True, to_simplified: bool = True, to_half: bool = True) -> str:
    if to_lower:
        text = text.lower()
    if to_half:
        text = "".join(q2b(c) for c in text)
    if to_simplified:
        text = t2s(text)
    return text


class AbstractTokenizer(abc.ABC):

    @abc.abstractmethod
    def tokenize(self, text: str, **kwargs) -> List[str]:
        raise NotImplementedError


class JiebaTokenizer(AbstractTokenizer):

    def __init__(self, custom_vocab_path: str = None):
        if custom_vocab_path:
            with open(custom_vocab_path, mode='rt', encoding='utf8') as fin:
                jieba.load_userdict(fin)
                logging.info('Load user dict: %s successfully.', custom_vocab_path)
        jieba.initialize()

    def tokenize(self, text: str, cut_all: bool = False, hmm: bool = True, **kwargs) -> List[str]:
        text = uniform_text(text)
        result = jieba.lcut(text, cut_all=kwargs.get('cut_all', False), HMM=kwargs.get('hmm', True))
        result = [x.strip() for x in result if x.strip()]
        return result


