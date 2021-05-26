package nav.recurison;

import java.util.*;

public class _6BinaryTree {
    /**
     * Definition for a binary tree node. */
    public static class TreeNode {
        String val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(String val) { this.val = val; }
        TreeNode(String val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /* Count no of BSTs - nth catalan */
    /* F(0) = 1
    F(1) = 1
    F(2) = F(0)F(1) + F(1)(0) =2
    F(n) = F(0)F(n-1)+F(1)F(n-2)+....+F(n-1)F(0) */
    //O(n^2)
    public int numTrees(int n) {
        if (n <= 1) return 1;
        int [] F = new int[n+1];
        F[0] = F[1] = 1;
        int result;
        for (int i = 2; i <= n; i++){
            result = 0;
            for(int j = 0; j <i; j++){
                result += F[j]*F[i-1-j];
            }
            F[i] = result;
        }
        return F[n];
    }

    public static void main(String[] args) {
        TreeNode A = new TreeNode("A");
        TreeNode B = new TreeNode("B");
        TreeNode C = new TreeNode("C");
        TreeNode D = new TreeNode("D");
        TreeNode E = new TreeNode("E");
        TreeNode F = new TreeNode("F");
        TreeNode G = new TreeNode("G");
        TreeNode H = new TreeNode("H");
        TreeNode I = new TreeNode("I");
        A.left = B; A.right = C;
        B.left = D; B.right =E;
        D.left = H; D.right = I;
        C.left = F; C.right = G;
        List<String> paths =  binaryTreePaths(A);
        System.out.println(Arrays.toString(paths.toArray()));
        TreeNode lowestCommonManager = getLowestCommonManager(A,E,I);
        System.out.println(lowestCommonManager.val);
         lowestCommonManager = getLowestCommonManager(A,E,G);
        System.out.println(lowestCommonManager.val);
        lowestCommonManager = getLowestCommonManager(A,H,D);
        System.out.println(lowestCommonManager.val);
        lowestCommonManager = getLowestCommonManager(A,B,H); // Important use case -> here B is the manager
        System.out.println(lowestCommonManager.val);
        System.out.println("========");
        lowestCommonManager = getLowestCommonManager_(A,E,I);
        System.out.println(lowestCommonManager.val);
        lowestCommonManager = getLowestCommonManager_(A,E,G);
        System.out.println(lowestCommonManager.val);
        lowestCommonManager = getLowestCommonManager_(A,H,D);
        System.out.println(lowestCommonManager.val);
        lowestCommonManager = getLowestCommonManager_(A,B,H); // Important use case -> here B is the manager
        System.out.println(lowestCommonManager.val);
    }

    public static List<String>  binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        helper(root, new ArrayList<>(), paths);
        return paths;

    }



    /*
                  A
              B      C
           D    E   F  G
         H   I(1)

         a b d i
         a c f

         a b e
         a b d i
     */

    public static TreeNode getLowestCommonManager_(TreeNode currNode, TreeNode reportOne, TreeNode reportTwo){
        if (currNode == null) return null;
        if (currNode == reportOne || currNode == reportTwo) return currNode;
        TreeNode left = getLowestCommonManager_(currNode.left, reportOne, reportTwo);
        TreeNode right = getLowestCommonManager_(currNode.right, reportOne, reportTwo);
        if (left != null && right!= null){
            return currNode;
        } else if (left!=null){
            return left;
        } else if (right !=null){
            return right;
        } else {
            return null;
        }
    }



    public static TreeNode getLowestCommonManager(TreeNode topManager, TreeNode reportOne, TreeNode reportTwo){
        Deque<TreeNode> h1 = getHierarchy(topManager, reportOne); // 0 contains to top Manager
        Deque<TreeNode> h2 = getHierarchy(topManager, reportTwo); // 0 contains to top Manager
        TreeNode manager = null;
        while(!h1.isEmpty() && !h2.isEmpty()){
            TreeNode one = h1.poll();
            TreeNode two = h2.poll();
            if (one == two ){
                manager = one;
            }
        }
        return manager;
    }

    private static Deque<TreeNode> getHierarchy(TreeNode head, TreeNode node){
        Deque<TreeNode> hierarchy = new ArrayDeque<>();
        getHierarchy(head, node, hierarchy);
        return hierarchy;
    }

    private static boolean getHierarchy(TreeNode head, TreeNode node, Deque<TreeNode> hierarchy){
        if (head == null) return false;
        hierarchy.add(head);
        if (head == node) return true; // the node must be in path because that too can be the manager
        if (getHierarchy(head.left, node, hierarchy) == true)
            return true;
        if (getHierarchy(head.right, node, hierarchy) == true)
            return true;
        hierarchy.removeLast();
        return false;
    }

    private static void helper(TreeNode root, List<String> currPath, List<String> paths){
        if (root == null) return;
        currPath.add(root.val);
        helper(root.left, currPath, paths);
        helper(root.right, currPath, paths);
        String str = "";
        if (root.left == null && root.right == null){
            for(int i=0; i< currPath.size(); i++){
                if (i == currPath.size() - 1)
                    str = str + currPath.get(i);
                else
                    str = str + currPath.get(i) + "->";
            }
            paths.add(str);
        }
        currPath.remove(currPath.size() - 1);

    }

}

