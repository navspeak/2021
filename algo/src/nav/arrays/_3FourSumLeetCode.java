package nav.arrays;


import java.util.*;

public class _3FourSumLeetCode {
    public static void main(String[] args) {
        int[] nums = {1,0,-1,0,-2,2};
        Set<List<Integer>> set = new HashSet<>();
        set.add( Arrays.asList(1,2,3));
        set.add( Arrays.asList(1,2,3));
        set.add( Arrays.asList(1,2,3));

        List<List<Integer>> lists = new _3FourSumLeetCode().fourSum(nums, 0);
        System.out.println(Arrays.deepToString(lists.toArray()));
    }
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        int k = 4;
        List<List<Integer>> ret = kSumRec(nums, 0, target, k);
        return ret;
    }

    private List<List<Integer>> kSumRec(int[] nums, int startIndex, int target, int k ){
        if (nums.length - startIndex < k) {
            return new ArrayList<>();
        }
        if (k == 2){
            return twoSum(nums, startIndex, target);
        }
        List<List<Integer>> ans = new ArrayList<>();
        /*
           k = 4;
           len = 5
           0 1 2 3 4
           0 [1 2 3 4]
           0 1 [2 3 4]  i < (len -1) -(k-1) + 1 = 4-3 +1 = 2 => i < len - k + 1
         */
        for (int i = startIndex; i < nums.length - k +1 ; i++) {
            int curr = nums[i];
            int rem = target - curr;
            List<List<Integer>> ret = kSumRec(nums, i + 1, rem, k - 1);
            if (ret.size() > 0) {
                for (List<Integer> one : ret) {
                    one.add(0, curr);
                }
                ans.addAll(ret);
            }
            while(i < nums.length - k +1 && nums[i] == nums[i+1]) {
                i++;
            }
        }
        return ans;

    }

    private List<List<Integer>> twoSum(int[] nums, int startIndex, int target){
        int i = startIndex, j = nums.length -1;
        List<List<Integer>> ret = new ArrayList<>();
        while (i < j){
            int sum = nums[i]+nums[j];
            if (sum == target){
                ret.add(new ArrayList(Arrays.asList(nums[i], nums[j])));
                //Handle duplicate combinations
                //Remember array is sorted
                while (i < j && nums[i] == nums[i+1])
                    i++;
                while (i < j && nums[j] == nums[j-1])
                    j--;
                i++; j--;
            } else if (sum < target){
                i++;
            } else {
                j--;
            }
        }
        return ret;
    }
}
//    class Solution {
//        public List<List<Integer>> fourSum(int[] nums, int target) {
//            Arrays.sort(nums);
//            return kSum(nums, target, 0, 4);
//        }
//
//        public List<List<Integer>> kSum(int[] nums, int target, int start, int k) {
//            List<List<Integer>> res = new ArrayList<>();
//            if (start == nums.length || nums[start] * k > target || target > nums[nums.length - 1] * k)
//                return res;
//            if (k == 2)
//                return twoSum(nums, target, start);
//
//            for (int i = start; i < nums.length; ++i)
//                if (i == start || nums[i - 1] != nums[i])
//                    for (List<Integer> subset : kSum(nums, target - nums[i], i + 1, k - 1)) {
//                        res.add(new ArrayList<>(Arrays.asList(nums[i])));
//                        res.get(res.size() - 1).addAll(subset);
//                    }
//
//            return res;
//        }
//
//        public List<List<Integer>> twoSum(int[] nums, int target, int start) {
//            List<List<Integer>> res = new ArrayList<>();
//            Set<Integer> s = new HashSet<>();
//
//            for (int i = start; i < nums.length; ++i) {
//                if (res.isEmpty() || res.get(res.size() - 1).get(1) != nums[i])
//                    if (s.contains(target - nums[i]))
//                        res.add(Arrays.asList(target - nums[i], nums[i]));
//                s.add(nums[i]);
//            }
//
//            return res;
//        }
//    }
//}
