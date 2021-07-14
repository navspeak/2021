package nav.dp;

//https://leetcode.com/problems/house-robber-ii/submissions/
public class HouseRobberII {
    public int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        return Math.max(robLinear(nums, 0), robLinear(nums,nums.length-1));

    }
    public int robLinear(int[] nums, int excludeIndex) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int[] T = new int[nums.length];

        if (excludeIndex == 0){
            T[0] = 0;
            T[1] = nums[1];
        } else {
            T[0] = nums[0];
            T[1] = Math.max(nums[0], nums[1]);
        }

        for(int i=2; i<nums.length;i++){
            T[i] = Math.max(T[i-1], T[i-2]+nums[i]);
        }

        return excludeIndex == nums.length-1? T[nums.length-2] : T[nums.length - 1];

    }

}
