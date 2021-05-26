package nav.arrays;

import java.util.*;
import java.util.LinkedList;

public class _3FourSum {
    /*
    {
  "array": [7, 6, 4, -1, 1, 2],
  "targetSum": 16
  7, 6, (4), -1, 1, 2

  13->[7,6]
  4+-1 = 3 => 16-3 => 13 => map.containsKey(16) => yes => 7,6,4,-1
 4+1=5 => 16-4 = 11 => map.containsKey(11) => no
 ==
   7, 6, 4, (-1), 1, 2
         -
  13 => 7,6
  10 => 6,4
  11 => 7,4
  ==
     7, 6, 4, -1, (1), 2 => 7,6,1,2
              -
  13 => 7,6
  10 => 6,4
  11 => 7,4
  3 => 3
  5 =>6,1
  6=>7,1


}

   at i = 1:
      13 -> 7,6

     */
    public static List<Integer[]> fourNumberSum(int[] array, int targetSum) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        List<Integer[]> result = new ArrayList<>();
        for(int i = 1; i < array.length; i++){
            for(int j = i - 1; j >=0; j--){
                final int a = array[j];
                final int b = array[i];
                map.compute(a+b, (k,v) -> {
                    if (v == null) {
                        v = new ArrayList<>();
                    }
                    v.add(new int[]{a,b});
                    return v;
                });
            }

            for(int j = i + 2; j < array.length; j++){
                final int a = array[i+1];
                final int b = array[j];
                int remSum = targetSum - a - b;
                if (map.containsKey(remSum)){
                    map.get(remSum).forEach( ints -> result.add(new Integer[]{ints[0], ints[1], a, b}));
                }
            }
        }

        return result;
    }

    public static List<Integer[]> fourNumberSum_(int[] array, int targetSum) {
        List<Integer[]> result = new LinkedList<>();
        Arrays.sort(array);
        for(int i=0; i < array.length; i++){
            int x = array[i];
            for(int j=i+1; j < array.length; j++){
                int y = array[j];
                int remSum = targetSum - x - y;
                List<int[]> twoSum = twoNumberSum(array, remSum, j+1);
                if (twoSum.size() > 0){
                    twoSum.forEach((otherTwo) -> result.add(new Integer[]{x,y,otherTwo[0],otherTwo[1]}));
                }
            }
        }
        return result;
    }

    private static List<int[]>twoNumberSum(int[] array, int targetSum, int start){
        List<int[]> result = new ArrayList<>();
        for(int i = start, j = array.length - 1; i< j && j > 0; ){
            if (array[i]+array[j] == targetSum){
                result.add(new int[]{array[i], array[j]});
                i++; j--;
            } else if (array[i]+array[j] < targetSum) {
                i++;
            } else {
                j--;
            }
        }
        return result;
    }

    // TIME: O(NlogN) + O(N^3)
    // SPACE: O(N)
}
/*
7 6 4 -1 1 2 => 16
x = 7
6 4 -1 1 2
y = 6
remSum = 3



*/

