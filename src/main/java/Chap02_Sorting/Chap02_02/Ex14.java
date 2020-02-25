package Chap02_Sorting.Chap02_02;

import Chap01_Fundamentals.Chap01_03.Queue;

/**
 * @author Evan
 * @date 2020/2/25 15:59
 * 归并有序队列
 */
public class Ex14 {
    public static void main(String[] args) {
        String[] a = {"a", "b", "c"};
        String[] b = {"d", "e", "f", "g", "h"};

        Queue<String> q1 = new Queue<>();
        for(String x: a)
            q1.enqueue(x);

        Queue<String> q2 = new Queue<>();
        for(String x: b)
            q2.enqueue(x);

        Queue<String> q = mergeQueue(q1, q2);
        while(!q.isEmpty())
            System.out.print(q.dequeue() + " ");
        System.out.println();

    }

    private static <T extends Comparable> Queue<T> mergeQueue(Queue<T> q1, Queue<T> q2){
        Queue<T> q = new Queue<>();
        if(q1.isEmpty() && q2.isEmpty()) return q;
        if(q1.isEmpty()) return q2;
        if(q2.isEmpty()) return q1;

        T item1 = null;
        T item2 = null;

        while(!(q1.isEmpty() && item1 == null) && !(q2.isEmpty() && item2 == null)){
            if(item1 == null)
                item1 = q1.dequeue();
            if(item2 == null)
                item2 = q2.dequeue();
            if(item1.compareTo(item2) < 0){
                q.enqueue(item1);
                item1 = null;
            }else{
                q.enqueue(item2);
                item2 = null;
            }
        }

        if(item1 != null)
            q.enqueue(item1);

        if(item2 != null)
            q.enqueue(item2);

        while(!q1.isEmpty()){
            q.enqueue(q1.dequeue());
        }

        while(!q2.isEmpty()){
            q.enqueue(q2.dequeue());
        }

        return q;
    }

}