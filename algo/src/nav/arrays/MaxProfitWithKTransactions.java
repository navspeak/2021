package nav.arrays;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MaxProfitWithKTransactions {

    // won't work for K limited transactions - Wrong implementation. See correct in dp folder
    public static void main(String[] args) {
        int[] prices = {100, 99, 98, 97, 1}; //{5, 11, 3, 50, 60, 90}, 2
        System.out.println(maxProfitWithKTransactions(prices, 5));
    }

    public static int maxProfitWithKTransactions(int[] prices, int k) {
        if (prices.length == 0 || k == 0) return 0;
        Integer min = prices[0];
        Integer max = null;
        PriorityQueue<Integer> transactions = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 1; i <prices.length; i++){
            if (prices[i] < min){
                min = prices[i];
            } else if (prices[i] > min) {
                if (max == null) max = prices[i];
                max = Math.max(prices[i], max);
            }
            if (max != null &&
                    (i< prices.length -1 && prices[i+1] < prices[i] ||
                    i == prices.length - 1)
            ){
                //stack.push(new int[]{min, max})
                transactions.add(max - min);
                if (i < prices.length - 1) {
                    min = prices[i + 1];
                    max = null;
                }
            }
        }
        int ret = 0;
        for (int i=0; i<k; i++) {
            if (!transactions.isEmpty())
                ret = ret + transactions.remove();
        }
        return ret;
    }
}
