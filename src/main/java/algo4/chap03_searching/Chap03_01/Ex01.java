package algo4.chap03_searching.Chap03_01;

/**
 * @author Evan
 * @date 2020/3/9 11:05
 * GPA计算
 */
public class Ex01 {
    public static void main(String[] args) {
        SequentialSearchST<String, Double> st = new SequentialSearchST<String, Double>(){{
            put("A+", 4.33); put("A" , 4.00); put("A-", 3.67);
            put("B+", 3.33); put("B" , 3.00); put("B-", 2.67);
            put("C+", 2.33); put("C" , 2.00); put("C-", 1.67);
            put("D" , 1.00); put("F" , 0.00);
        }};

        String[] scores = {"A-", "A+", "B-", "B", "B+", "A+", "A-"};

        double sum = 0;
        for(String score: scores)
            sum += st.get(score);
        System.out.println("GPA: " + sum / scores.length);
    }

}
