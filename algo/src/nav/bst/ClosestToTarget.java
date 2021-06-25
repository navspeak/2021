package nav.bst;

public class ClosestToTarget {
    public static int findClosestValueInBst(BST tree, int target) {
        return helper(tree, target, tree.value);
    }

    private static int helper(BST tree, int target, int closest) {

		/*
		    100                  =>99
		98       101
		  99.5
		     100

		*/
        if (tree == null) return closest;
        int prevDiff = Math.abs(target - closest);
        int currDiff = Math.abs(target - tree.value);
        if (prevDiff > currDiff) closest = tree.value;
        if (target > tree.value) {
            return helper(tree.right, target, closest);
        } else {
            return helper(tree.left, target, closest);
        }

    }

    static class BST {
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
        }
    }
}
