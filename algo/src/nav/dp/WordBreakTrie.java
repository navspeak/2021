package nav.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WordBreakTrie {
    public static void main(String[] args) {
//        Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
//        Output: ["cats and dog","cat sand dog"]
        String s = "catsanddog";
        List<String> wordDict = Arrays.asList("cat", "cats", "and", "sand", "dog");
       // System.out.println(Arrays.toString(new WordBreakTrie().wordBreak(s, wordDict).toArray()));
    }

    //0(2^N)
//    public List<String> wordBreak(String s, List<String> wordDict) {
//        TrieNode trie = populateSuffixTree(s);
//        List<String> ans = new ArrayList<>();
//        for(String word: wordDict){
//            if (search(trie, word ) == true){
//        }
//    }

}