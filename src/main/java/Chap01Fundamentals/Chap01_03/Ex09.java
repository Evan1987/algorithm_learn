package Chap01Fundamentals.Chap01_03;

import java.util.HashSet;
import java.util.Set;

public class Ex09 {
    private static String completeParentheses(String[] inputs){
        Set<String> OPS = new HashSet<String>(){{
            add("+"); add("-"); add("*"); add("/");
        }};
        Stack<String> ops = new Stack<String>();
        Stack<String> vals = new Stack<String>();
        for(String s: inputs){
            if(OPS.contains(s)) ops.push(s);
            else if(s.equals(")"))
            {
                String op = ops.pop();
                String v = vals.pop();
                v = "( " + vals.pop() + " " + op + " " + v + " )";
                vals.push(v);
            }
            else vals.push(s);
        }
        return vals.pop();
    }

    public static void main(String[] args) {
        String input = "1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )";
        System.out.println(completeParentheses(input.split(" ")));
    }
}
