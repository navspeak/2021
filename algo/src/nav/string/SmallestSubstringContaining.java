package nav.string;

import java.util.*;

public class SmallestSubstringContaining {
    public static void main(String[] args) {
        String bigString = "abcd$ef$axb$c$";
        String smallString = "$$abf";
        String expected = "f$axb$";
        System.out.println(smallestSubstringContaining(bigString, smallString));
    }
// https://www.youtube.com/watch?v=U1q16AFcjKs
    // isn't working. some minor issue i guess. Look at it Later
    public static String smallestSubstringContaining(String bigString, String smallString) {
        Map<Character, Integer> patternMap = new HashMap<>();
        for (char c: smallString.toCharArray()){
            patternMap.compute(c, (k,v)-> {
                if (v == null) return 1;
                return v+1;
            });
        }
        int l = 0, r = 0;
        int need = patternMap.size();
        int start = 0, end =0, len = Integer.MAX_VALUE;
        boolean found = false;

        while(r<bigString.length()){
           char curr = bigString.charAt(r++);
           if (patternMap.containsKey(curr)){
               int val = patternMap.get(curr);
               patternMap.put(curr, val  - 1);
               if (val == 0) need--;
           }

           if (need > 0) continue;

           while (need == 0){
               char atLeft = bigString.charAt(l++);
               if (patternMap.containsKey(atLeft)){
                   int val = patternMap.get(atLeft);
                   patternMap.put(atLeft, val+1);
                   if (val == 0) need++;
               }
           }

            if (r - l < len) {
                found = true;
                start = l;
                len = r - l;
                end = r; // one more than end 0-based index - helps in doing substring
            }
        }
        return found ? bigString.substring(start,end): "";
    }


}
