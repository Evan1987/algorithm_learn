package algo4.chap01_fundamentals.Chap01_03;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Dijkstra {
    private Set<String> OPS = new HashSet<String>(Arrays.asList("+", "-", "*", "/", "sqrt"));

    public double Evaluate(String[] inputs){
        Stack<String> ops = new Stack<String>();
        Stack<Double> vals = new Stack<Double>();

        for(String s: inputs){
            if(OPS.contains(s)) ops.push(s);
            else if(s.equals("(")) ;
            else if(s.equals(")"))
            {
                String op = ops.pop();
                double v = vals.pop();
                if(op.equals("+")) v = vals.pop() + v;
                else if(op.equals("-")) v = vals.pop() - v;
                else if(op.equals("*")) v = vals.pop() * v;
                else if(op.equals("/")) v = vals.pop() / v;
                else if(op.equals("sqrt")) v = Math.sqrt(v);
                vals.push(v);
            }
            else vals.push(Double.parseDouble(s));
        }
        return vals.pop();
    }

}
