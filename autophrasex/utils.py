#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2022/8/25 17:03
# @author  : zhaochengming

import os
import opencc
from naive_stopwords import Stopwords
from typing import *


STOPWORDS = Stopwords()
converter = opencc.OpenCC("t2s")


def t2s(text: str) -> str:
    return converter.convert(text)


def lower(text: str) -> str:
    return text.lower()


def q2b(char: str) -> str:
    """全角转半角"""
    ascii_ = ord(char)
    if ascii_ == 12288:  # 全角空格 \u3000
        return " "  # ascii 32
    if 65281 <= ascii_ <= 65374:
        return chr(ascii_ - 65248)
    return char


def ngrams(seq: List[str], k: int = 2) -> Iterable[str]:
    n = len(seq)
    start, end = 0, 0
    while start <= n - k:
        end = start + k
        yield (start, end), tuple(seq[start: end])
        start += 1


def load_input_files(input_files, callback):
    if not input_files:
        return
    if isinstance(input_files, str):
        input_files = [input_files]
    for f in input_files:
        if not os.path.exists(f):
            continue
        lino = 0
        with open(f, mode='rt', encoding='utf8') as fin:
            for line in fin:
                line = line.rstrip('\n')
                if callback:
                    callback(line, lino)
                lino += 1


__all__ = ["STOPWORDS", "t2s", "lower", "q2b", "ngrams"]
