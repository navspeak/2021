package nav.bst;

public class DiameterOfBinaryTree {
    public int diameterOfBinaryTree(TreeNode root) {
        int[] ret = helper(root);
        return ret[0];
    }

    public int[] helper(TreeNode root) {

        if (root == null) return new int[]{0,0}; //d,h

        int[] l = helper(root.left);
        int[] r = helper(root.right);

        int longestPathThruRoot = l[1]+r[1];
        int maxDiameterSoFar = Math.max(l[0], r[0]);

        int currentHeight = Math.max(l[1], r[1]) + 1;
        int currentDiameter = Math.max(longestPathThruRoot, maxDiameterSoFar);
        return new int[]{currentDiameter,currentHeight};


    }

    static class TreeNode {
        TreeNode left, right;
        int val;
    }
}
