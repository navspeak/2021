package nav.string;

import java.util.*;

//sliding window
public class Anagram {
    public static void main(String[] args) {
        //System.out.println(Arrays.toString(findAnagrams("abcddba", "abd").toArray()));
        System.out.println(Arrays.toString(findAnagrams("xabccba", "abc").toArray()));
    }
 // sliding window Nick White
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s == null || p == null || s.length() == 0 || p.length() == 0) return result;
        int[] charMap = new int[26];
        for(char c: p.toCharArray()){
            charMap[c-'a']++;
        }

        int l=0, r=0, c= p.length();
        while(r<s.length()){
            if (charMap[s.charAt(r) - 'a']> 0){
                c--;
            }
            charMap[s.charAt(r) - 'a'] --;
            r++;
            if (c == 0) result.add(l);
            if (r - l == p.length()){
                if (charMap[s.charAt(l) - 'a'] >=0 ) c++;
                charMap[s.charAt(l) - 'a']++;
                l++;
            }
        }

        return result;
    }




}
