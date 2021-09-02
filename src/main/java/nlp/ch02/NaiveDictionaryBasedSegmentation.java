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
        this(IOUtil.loadDictionary("data/dictionary/CoreNatureDictionary.mini.txt"));
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



}
