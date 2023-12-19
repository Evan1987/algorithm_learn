
"""学习 https://github.com/openai/tiktoken/blob/main/tiktoken/_educational.py"""
import re
from typing import *


def visualize_tokens(token_bytes: List[bytes]) -> None:
    background = [f"\u001b[48;5;{i}m" for i in [167, 179, 185, 77, 80, 68, 134]]

    n = len(background)
    i = 0
    last_color = None
    for x in token_bytes:
        x = x.decode("utf-8", "replace")
        color = background[i % n]
        if color == last_color:
            color = background[(i + 1) % n]
            assert color != last_color
        last_color = color
        i += len(x)
        print(color + x, end="")
    print("\u001b[0m")


class SimpleBPE(object):

    def __init__(self, pattern_str: str, ranks: Dict[bytes, int] = None):
        """
        :param pattern_str: A regex pattern string that is used to split the input text
        :param ranks: A dictionary mapping token bytes to their ranks. The ranks correspond to merge priority
        """
        self.pattern_str = pattern_str
        self.ranks = ranks or {}
        self._pattern = re.compile(self.pattern_str)
        self._decoder = {token: token_bytes for token_bytes, token in self.ranks.items()}

    def fit(self, data: str, vocab_size: int, visualize: bool = False):
        if vocab_size < 2 ** 8:
            raise ValueError("vocab_size must be at least 256, so we can encode all bytes")
        # First, add tokens for each individual byte value
        self.ranks = {bytes([i]): i for i in range(2 ** 8)}

        # Splinter up our data into lists of bytes
        # data = "Hello world"
        # words = [
        #     [b'H', b'e', b'l', b'l', b'o'],
        #     [b' ', b'w', b'o', b'r', b'l', b'd']
        # ]
        words: List[List[bytes]] = [[bytes([b]) for b in word.encode("utf-8")] for word in self._pattern.findall(data)]

        # Now, use our data to figure out which merges we should make
        while len(self.ranks) < vocab_size:
            pass




