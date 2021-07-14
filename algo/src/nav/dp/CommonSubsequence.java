package nav.dp;

import java.util.*;

public class CommonSubsequence {
    public static void main(String[] args) {
        List<Character> ans = longestCommonSubsequence("ZXVVYZW", "XKYKZPW");
        System.out.println(Arrays.toString(ans.toArray()));

        ans = longestCommonSubsequence("ABCDEFG", "APPLES");
        System.out.println(Arrays.toString(ans.toArray()));
    }

    public static List<Character> longestCommonSubsequence(String s1, String s2) {
        if (s1.length() == 0 || s2.length() == 0) return Collections.emptyList();
        int [][] T = new int[s1.length()][s2.length()];
        int i = 0, j = 0;
        for ( i = 0; i< s1.length(); i++){
            for ( j = 0; j< s2.length(); j++){
                if (s1.charAt(i) == s2.charAt(j)){
                    if (i == 0 || j == 0)
                        T[i][j] =1;
                    else
                        T[i][j] = 1 + T[i-1][j-1];
                } else {
                    int v1 = j== 0? 0: T[i][j-1];
                    int v2 = i== 0? 0: T[i-1][j];
                    T[i][j] = Math.max(v1,v2);
                }
            }
        }
        i--;j--;
        int pos = T[i][j];
        List<Character> ans = new ArrayList<>(pos);

        while (i >= 0 && j >= 0){
            if (s1.charAt(i) == s2.charAt(j)) {
                ans.add(0,s1.charAt(i));
                i--; j--;
            }
            else if (i > 0 && T[i-1][j] == T[i][j]){
                i--;
            } else{
               j--;
            }
        }
        return ans;

    }
}
