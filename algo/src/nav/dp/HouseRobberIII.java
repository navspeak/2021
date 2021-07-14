package nav.dp;



public class HouseRobberIII {
    /**
     * Definition for a binary tree node. */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

    }

    public int rob(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return root.val;
        int[] ret = rob_helper(root);
        return Math.max(ret[0], ret[1]);

        /*
                3
            2        3
         5     6        7

         5,0  6,0 2    0+0+2, 5+6=> 2,11
         0,0  0,0 7   7,0
         7,0 0,0 3    0+0+3, 7 => 3,7

         3+11+7, 5

        */

    }
    public int[] rob_helper(TreeNode root) {
        if (root == null) return new int[]{0,0};
        if (root.left == null && root.right == null) return new int[]{root.val,0};

        int[] left = rob_helper(root.left);
        int[] right = rob_helper(root.right);
        // [R1, l1] [R2, ]

        return new int[]{
                left[1] + right[1] + root.val,
                Math.max(left[0], left[1])+
                        Math.max(right[0], right[1])
        };
    }

}
