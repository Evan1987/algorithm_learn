package Chap02_Sorting;


import java.util.LinkedList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/2/29 19:14
 * Abstract class for Priority-Queue
 */
public abstract class PQ<Key extends Comparable<? super Key>> {

    public abstract void push(Key v);
    public abstract Key peek();
    public abstract Key pop();

    public List<Key> toList(){
        LinkedList<Key> keys = new LinkedList<>();
        while(!this.isEmpty()){
            keys.add(this.pop());
        }
        return keys;
    }

    public abstract int size();

    public boolean isEmpty(){
        return this.size() == 0;
    }

    public boolean less(Key a, Key b){
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<? super T>> void topMTest(PQ<T> pq, T[] inputs, int M){
        for(T x: inputs){
            pq.push(x);
            if(pq.size() > M)
                pq.pop();
        }
        List<T> keys = pq.toList();
        keys.forEach(key -> System.out.print(key + " "));
    }

}
