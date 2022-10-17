#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @date    : 2022/8/25 17:31
# @author  : zhaochengming

import abc
import logging
import os
from utils import *
from typing import *
from .tokenizer import AbstractTokenizer


class AbstractCorpusReadCallback(abc.ABC):

    def on_process_doc_begin(self):
        """Starting to process a doc"""
        pass

    def update_tokens(self, tokens: List[str], **kwargs):
        """Process tokens, tokenized from current doc.
        :param tokens: List of string, all tokens tokenized from doc
        """
        pass

    def update_ngrams(self, start: int, end: int, ngram: Tuple, n: int, **kwargs):
        """Process ngrams.
        :param start: start index of this ngram in the whole token list
        :param end: end index of this ngram in the whole token list
        :param ngram: ngram tokens
        :param n: N of n-grams
        """
        pass

    def on_process_doc_end(self):
        """Finished to process a document"""
        pass


class AbstractCorpusReader(abc.ABC):

    @abc.abstractmethod
    def read(self, corpus_files: List[str], **kwargs):
        raise NotImplementedError()


def read_corpus_files(input_files: List[str], callback: Callable = None,
                      verbose: bool = True, log_steps: int = 100):
    if isinstance(input_files, str):
        input_files = [input_files]
    count = 0
    for file in input_files:
        if not os.path.exists(file):
            logging.warning('File: %s does not exist. Skipped.', file)
            continue
        with open(file, mode='rt', encoding='utf-8') as fin:
            for line in fin:
                line = line.strip()
                if not line:
                    continue
                if callback:
                    callback(line)
                count += 1
                if verbose and count % log_steps == 0:
                    logging.info('Processes %d lines.', count)
        logging.info('Finished to process file: %s', file)
    logging.info('Done! Processed %d lines in total.', count)


class DefaultCorpusReader(AbstractCorpusReader):

    def __init__(self, tokenizer: AbstractTokenizer):
        super(DefaultCorpusReader, self).__init__()
        self.tokenizer = tokenizer

    def read(self, corpus_files: List[str], callback: AbstractCorpusReadCallback = None,
             N: int = 4, verbose: bool = True, log_steps: int = 100, **kwargs):

        def read_line_callback(line: str):
            # callbacks process doc begin
            callback.on_process_doc_begin()
            tokens: List[str] = self.tokenizer.tokenize(line, **kwargs)
            # callbacks process tokens
            callback.update_tokens(tokens, **kwargs)
            # callbacks process ngrams
            for k in range(1, N + 1):
                for (start, end), window in ngrams(tokens, k=k):
                    callback.update_ngrams(start, end, window, k, **kwargs)
            # callbacks process doc end
            callback.on_process_doc_end()

        read_corpus_files(corpus_files, callback=read_line_callback, verbose=verbose, log_steps=log_steps)



