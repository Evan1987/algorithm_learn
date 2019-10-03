package Chap01Fundamentals.Chap01_02;

public class Ex15 {
    private static int[] readInts(String input){
        String[] words = input.split("\\s+");
        int[] res = new int[words.length];
        for(int i = 0; i < words.length; i++){
            res[i] = Integer.parseInt(words[i]);
        }
        return res;
    }

    public static void main(String[] args){
        String input = "1 2 3 4 5";
        int[] res = readInts(input);
        for(int x: res){
            System.out.println(x);
        }
    }
}
