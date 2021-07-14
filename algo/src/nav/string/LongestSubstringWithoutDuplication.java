package nav.string;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutDuplication {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("navneet"));
        System.out.println(lengthOfLongestSubstring("navaneet"));
        System.out.println(lengthOfLongestSubstring("bbbbbb"));
        System.out.println(lengthOfLongestSubstring("aau"));
        System.out.println(lengthOfLongestSubstring("tmmzuxt"));// watch out for such case abbcda
        System.out.println(lengthOfLongestSubstring("a"));
        System.out.println(lengthOfLongestSubstring(""));
        System.out.println(lengthOfLongestSubstring(" "));
    }
    // O(n) time: space for hashmap
    // sliding window of varying size
    public static int lengthOfLongestSubstring(String s) {
        // navneet
        if (s.length() <= 1) return s.length();
        Map<Character , Integer> map = new HashMap<>(); // Map of eaech character and its last seen index in the string
        int start = 0, len = 1; // start with just first character as the answer
        int currStartIdx = 0;
        boolean substringFound = false; // case nav
        for (int i = 0; i < s.length(); i++) {
            Character curr = s.charAt(i);

            if (!map.containsKey(curr)) {
                map.put(curr, i);
                if (i == s.length() - 1 && i - currStartIdx + 1 > len){ // 1 extra because we need at the last index
                    start = currStartIdx;
                    len = i - currStartIdx +1;
                }
            }
            else {
                if (map.get(curr) < currStartIdx) {
                    map.put(curr, i);
                    if (i == s.length() - 1 && i - currStartIdx + 1 > len){ // 1 extra because we need at the last index
                        start = currStartIdx;
                        len = i - currStartIdx +1;
                    }
                    continue;
                }
                int newLen = i - currStartIdx;
                if (newLen >= len){
                    len = newLen;
                    start = currStartIdx;
                    //substringFound = true;
                    //System.out.println(s.substring(start, start+len));
                }
                currStartIdx = map.get(curr) + 1;

                map.put(curr, i);
            }
        }

        System.out.println(s.substring(start, start+len));
        return len;
    }
}
