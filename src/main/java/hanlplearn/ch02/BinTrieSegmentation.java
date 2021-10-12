package hanlplearn.ch02;


import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import hanlplearn.collection.IHit;
import hanlplearn.collection.trie.bintrie.BinTrie;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BinTrieSegmentation {

    private final BinTrie<CoreDictionary.Attribute> binTrie;

    public BinTrieSegmentation() throws IOException {
        Map<String, CoreDictionary.Attribute> dictionary = IOUtil.loadDictionary("F:/data/hanlp/dictionary/CoreNatureDictionary.mini.txt");
        this.binTrie = new BinTrie<>(dictionary);
    }

    public List<String> segmentFully(String text) {
        List<String> words = new LinkedList<>();
        this.binTrie.parseText(text, (begin, end, value) -> words.add(text.substring(begin, end)));
        return words;
    }

    public static void main(String[] args) throws IOException {
        BinTrieSegmentation seg = new BinTrieSegmentation();
        System.out.println(seg.segmentFully("就读北京大学"));
    }


}
