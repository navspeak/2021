package nav.dp;

import java.util.Arrays;

public class Jumps {

    // https://leetcode.com/problems/jump-game-ii/submissions/
    public int jump(int[] nums) {
        int[] jumps = new int[nums.length];
        Arrays.fill(jumps, jumps.length);
        jumps[0] = 0;

        for (int i = 1; i <  nums.length; i++) {
            for (int j = 0; j < i ; j++) {
                if (j + nums[j] >= i){
                    jumps[i] = Math.min(jumps[i], jumps[j]+1);
                    if (jumps[i] == 1) break;// optimization. Will work without this too
                }
            }
        }
        return jumps[nums.length - 1];
    }
}
