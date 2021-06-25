package nav.bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Reconstruct {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(10,4,2,1,5,17, 19, 18);


        Integer [] arr = list.stream().toArray(Integer[]::new);
        //int[] arr = {10,4,2,1,5,17, 19, 18};
        List<Integer> curr = new ArrayList<>(1);
        curr.add(0);
        BSTOp.BST ret = reconstructBst(arr,  curr, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println();
    }

    /*                                                     We need to keep track of it globally               */
    private static BSTOp.BST reconstructBst(Integer[] arr, List<Integer> currRootNodeIndex, int min, int max) {
        Integer i = currRootNodeIndex.get(0);
        if (i == arr.length) return null;
        if (arr[i] < min || arr[i] >= max) return null;
        BSTOp.BST root = new BSTOp.BST(arr[i]);
        currRootNodeIndex.set(0,i+1);
        root.left = reconstructBst(arr, currRootNodeIndex, min, arr[i]);
        root.right = reconstructBst(arr, currRootNodeIndex,  arr[i], max);
        return root;
    }
}
