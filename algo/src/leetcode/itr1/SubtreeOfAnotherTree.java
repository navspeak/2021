package leetcode.itr1;

import leetcode.TreeNode;

/*
572. Subtree of Another Tree
Given the roots of two binary trees root and subRoot,
return true if there is a subtree of root with the same structure and node values of subRoot and false otherwise.

A subtree of a binary tree tree is a tree that consists of a node in tree and all of this node's descendants.
The tree tree could also be considered as a subtree of itself.
 */

// Time O(M+N) Space: O(Min of m,n)
public class SubtreeOfAnotherTree {
    public static void main(String[] args) {

//        Integer[] a = {1,null,1,null,1,null,1,null,1,null,1,null,1,null,1, null,1,null,1,null,1,2};
//        Integer[] b = {1,null,1,null,1,null,1,null,1,null,1,2};
        Integer[] a = {10,null,20,null,30,null,1,null,2, 3};
        Integer[] b = {1,null,2,3};
        System.out.println(isSubtree(TreeNode.buildTree(a), TreeNode.buildTree(b)));
    }
    // This appears simple but look out for minor errors you are likely to make
    // A = || vs &&
    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null ) return false;
        if (isIdentical(root, subRoot) == true)
            return true;
        return isSubtree(root.left, subRoot) || //If you are not careful or thinking you may put &&
                isSubtree(root.right, subRoot); // also you need to recurse on isSubtree _NOT_ isIdentical
    }

    public static boolean isIdentical(TreeNode one, TreeNode two){
        if (one == null && two == null) return true;
        if (one == null || two == null) return false;
        if (one.val == two.val){
            return isIdentical(one.left, two.left) &&
                    isIdentical(one.right, two.right);
        } else return false;


    }
}


//class Solution {
//    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
//
//        if(root==null && subRoot==null){
//            return true;
//        }else if(root==null){
//            return false;
//        }else{
//            if(equal(root, subRoot)){
//                return true;
//            }else{
//                return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
//            }
//        }
//    }
//
//    private boolean equal(TreeNode root, TreeNode subRoot){
//        if(root!=null && subRoot!=null){
//            if(root.val != subRoot.val){
//                return false;
//            }else{
//                return equal(root.left, subRoot.left) && equal(root.right, subRoot.right);
//            }
//        }else if(root==null && subRoot!=null || root!=null && subRoot==null){
//            return false;
//        }else{
//            return true;
//        }
//    }
//}