package nav.dp;

import java.util.*;

public class CoinChange {
    public static void main(String[] args) {
        //6 [6,1,5]  => 5+1 is one way, 1+1+1+1+1 another, just 6 another => 3
        System.out.println(numberOfWaysToMakeChange(6, new int[]{6,1,5})); // 3
        System.out.println(numberOfWaysToMakeChangeDP(6, new int[]{6,1,5})); // 3
    }

    public static int numberOfWaysToMakeChange(int total, int[] denoms) {

        Map<String, Integer> memo = new HashMap<>();
        return numberOfWaysToMakeChange(total, denoms, 0, memo);
    }

    public static int numberOfWaysToMakeChangeDP(int total, int[] denoms) {
        int [][] T = new int[denoms.length][total+1];
        for (int i = 0; i < denoms.length; i++) {
            T[i][0] = 1;
        }

        for (int i = 0; i < denoms.length; i++) {
            for (int j = 1; j <= total; j++) {
               int way1 = 0;
               if (denoms[i] <= j){
                   way1 = T[i][j-denoms[i]];
               }
               T[i][j] = (i> 0 ? T[i-1][j] : 0) + way1;
            }
        }

        return T[denoms.length-1][total];
    }

    public static int numberOfWaysToMakeChange(int total, int[] denoms, int i, Map<String, Integer> memo) {
       String key = total+"-"+i;
       if (memo.containsKey(key)) return memo.get(key);
       if (total == 0) {
           memo.put(key, 1);
           return  1;
       }
       if (i == denoms.length) {
           memo.put(key, 0);
           return 0;
       }
       int ways1 =0 , ways2 = 0;

       if (denoms[i] <= total)
            ways1 = numberOfWaysToMakeChange(total - denoms[i], denoms, i, memo);
       ways2 = numberOfWaysToMakeChange(total, denoms, i+1, memo);
       memo.put(key, ways1 + ways2);
       return ways1 + ways2;

    }
}
