package nav.dp;

import java.util.HashMap;
import java.util.Map;

public class MaxProfitWithKTransactions {
    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{5,11,3,50,60,90}, 2));
    }

    // time limit exceeds
    public static int maxProfit(int[] prices, int k) {

        /*
            i = no. of transactions, j = no of days


            T[i][j] = T[i][j-1] skip transaction on jth day
                    = prices[j] +  From x=0 to j-1 Max(T[i-1][j-x] - profit[x])

         */
        if (prices.length == 0) return 0;
        int M = k+1;
        int N = prices.length;
        int [][]T = new int[M][N];
        // T[0][j] = 0 => 0 transactions on any day
        // T[k][0] = 0 => whatever # of transactions on 0th day or first day

        for (int i = 1; i < M; i++) {
            for (int j = 1; j < N; j++) {
                int skipThatday = T[i][j-1];
                int onOneOfTheEarlierDays = Integer.MIN_VALUE;
                for (int x = 0; x < j; x++) {
                    onOneOfTheEarlierDays= Math.max(T[i-1][x] - prices[x], onOneOfTheEarlierDays);
                }
                if (onOneOfTheEarlierDays == Integer.MIN_VALUE) onOneOfTheEarlierDays = 0;
                int sellOnThatday = prices[j] + onOneOfTheEarlierDays;
                T[i][j] = Math.max(skipThatday, sellOnThatday );
            }
        }
        return T[M-1][N-1];

    }

    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/submissions/
    public static int maxProfit_optimized(int[] prices, int k) {

        /*
            i = no. of transactions, j = no of days


            T[i][j] = T[i][j-1] skip transaction on jth day
                    = prices[j] +  From x=0 to j-1 Max(T[i-1][j-x] - profit[x])

         */
        if (prices.length == 0) return 0;
        int M = k+1;
        int N = prices.length;
        int [][]T = new int[M][N];
        // T[0][j] = 0 => 0 transactions on any day
        // T[k][0] = 0 => whatever # of transactions on 0th day or first day

        int onOneOfTheEarlierDays = Integer.MIN_VALUE;
        for (int i = 1; i < M; i++) {
            for (int j = 1; j < N; j++) {
                int skipThatday = T[i][j-1];

                //for (int x = 0; x < j; x++) {
                    onOneOfTheEarlierDays= Math.max(T[i-1][j-1] - prices[j-1], onOneOfTheEarlierDays);
                //}
               // if (onOneOfTheEarlierDays == Integer.MIN_VALUE) onOneOfTheEarlierDays = 0;
                int sellOnThatday = prices[j] + onOneOfTheEarlierDays;
                T[i][j] = Math.max(skipThatday, sellOnThatday );
            }
        }
        return T[M-1][N-1];

    }
}
/*
          1 10 3 8 9 10 =>  B1, S10, B3, S10 => 9+7=16
       0  0 0 0 0 0 0
       1  0 X
       2  0

       0
 */