package misc.ahocorasick.trie.token;

import misc.ahocorasick.trie.Emit;

/**
 * 一个片段
 * @author zhaochengming
 * @date 2020/12/11 22:47
 */
public abstract class Token {

    private final String fragment;
    public Token(String fragment) {
        this.fragment = fragment;
    }

    public String getFragment() {
        return fragment;
    }

    public abstract boolean isMatch();

    public abstract Emit getEmit();
}
