package nav.recurison;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        int[] x = {7,8, 1,2,3,4,9};
        List<Integer> y = IntStream.of(x).boxed().collect(Collectors.toList());
       List<Integer> z = y.stream().filter(e -> e < y.get(0)).collect(Collectors.toList());
       int[] memo = new int[11];
       memo[0] = memo[1] = 1;
       System.out.println(numTrees(10, memo));
       System.out.println(Arrays.toString(memo));
    }

   static int numTrees(int n, int[] memo){
        if (memo[n] > 0) return memo[n];
        if (n <= 1 ) return 1;
        int count = 0;
        for (int left=0; left<n; left++){
            int right = n - left - 1;
            count+=numTrees(left, memo)*numTrees(right, memo);
        }
        memo[n] = count;
        return count;
   }

   /*
        n= 0 => 1
        n= 1 => 1
        n= 2 => f(0)f(1)+f(1)f(0) = 2
        n= 3 => f(0)f(2)+f(1)f(1)+f(2)f(0) = 5
        n= 4 => f(0)f(3)+f(1)f(2)+f(2)f(2)+f(3)f(0)= 14

    */
}
