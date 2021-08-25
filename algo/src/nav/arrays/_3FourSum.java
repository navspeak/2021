package nav.arrays;

import java.util.*;
import java.util.LinkedList;

public class _3FourSum {

    public static void main(String[] args) {
        //List<Integer[]> res = fourNumberSum_(new int[]{7,6,4,-1,1,2}, 16);
        //List<Integer[]> res = fourNumberSum(new int[]{1,0,-1,0,-2,2}, 0);
        //List<List<Integer>> res = fourNumberSum(new int[]{2,2,2,2,2}, 8);
        System.out.println(Arrays.deepToString(threeSum(new int[]{-1,0,1,2,-1,-4}).toArray()));
        List<List<Integer>> res = fourSum(new int[]{-5,5,4,-3,0,0,4,-2}, 4);
        res.forEach(list -> System.out.println(Arrays.toString(list.toArray())));

    }


    public static List<List<Integer>> threeSum(int[] nums) {

        // On leetcode this ran slower
        // set.contains() can run O(n) in worst case
//         if (nums == null || nums.length < 3  )
//             return new ArrayList<>();

//         Set<Integer> set = new HashSet();
//         Set<List<Integer>> result = new HashSet<>();

//         for(int i = 0; i < nums.length; i++){
//             set.add(nums[i]);
//             for (int j = i + 2; j<nums.length; j++){
//                 int a = nums[i+1];
//                 int b = nums[j];
//                 if (set.contains(-(a+b))){
//                     List<Integer> triplets = Arrays.asList(a,b,-(a+b));
//                     triplets.sort(Comparator.naturalOrder());
//                     result.add(triplets);
//                 }
//             }
//         }
//         return List.copyOf(result);

        Arrays.sort(nums); // On leetcode it is faster
        Set<List<Integer>> result = new HashSet<>();
        for (int i = 0; i < nums.length; i++){
            int remSum = -1*nums[i];
            int p = i+1, q= nums.length -1;
            while(p < q){
                if (nums[p]+nums[q] == remSum){
                    result.add(Arrays.asList(nums[i], nums[p], nums[q]));
                    p++; q--;
                } else if (nums[p]+nums[q] < remSum){
                    p++;
                } else {
                    q--;
                }
            }
        }
        return List.copyOf(result);
    }
    /*
    {
  "array": [7, 6, 4, -1, 1, 2],
  "targetSum": 16
  7, 6, (4), -1, 1, 2

  13->[7,6]
  4+-1 = 3 => 16-3 => 13 => map.containsKey(13) => yes => 7,6,4,-1
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
  5 =>6,-1
  6=>7,-1


}

   at i = 1:
      13 -> 7,6

     */
    // Avg Time complexity O(n^2)
    // Worst say input array is [4,1,-1,4,3,-3] & target = 0,the second loop will have n pairs so in that case Time complexity = O(n^3)
     public static List<List<Integer>> fourSum(int[] array, int targetSum) {
        Map<Integer, List<List<Integer>>> map = new HashMap<>();
        Set<List<Integer>> result = new HashSet<>();
        for(int i = 1; i < array.length; i++){
            for(int j = 0; j<i; j++){
                final int a = array[j]; // a < b
                final int indexA = j;
                final int b = array[i];
                final int  indexB = i;

                map.compute(a+b, (k,v) -> {
                    if (v == null) {
                        v = new ArrayList<>();
                    }
                    v.add(Arrays.asList(a,b));
                    return v;
                });


            }

            for(int j = i + 2; j < array.length; j++){
                final int a = array[i+1];
                final int indexA = i+1;
                final int b = array[j];
                final int  indexB = j;
                int remSum = targetSum - a - b;
                if (map.containsKey(remSum)){
                    map.get(remSum).forEach( ints -> {
                        result.add(new ArrayList(Arrays.asList(ints.get(0), ints.get(1), a, b)));
                    });
                }
                while(j < array.length -1 && array[j] == array[j+1]) {
                    j++;
                }
            }

        }
        return List.copyOf(result);
    }

    // Not optimal
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



    // TIME: O(NlogN) + O(N^2)
    // SPACE: O(N)
}
/*
7 6 4 -1 1 2 => 16
x = 7
6 4 -1 1 2
y = 6
remSum = 3



*/

