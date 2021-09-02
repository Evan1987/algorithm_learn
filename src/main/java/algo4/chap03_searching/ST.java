package algo4.chap03_searching;

import utils.Constant.Data;
import utils.annotations.WatchTime;

import java.io.*;
import java.util.NoSuchElementException;

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

    public void nullKeyCheck(K key){
        if(key == null) throw new IllegalArgumentException("the key is null");
    }

    public void emptyCheck(){
        if(this.isEmpty()) throw new NoSuchElementException("the symbol table is empty");
    }

    public void delete(K key){
        this.nullKeyCheck(key);
        this.put(key, null);
    }

    public boolean contains(K key){
        this.nullKeyCheck(key);
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

        table.delete("s");

        for(String s: table.keys())
            System.out.println(s + " " + table.get(s));
    }

    @WatchTime(methodDesc = "FreqCounter Analysis")
    public static void freqCounterTest(ST<String, Integer> table, int minLen){
        File file =  new File(Data.LEIPZIG300k);
        try {
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                for(String word: line.split(" ")){
                    if(word.length() < minLen) continue;
                    if(!table.contains(word))
                        table.put(word, 1);
                    else
                        table.put(word, table.get(word) + 1);
                }
            }

            String max = "";
            int maxNum = 0;
            for(String word: table.keys()){
                int num = table.get(word);
                if(num > maxNum){
                    max = word;
                    maxNum = num;
                }
            }
            System.out.println(max + " " + maxNum);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
