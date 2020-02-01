package Chap01_Fundamentals.Chap01_03;

public class Parentheses {
    public static void main(String[] args) {
        String x1 = "[()]{}{[()()]()}";
        String x2= "[(])";
        String[] inputs1 = string2Array(x1);
        String[] inputs2 = string2Array(x2);

        System.out.println(check(inputs1));  // true
        System.out.println(check(inputs2));  // false
    }

    private static String[] string2Array(String x){
        String[] res = new String[x.length()];
        char[] charArray = x.toCharArray();
        for(int i = 0; i < x.length(); i++){
            res[i] = String.valueOf(charArray[i]);
        }
        return res;
    }

    private static boolean check(String[] inputs){
        Stack<String> stack = new Stack<String>();
        for(String input: inputs){
            if(input.equals("{") || input.equals("[") || input.equals("(")){
                stack.push(input);
            }else if(!stack.isEmpty()){
                if(input.equals("}")){
                    if(!stack.pop().equals("{")) return false;
                }
                else if(input.equals("]")){
                    if(!stack.pop().equals("[")) return false;
                }
                else if(input.equals(")")){
                    if(!stack.pop().equals("(")) return false;
                }
            }
        }
        return stack.isEmpty();
    }

}
