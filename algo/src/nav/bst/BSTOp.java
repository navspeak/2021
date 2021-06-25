package nav.bst;

import java.util.ArrayList;
import java.util.List;
//inorder -> ascending order
public class BSTOp {
    static class BST {
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
        }

        public BST insert(int value) {
            return insert(this, value);
        }

        private BST insert(BST node, int val) {
            if (node == null) return new BST(val);
            if (val > node.value)
                node.right = insert(node.right, val);
            else
                node.left = insert(node.left, val);
            return node;
        }

        public boolean contains(int value) {
            BST node = search(this, value);
            return node != null;
        }

        private static BST search(BST node, int val){
            if (node == null || node.value == val) return node;
            if (val < node.value)
                return search(node.left, val);
            else
                return search(node.right, val);
        }

        public BST remove(int value) {
            return delete(this, value );
        }

        private static BST delete(BST node, int val){
            if (node == null) return null;
            if (val < node.value){
                node.left = delete(node.left, val);
            } else if (val > node.value){
                node.right = delete(node.right, val);
            } else {
                if (node.left == null && node.right == null) {
                    return null;
                } else if (node.right == null){
                    return node.left;
                } else if (node.left == null){
                    return node.right;
                }

                BST ptr = node.left;
                while (ptr.right!= null) { // just smaller than val
                    ptr = ptr.right;
                }
                int tmp = ptr.value;
                ptr.value = val;
                node.value = tmp;
                node.left = delete(node.left, val);
            }
            return node;
        }

    }

    public static int findKthLargestValueInBst(BST tree, int k) {
        List<Integer> res = new ArrayList<>();
        helper(tree, k, res);
        return res.get(k-1);
    }

    public static void helper(BST tree, int k, List<Integer> res) {
        if (tree == null) return;
        helper(tree.right,k, res);
        res.add(tree.value);
        if (res.size() < k)
            helper(tree.left, k, res);
    }
    public static boolean validateBst(BST tree) {
        return validateBst(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean validateBst(BST tree, int min, int max) {
        if (tree == null) return true;
        if (tree.value >= min && tree.value < max){
            boolean leftCheck = validateBst(tree.left, min, tree.value);
            if (leftCheck == false) return false;
            boolean rightCheck = validateBst(tree.right, tree.value, max);
            return rightCheck;
        }
        return false;

    }

    public static void main(String[] args) {
        BST root = new BST(10);
        root.left = new BST(5);
        root.left.left = new BST(2);
        root.left.left.left = new BST(1);
        root.left.right = new BST(5);
        root.right = new BST(15);
        root.right.left = new BST(13);
        root.right.left.right = new BST(14);
        root.right.right = new BST(22);

        System.out.println(findKthLargestValueInBst(root, 3));

        root.insert(12);
        System.out.println(root.right.left.left.value == 12);

        root.remove(10);
        System.out.println(root.contains(10) == false);
        System.out.println(root.value == 5); // can also be 12

        System.out.println(root.contains(15));
        List<Integer> l = new ArrayList<>();

    }
}
