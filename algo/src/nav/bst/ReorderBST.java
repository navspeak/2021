package nav.bst;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
// https://leetcode.com/problems/number-of-ways-to-reorder-array-to-get-same-bst/
public class ReorderBST {
    public static void main(String[] args) {
//        System.out.println((new ReorderBST()).numOfWays(new int[]{3,4,5,1,2}));
//        System.out.println((new ReorderBST()).numOfWays(new int[]{2,3,1}));
        System.out.println((new ReorderBST()).numOfWays(new int[]{9,4,2,1,3,6,5,7,8,14,11,10,12,13,16,15,17,18}));
    }
    private static long MOD = 1000000007;
    private static long[][] pascalsTriangle;
    public int numOfWays(int[] nums) {
        int n = nums.length;
        pascalsTriangle = new long[n][n];
        for(int i = 0; i < n; i++){
            pascalsTriangle[i][0] = 1;
            pascalsTriangle[i][i] = 1;
        }
        for(int i = 2; i < n; i++){
            for (int j = 1; j < n; j++){
                pascalsTriangle[i][j] = (pascalsTriangle[i-1][j-1] + pascalsTriangle[i-1][j])%MOD;
            }
        }

        return (int) numOfWays(IntStream.of(nums).boxed().collect(Collectors.toList())) -1;
    }

    private long numOfWays(List<Integer> nums) {
        if (nums.size() <= 1) return 1;
        List<Integer> left = nums.stream().filter(e -> e < nums.get(0)).collect(Collectors.toList());
        List<Integer> right = nums.stream().filter(e -> e > nums.get(0)).collect(Collectors.toList());
        return pascalsTriangle[nums.size()-1][left.size()]  * numOfWays(left) % MOD * numOfWays(right) % MOD;
    }

}
