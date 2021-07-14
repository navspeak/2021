package nav.dp;

import java.util.Arrays;

public class PalindromicCuts {
    public static void main(String[] args) {
        System.out.println(palindromePartitioningMinCuts("noonabbad"));
    }
    public static int palindromePartitioningMinCuts(String str) {
        int[] palindromicLengths = new int[str.length()];
        Arrays.fill(palindromicLengths, 1);
        for (int i =0; i< str.length(); i++){
            int len = Math.max(getPalindromeLength(str, i,i),
                    getPalindromeLength(str, i,i+1));
            // 012 3 456       01 2
            // rac(e)car => 7, ra(c)car=> 6
            int x = i - (len-1)/2;// 3 -7/2 = 3-2, 2-5/2= 2-3
            if (palindromicLengths[x] < len)
                palindromicLengths[x] = len;
        }
        // iterate thru palindromicLengths to count no. of cuts needs
        int cuts = 0;
        for (int i =0; i< str.length(); ){
            int lenStartingAtThisIndex = palindromicLengths[i];
            int nextIndex = i + lenStartingAtThisIndex;
            if (nextIndex < str.length()) cuts++;
            i = nextIndex;
        }
        return cuts;
    }

    static int getPalindromeLength(String str, int l, int r){
        //if (l==r) return 1;
        if (l>r) return 0;
        while(l>=0 && r<str.length() && str.charAt(l) == str.charAt(r)){
            l--;
            r++;
        }
        return r-l-1; // 7--1=8
    }
}
