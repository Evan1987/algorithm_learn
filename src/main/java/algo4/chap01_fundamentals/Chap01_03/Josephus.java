package algo4.chap01_fundamentals.Chap01_03;


// Ex 37
public class Josephus {

    public static void main(String[] args) {
        int N = 7;
        int M = 2;
        Queue<Integer> queue = new Queue<Integer>();
        for(int i = 0; i < N; i ++){
            queue.enqueue(i);
        }

        while (!queue.isEmpty()){
            for(int i = 1; i < M; i ++){
                queue.enqueue(queue.dequeue());
            }

            System.out.println(queue.dequeue() + " ");
        }
    }
}
