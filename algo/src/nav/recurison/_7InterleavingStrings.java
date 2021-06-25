package nav.recurison;

import java.util.HashMap;
import java.util.Map;

public class _7InterleavingStrings {
    private static Map<String, Boolean> map = new HashMap<>();
    public static boolean interweavingStrings_WillNotWork(String one, String two, String three) {
        // ("aacaaaa", "aaabaaa", "aaaaaacbaaaaaa")
        int onelength = one == null ? 0: one.length();
        int twolength = two == null ? 0: two.length();
        int threelength = three == null ? 0: three.length();
        if (threelength != (onelength + twolength))
            return false;


        boolean inOrder1 = checkRelativeOrder(one, three);
        if (inOrder1 == false) return false;
        boolean inOrder2 = checkRelativeOrder(two, three);
        if (inOrder2 == false) return false;
        return true;
    }

    public static boolean interweavingStrings(String one, String two, String three) {
        int onelength = one == null ? 0: one.length();
        int twolength = two == null ? 0: two.length();
        int threelength = three == null ? 0: three.length();
        if (threelength != (onelength + twolength))
            return false;

        return interweavingStrings_helper(one, two,  three);
    }

    // without memo - Time O(2^(m+n) | Space O(m+n). With Memo: Time O(mn) |O(mn)
    public static boolean interweavingStrings_helper(String one, String two, String three) {

        String key = one+"-"+two+"-"+three;
        //if (map.containsKey(key)) return map.get(key);
        System.out.println("F("+key+")");
        if (one.isEmpty()) {
            return two.equals(three);
        }
        if (two.isEmpty()) {
            return one.equals(three);
        }

        boolean o1 = false, o2=false;
        if (three.charAt(0) == one.charAt(0)) {
           if (interweavingStrings_helper(one.substring(1), two,   three.substring(1) ) == true) {
               map.put(key, true);
               return true;
           }
        }

        if (three.charAt(0) == two.charAt(0)){
            if (interweavingStrings_helper(one, two.substring(1),  three.substring(1) ) == true){
                map.put(key, true);
                return true;
            }
        }
        map.put(key, false);
        return false;
    }

    private static boolean checkRelativeOrder(String one, String two){
        int j = 0;
        for(int i=0; i < one.length(); i++){
            boolean match = false;
            while(j < two.length()){
                if (one.substring(i,i+1).equals(two.substring(j,j+1))){
                    match = true;
                    j++;
                    break;
                }
                j++;
            }
            if (match == false) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String x = "a".substring(1);
        System.out.println(x == null); // x will bempty not null
        System.out.println(x.isEmpty());
        System.out.println(checkRelativeOrder("aabcc", "aadbbbaccc"));
//        System.out.println(checkRelativeOrder("dbbca", "aadbbbaccc"));
        //System.out.println(interweavingStrings("algoexpert", "your-dream-job", "your-algodream-expertjob"));
        System.out.println(interweavingStrings("aaa", "aaaf", "aaafaaa"));
//        System.out.println(interweavingStrings("aabcc", "dbbca", "aadbbbaccc"));
//        System.out.println(interweavingStrings("aacaaaa", "aaabaaa", "aaaaaacbaaaaaa"));
    }
}
/*

     abc
                       aaxbbc
     axb

     i=0, j=0, k=0
     if (three[k] == one[i]) i++; k++
     else if (three[k] == two[i]) i++; k++
     i=1, j=0, k=1
       i=2, j=0, k=2


 */
