package nav.arrays;

import java.util.*;
import java.util.stream.Collectors;

public class _1KadaneEtc {
    public int maxSubArray(int[] nums) {
/*
   [-2,1,-3,4,-1,2,1,-5,4]
  max      -2  -1   -1  [4 or -1+4] 3   5  6  1  5
 maxSofar -2  -1   -4   4          4   5  6  6  6
 [-2,1]
*/

        int subArraySum = nums[0];
        int maxSoFar = nums[0];
        for(int i=1; i<nums.length; i++){
            if (nums[i] > subArraySum + nums[i]) {
                subArraySum = nums[i];
            } else {
                subArraySum = subArraySum + nums[i];
            }

            maxSoFar = Math.max(maxSoFar, subArraySum );
        }
        return  maxSoFar;

    }

    /* 3Sum */

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
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
//    public List<List<Integer>> threeSum(int[] nums) {
//        if(nums == null || nums.length == 0) return Collections.emptyList();
//        Arrays.sort(nums);
//        Set<List<Integer>> result = new HashSet<>();
//        for (int i =0; i<nums.length; i++){
//            int remSum = -nums[i];
//            List<List<Integer>> twoSumList = twoSum(nums, i+1, nums.length-1, remSum);
//            if (twoSumList.size() > 0) {
//                for (List<Integer> list: twoSumList){
//                    list.add(nums[i]);
//                    result.add(list);
//                }
//            }
//        }
//
//        return List.copyOf(result);
//    }
//
    public  List<List<Integer>> fourNumberSum(int[] array, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(array);
        for(int i=0; i < array.length; i++){
            int x = array[i];
            for(int j=i+1; j < array.length; j++){
                int y = array[j];
                int remSum = targetSum - x - y;
                List<List<Integer>> twoSum = twoSum(array, j+1, array.length - 1, remSum);
                if (twoSum.size() > 0){
                    twoSum.forEach((otherTwo) -> result.add(Arrays.asList(x,y,otherTwo.get(0),otherTwo.get(1))));
                }
            }
        }
        return result;
    }

    private List<List<Integer>> twoSum(int[] nums, int i, int j, int sum){
        List<List<Integer>> result = new ArrayList<>();
        while(i<j){
            if (nums[i]+nums[j] == sum){
                result.add(new ArrayList(Arrays.asList(nums[i], nums[j])));
                i++;
            } else if (nums[i]+nums[j] > sum) {
                j--;
            } else {
                i++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        _1KadaneEtc prog = new _1KadaneEtc();
        System.out.println(Arrays.deepToString(prog.threeSum(new int[]{-1,0,1,2,-1,-4}).toArray()));
    }
}


