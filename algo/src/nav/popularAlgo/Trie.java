package nav.popularAlgo;

import java.util.HashMap;
import java.util.Map;

public class Trie {
    //suffix tree not a prefix tree (same concept)
    /* suffix tree for "babc"

          [ b,       a,    c    ]
           / \       /     \
          .   .     .       *
        [a]    c  b
        /     /   /
       .    *   c
      [b]      /
      /      *
     [c]
     /
    [*]


     */
    public static void main(String[] args) {
        SuffixTrie trie = new SuffixTrie("babc");
        System.out.println(trie.contains("babc"));
        System.out.println(trie.contains("abc"));
        System.out.println(trie.contains("bc"));
        System.out.println(trie.contains("c"));
        System.out.println(trie.contains("cd"));

    }
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    }

    static class SuffixTrie {
        TrieNode root = new TrieNode();
        char endSymbol = '*';

        public SuffixTrie(String str) {
            populateSuffixTrieFrom(str);
        }

        //O(n^2) space and time
        public void populateSuffixTrieFrom(String str) {
            for (int i =0; i < str.length(); i++){
                insert(root, str.substring(i), 0);
            }
        }

        private void insert(TrieNode root, String str, int index){
            if (index == str.length()){
                root.children.put('*', null); // should you put null value?
                return;
            }
            char ch = str.charAt(index);
            TrieNode node = root.children.get(ch);;
            if (node != null){
                insert(node, str,index+1);
            } else {
                node = new TrieNode();
                root.children.put(ch, node);
                insert(node, str,index+1);
            }
        }

        // o(m) time
        public boolean contains(String str) {
            return search(root, str, 0);
        }

        private boolean search(TrieNode root, String str, int index){
            if (index == str.length()){
                return root.children.containsKey('*') == true;
            }
            char ch = str.charAt(index);
            TrieNode node = root.children.get(ch);
            if (node == null){
                return false;
            } else {
                return search(node, str, index+1);
            }
        }
    }

}
