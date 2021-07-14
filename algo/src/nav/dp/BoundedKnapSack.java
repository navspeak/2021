package nav.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoundedKnapSack {
    public static void main(String[] args) {
        int[][] input = {{1, 2}, {4, 3}, {5, 6}, {6, 7}};
        int[][] expected = {{10}, {1, 3}};
        System.out.println(Arrays.deepToString(knapsackProblem(input, 10).toArray()));
    }
    public static List<List<Integer>> knapsackProblem(int[][] items, int capacity) {
        int[][] T = new int[items.length][capacity+1];
        int i =0, j =0;
        for(i = 0; i< items.length; i++){
            for(j = 1; j<= capacity; j++){
                int o1=0, o2=0;
                int wt = items[i][1];
                int val = items[i][0];
                if(wt <= j){
                    if (i > 0)
                        o1 = val +  T[i-1][j-wt];
                    else
                        o1 = val;
                }
                if (i> 0)
                    o2 = T[i-1][j];
                T[i][j] = Math.max(o1, o2);
            }
        }
        i--; j--;
        int maxProfit = T[i][j];
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> totalValue = Arrays.asList(maxProfit);
        List<Integer> finalItems = new ArrayList<>();
        while (i >= 0 && j >= 0){
            int wt = items[i][1];
            int val = items[i][0];
            if (i == 0){
                if (T[i][j] == val){
                    finalItems.add(i);
                }
                break;
            }
            if (T[i][j] == T[i-1][j]){
                i--;
            } else if (j >= wt && T[i][j] == T[i-1][j-wt]){
                j = j -wt;
            } else {
                finalItems.add(i);
                i = i - 1;
                j = j - wt;
            }
        }
        result.add(totalValue);
        result.add(finalItems);
        return result;
    }
}
