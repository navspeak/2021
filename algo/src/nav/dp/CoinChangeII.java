package nav.dp;

import java.util.HashMap;
import java.util.Map;

public class CoinChangeII {
    public static void main(String[] args) {
        System.out.println(minNumberOfCoinsForChange(7, new int[]{1,5,10})); // $5 * 1 coin + $1* 2 = 3
        System.out.println(minNumberOfCoinsForChangeDP(7, new int[]{1,5,10})); // 3
    }

    // here it's asked the min number of coins. Coin I was just number of ways to get a value
    // like 6 [6,1,5]  => 5+1 is one way, 1+1+1+1+1 another, just 6 another => 3
    public static int minNumberOfCoinsForChange(int total, int[] denoms) {

        Map<String, Integer> memo = new HashMap<>();
        int ret = minNumberOfCoinsForChange(total, denoms, 0);
        return ret == Integer.MAX_VALUE? -1: ret;
    }

    /*
                   6 [ 1,5,10]
       op1 = Take 1 if possible    op2 = Don't take 1 => Return min op1 & op2
       op1 = 1 + 5[1,5,10] => note recursive call to any option can lead to no solution
       op2 = 6 [5,10]

       No solution: 1[5,10] => 1[10] => 1[]=> No solution return INFINITY
       0 solution: 0 [10] => 0 solution
     */

    // Time complexity 2^total => with memoization can be total*denoms.length
    public static int minNumberOfCoinsForChange(int total, int[] denoms, int i) {
        if (total == 0 ) return 0;
        if (i == denoms.length) return  Integer.MAX_VALUE;
        int num1 = Integer.MAX_VALUE, num2 = 0;
        if (denoms[i] <= total){
            num1 = minNumberOfCoinsForChange(total - denoms[i], denoms, i);
            num1 = num1== Integer.MAX_VALUE? num1: num1+1;
        }
        num2 = minNumberOfCoinsForChange(total, denoms, i+1);

        return Math.min(num1, num2);
    }

    public static int minNumberOfCoinsForChangeDP(int total, int[] denoms) {
        int M = denoms.length+1;
        int N = total+1;
        int[][] T = new int[M][N];
        /*
                   0 1 2 3 4 5 6 7 (total)
        (coins) 0  0 - - - - - - -
           1    1  0
           5    2  0
           10   3  0

           T[0][j] = Integer.MAX_VALUE;
           T[i][0] = 0;
         */

        for (int j = 1; j < N ; j++) {
            T[0][j] = Integer.MAX_VALUE;
        }

        for (int i = 1; i < M; i++) {
            for (int j = 1; j < N; j++) {
                int num1 = Integer.MAX_VALUE, num2 = 0;
                if (denoms[i-1] <= j) {
                    num1 =  T[i][j-denoms[i-1]] == Integer.MAX_VALUE ?
                            Integer.MAX_VALUE : 1 + T[i][j-denoms[i-1]];
                }
                num2 = T[i-1][j];
                T[i][j] = Math.min(num1, num2);
            }
        }

        return T[M-1][N-1] == Integer.MAX_VALUE? -1 : T[M-1][N-1];
    }


}
