package Chap01Fundamentals.Chap01_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ex03 {
    public static void main(String[] args){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try{
            String line = reader.readLine();
            String[] numbers = line.split(" ");

            Boolean isEqual = true;
            int same = Integer.parseInt(numbers[0]);
            for(int i = 1; i < 3; i++){
                if (same != Integer.parseInt(numbers[i])){
                    isEqual = false;
                    break;
                }
            }

            if(isEqual){System.out.println("Equal");}else{System.out.println("Not Equal");}
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
