package nav.dp;

import java.util.TreeMap;

/*
https://www.youtube.com/watch?v=-w67-4tnH5U
Description
There are a row of n houses, each house can be painted with one of the three colors: red, blue or green.
 The cost of painting each house with a certain color is different.
 You have to paint all the houses such that no two adjacent houses have the same color, and you need to cost the least.
  Return the minimum cost.

The cost of painting each house with a certain color is represented by a n x 3 cost matrix.
 For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so on...
  Find the minimum cost to paint all houses.

All costs are positive integers.

Example
Example 1:

Input: [[14,2,11],[11,14,5],[14,3,10]]
Output: 10
Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue. Minimum cost: 2 + 5 + 3 = 10.
Example 2:

Input: [[1,2,3],[1,4,6]]
Output: 3
 */
public class HousePaint {
    public static void main(String[] args) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(5,3);
        if (map.ceilingEntry(5) == null){
            System.out.println("nothing greater than or equal to 5");
        } else {
            System.out.println(map.ceilingEntry(5).getValue());
        }
        int[][] costs0 = {{17,2,17},{16,16,5},{14,3,19}};
        System.out.println(minCost(costs0));
        int[][] costs1 = {{14,2,11},{11,14,5},{14,3,10}};
        System.out.println(minCost(costs1));
        int[][] costs2 = {{1,2,3},{1,4,6}};
        System.out.println(minCost(costs2));
    }
    public static int minCost(int[][] costs) {
        /*
        costs or input
                    Color
                    R   G    B
        Houses 0   10   5    9
               1   8    7    5
               2   1    7    6

        variables: House# and Color#

                    Color
                    R   G    B
        Houses 0   14   5    9
               1   13                              T[i = 1][j = R] = I[i][j] + Min{ T[i-1][j] where j iterates from 0 to 3 leaving 0
               2


         */
        int[][] T = new int[costs.length][costs[0].length];
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < costs.length; i++) {
            for (int j = 0; j < costs[0].length ; j++) {
                if (i == 0){
                    T[i][j] = costs[i][j];
                    continue;
                }
                int costAtThisColor = costs[i][j];
                int minCostForLastHouse = Integer.MAX_VALUE;
                for (int k = 0; k < 3; k++) {
                    if (k == j) continue;
                    minCostForLastHouse = Math.min(minCostForLastHouse, T[i-1][k] );
                }
                T[i][j] = costAtThisColor + minCostForLastHouse;
                if (i == costs.length-1)
                    minCost = Math.min(T[i][j], minCost);
            }
        }
        return minCost;
    }
}
