package nav.misc;

import java.util.*;
// Next greatest
public class NextHighest {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new NextHighest().dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
    }

    public int[] dailyTemperatures(int[] temperatures) {

        if (temperatures.length == 1) return new int[]{0};
        int[] result = new int[temperatures.length];
        Deque<Integer> stack = new ArrayDeque<>();
        Map<Integer, Integer> mapOfNxtGreatest = new HashMap<>();
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]){
                mapOfNxtGreatest.put(stack.pop(), i);
            }
            stack.push(i);
        }

        for (int i = 0; i < temperatures.length ; i++) {
            if (mapOfNxtGreatest.containsKey(i))
                result[i] = mapOfNxtGreatest.get(i) - i;
            else
                result[i] = 0;
        }


        return result;

    }
}


//result[temperatures.length - 1] = temperatures.length - 1;
//        TreeMap<Integer, Integer> map = new TreeMap<>();// value to Index map;
//        map.put(temperatures[temperatures.length - 1], temperatures.length - 1);
//
//
//                 0 1 2 3 4 5
//                 ===========
//                 9 3 4 6 10 2
//
//                 4 1 1 1 0 0
//
//        Brute Force:
//        1. Iterate thru the list using i pointer
//        2. For each i, check if arr[j] > a[i] and result[i] = j-1 ; j = i+1 to end of array
//           - if j is out of the bounds, result[i] = 0
//        3. O(N^2) = Bad
//
//        Can we do better? - O(nlogn) - Sorting or O(n) ---  or even O(logn)?? X
//        - We will need to preserve the index along with values
//
//        Linear X
//        - Two traversal
//
//                [3|1 4|2 6|3 10|4 5|2       ] Index|value = TreeMap
//
//                 0 1 2 3 4  5
//                 ============
//                 9 3 4 6 10 2
//                 4 2 3 4 4  5
//                 ------------
//
//
//                arr[i]< maximums so far
//
//                 0 1 2 3 4  5 index
//                 ============
//                 9 3 4 6 10 2 values
//                 4 2 3 4 4 5
//                 0 1 1 1 0 0
//
//                2->5, 10->4, 6->3, 4->2
//
//        Time: O(nlogn)
//        Space O(n)
//
//
//        won't work => we will need to use List


//        for (int i = temperatures.length - 2; i>=0; i--){
//                if (map.ceilingEntry(temperatures[i]) == null){
//                result[i]=i;
//                }else {
//                result[i] = map.ceilingEntry(temperatures[i]).getValue();
//                }
//                map.put(temperatures[i], i);
//                }
//
//                for (int i = 0; i < temperatures.length; i++ ){
//        result[i] = result[i] -i;
//        }
