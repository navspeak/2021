package nav.arrays;

import java.util.*;
import java.util.stream.Collectors;

public class _1KadaneEtc {
    public int maxSubArray(int[] nums) {
/*
1. have a global and local maxSum (or sumSoFar) = a[0]
2. At every element add that to local maxsum.
    -If that is less that a[i], we can start new subarray at a[i]
    -Else local maxSum is previous local MaxSum + a[i]
    -Make globalMax = localmaxSum if it is less than localmaxSum

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

   // works for sorted array
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

    }
}


