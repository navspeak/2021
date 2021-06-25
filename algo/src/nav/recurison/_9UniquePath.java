package nav.recurison;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class _9UniquePath {

    //https://leetcode.com/problems/unique-paths/submissions/
    // m = 3 & n = 2 => 3
//    Input: m = 3, n = 7
//    Output: 28
//    Example 2:
//
//    Input: m = 3, n = 2
//    Output: 3
//
//    Input: m = 7, n = 3
//    Output: 28
//    Example 4:
//
//    Input: m = 3, n = 3
//    Output: 6
    public int uniquePaths(int m, int n) {
        Map<String, Integer> memo = new HashMap<>();
        int paths = helper(0,0,m-1, n-1, memo); // Not 0 based. Start is 1,1
        return paths;
    }

    private int helper(int x, int y, int M, int N, Map<String, Integer> memo){
        String key = x+ "-"+y;
        if (memo.containsKey(key)) return memo.get(key);
        if(x > M || y > N) {
            return 0;
        }
        if (x == M && y == N){
            return 1;
        }

        int count = helper(x,y+1, M, N, memo) + helper(x+1, y, M, N, memo);
        memo.put(key, count);
        return count;
    }

    public int uniquePathsDP(int m, int n) {
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            dp[i][0] = 1;
        }

        for(int i = 0; i < n; i++){
            dp[0][i] = 1;
        }

        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }

    public static long uniquePathsFormula(int m, int n) {
        int rights = m - 1;
        int downs = n - 1;
        int total = rights + downs;
        //total choose rights = t!/r!d!
        long a = factorial(total);
        long b = factorial(rights);
        long c = factorial(downs);
        return a/(b*c);
    }

     static long factorial(int n){
        if (n == 1) return 1;
        return n*factorial(n-1);
    }

    public static void main(String[] args) {
        System.out.println(uniquePathsFormula(10,10));
    }
}
