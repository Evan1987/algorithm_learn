package algo4.chap05_string.Chap05_02;

/**
 * @author zhaochengming
 * @date 2020/8/31 12:26
 */
public abstract class AbstractStringST<Value> {

    public abstract void put(String key, Value val);
    public abstract Value get(String key);
    public abstract void delete(String key);
    public abstract String longestPrefixOf(String s);
    public abstract Iterable<String> keysWithPrefix(String s);
    public abstract Iterable<String> keysThatMatch(String s);

    public abstract int size();

    public boolean contains(String key){
        return this.get(key) != null;
    }

    public boolean isEmpty(){
        return this.size() == 0;
    }

    public Iterable<String> keys(){
        return this.keysWithPrefix("");
    }
}
