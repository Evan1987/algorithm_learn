package nlp.ch02;

import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NaiveDictionaryBasedSegmentation {

    private final Map<String, CoreDictionary.Attribute> dictionary;

    public NaiveDictionaryBasedSegmentation(Map<String, CoreDictionary.Attribute> dictionary) {
        this.dictionary = dictionary;
    }

    public NaiveDictionaryBasedSegmentation() throws IOException {
        this(IOUtil.loadDictionary("F:/data/hanlp/dictionary/CoreNatureDictionary.mini.txt"));
    }

    public List<String> segmentFully(String text) {
        List<String> wordList = new LinkedList<>();
        for (int i = 0; i < text.length(); i ++) {
            for (int j = i + 1; j <= text.length(); j ++) {
                String word = text.substring(i, j);
                if (this.dictionary.containsKey(word))
                    wordList.add(word);
            }
        }
        return wordList;
    }

    public List<String> segmentForwardLongest(String text) {
        List<String> wordList = new LinkedList<>();
        for (int i = 0; i < text.length(); ) {
            int z = i + 1;
            for (int j = z; j <= text.length(); j ++) {
                if (this.dictionary.containsKey(text.substring(i, j)))
                    z = j;
            }
            wordList.add(text.substring(i, z));
            i = z;
        }
        return wordList;
    }

    public List<String> segmentBackwardLongest(String text) {
        List<String> wordList = new LinkedList<>();
        for (int i = text.length(); i > 0; ) {
            int z = i - 1;
            for (int j = z; j >= 0; j --) {
                if (this.dictionary.containsKey(text.substring(j, i)))
                    z = j;
            }
            wordList.add(0, text.substring(z, i));
            i = z;
        }
        return wordList;
    }

    private static int countSingleWord(List<String> wordList) {
        int size = 0;
        for (String word: wordList)
            if (word.length() == 1)
                size ++;
        return size;
    }

    public List<String> segmentBidirectional(String text) {
        List<String> forward = segmentForwardLongest(text);
        List<String> backward = segmentBackwardLongest(text);

        if (forward.size() < backward.size())
            return forward;
        else if (forward.size() > backward.size())
            return backward;
        if (countSingleWord(forward) < countSingleWord(backward))
            return forward;
        return backward;
    }

    public static void main(String[] args) throws IOException {
        NaiveDictionaryBasedSegmentation seg = new NaiveDictionaryBasedSegmentation();
        System.out.println(seg.segmentFully("就读北京大学"));
        System.out.println(seg.segmentForwardLongest("就读北京大学"));
        System.out.println(seg.segmentForwardLongest("研究生命起源"));
        System.out.println(seg.segmentBackwardLongest("研究生命起源"));
    }



}
