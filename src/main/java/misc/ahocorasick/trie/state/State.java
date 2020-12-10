package misc.ahocorasick.trie.state;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * 一个状态有如下几个功能:
 *  success: 成功转移到另一个状态
 *  failure: 不可顺着字符串跳转的话，则跳转到一个浅一点的节点
 *  emits: 命中一个模式串
 *根节点稍有不同，根节点没有 failure 功能，它的“failure”指的是按照字符串路径转移到下一个状态。其他节点则都有failure状态
 *
 * @author zhaochengming
 * @date 2020/12/11 00:20
 */
public abstract class State {

    // 模式串的长度，也是这个状态的深度
    protected final int depth;

    // 只用于根节点来表示自己（当没有匹配到任何模式串的时候）
    protected final State rootState;

    // fail 函数，如果没有匹配到，则跳转到此状态。
    protected State failure = null;

    // 只要这个状态可达，则记录模式串
    private Set<String> emits = null;

    public State() {
        this(0);
    }

    // 构造深度为depth的节点
    public State(int depth) {
        this.depth = depth;
        this.rootState = depth == 0 ? this : null;
    }

    public int getDepth() {
        return depth;
    }

    // 添加一个匹配到的模式串（这个状态对应着这个模式串)
    public void addEmit(String keyword) {
        if (this.emits == null)
            this.emits = new TreeSet<>();
        this.emits.add(keyword);
    }

    // 添加一些匹配到的模式串
    public void addEmit(Collection<String> emits) {
        for (String emit: emits)
            addEmit(emit);
    }

    // 获取这个节点代表的模式串(们)
    public Collection<String> getEmits() {
        return this.emits == null ? Collections.emptyList() : this.emits;
    }

    // 获取failure状态
    public State getFailure() {
        return failure;
    }

    public void setFailure(State failure) {
        this.failure = failure;
    }

    // 转移到下一个状态（基于success转移）
    public abstract State nextState(Character character);
    // 转移到下一个状态，忽略根节点
    public abstract State nextStateIgnoreRootState(Character character);
    // 添加一个状态到success函数
    public abstract State addState(Character character);
    // 获取success状态
    public abstract Collection<State> getStates();
    // 获取要转移到下一个状态的可能char
    public abstract Collection<Character> getTransitions();

}
