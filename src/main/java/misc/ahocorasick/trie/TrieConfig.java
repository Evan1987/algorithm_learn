package misc.ahocorasick.trie;

/**
 * @author zhaochengming
 * @date 2020/12/11 00:13
 */
public class TrieConfig {
    // 允许重叠
    private boolean allowOverlaps = true;

    public boolean isAllowOverlaps() {
        return allowOverlaps;
    }

    public void setAllowOverlaps(boolean allowOverlaps) {
        this.allowOverlaps = allowOverlaps;
    }

    // 只匹配完整单词
    private boolean onlyWholeWords = false;

    public boolean isOnlyWholeWords() {
        return onlyWholeWords;
    }

    public void setOnlyWholeWords(boolean onlyWholeWords) {
        this.onlyWholeWords = onlyWholeWords;
    }

    // 大小写不敏感
    private boolean caseInsensitive = false;

    public boolean isCaseInsensitive() {
        return caseInsensitive;
    }

    public void setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }
}