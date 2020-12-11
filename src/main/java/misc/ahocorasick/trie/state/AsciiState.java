package misc.ahocorasick.trie.state;

import java.util.*;

/**
 * 单字节字符优化版AC自动机节点
 * @author zhaochengming
 * @date 2020/12/11 00:46
 */
public class AsciiState extends State {

    static final int SIZE = 256;

    // goto 表，也称转移函数。根据字符串的下一个字符转移到下一个状态
    private final State[] success = new State[SIZE];

    public AsciiState() {}
    public AsciiState(int depth) {
        super(depth);
    }

    /**
     * 转移到下一个状态
     * @param character 希望按此字符转移
     * @param ignoreRootState 是否忽略根节点，如果是根节点自己调用则应该是true，否则为false
     * @return 转移结果
     */
    private State nextState(Character character, boolean ignoreRootState) {
        // 取低8位
        State nextState = this.success[character & 0xff];
        if (!ignoreRootState && nextState == null && this.rootState != null)
        {
            nextState = this.rootState;
        }
        return nextState;
    }

    @Override
    public State nextState(Character character) {
        return nextState(character, false);
    }

    @Override
    public State nextStateIgnoreRootState(Character character) {
        return nextState(character, true);
    }

    @Override
    public State addState(Character character) {
        State nextState = nextStateIgnoreRootState(character);
        if(nextState == null) {
            nextState = new AsciiState(this.depth + 1);
            this.success[character] = nextState;
        }
        return nextState;
    }

    @Override
    public Collection<State> getStates() {
        List<State> stateList = new ArrayList<>(SIZE);
        for (State state : success)
            if (state != null)
                stateList.add(state);
        return stateList;
    }

    @Override
    public Collection<Character> getTransitions() {
        List<Character> chars = new ArrayList<>(SIZE);
        for(int i = 0; i < SIZE; i ++)
            if(this.success[i] != null)
                chars.add((char) i);
        return chars;
    }
}
