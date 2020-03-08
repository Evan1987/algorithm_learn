package Chap03_Searching;

/**
 * @author Evan
 * @date 2020/3/8 13:35
 * 符号表基础类
 */
public abstract class ST<K, V>  {
    public abstract void put(K key, V value);
    public abstract V get(K key);
    public abstract int size();
    public abstract Iterable<K> keys();

    protected ST(){}

    public void delete(K key){
        this.put(key, null);
    }

    public boolean contains(K key){
        return this.get(key) != null;
    }

    public boolean isEmpty(){
        return this.size() == 0;
    }

    // 行为测试用例
    public static void test(ST<String, Integer> table){
        String[] arr = {"s", "e", "a", "r", "c", "h", "e", "x", "a", "m", "p", "l", "e"};
        for(int i = 0; i < arr.length; i ++)
            table.put(arr[i], i);

        for(String s: table.keys())
            System.out.println(s + " " + table.get(s));
    }


}
