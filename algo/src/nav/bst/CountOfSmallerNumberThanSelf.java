package nav.bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//MergeSort Merge Sort
public class CountOfSmallerNumberThanSelf {
    //https://leetcode.com/problems/count-of-smaller-numbers-after-self/
    // TimeExceeded in leetcode
    public static void main(String[] args) {
        CountOfSmallerNumberThanSelf obj = new CountOfSmallerNumberThanSelf();
        System.out.println(Arrays.toString(obj.countSmaller(new int[]{8,5,11,-1,3,4,2}).toArray()));
    }

    // accepted by leetcode
    private static class Element implements  Comparable<Element> {
        int val;
        int index;

        public Element(int val, int index) {
            this.val = val;
            this.index = index;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "val=" + val +
                    ", index=" + index +
                    '}';
        }

        @Override
        public int compareTo(Element o) {
            return Integer.compare(this.val, o.val);
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        Element[] elements = new Element[nums.length];
        Integer[] result = new Integer[nums.length];
        Arrays.fill(result, 0);

        for (int i = 0; i < nums.length; i++) {
            elements[i] = new Element(nums[i], i);
        }
        sort(elements, 0, nums.length -1, result);
        return List.of(result);

    }


    public void sort(Element[] elements, int l, int r, Integer[] result){
        if (l >= r) return;
        int mid = l + (r - l)/2;
        sort(elements, l, mid, result);
        sort(elements, mid+1, r, result);
        merge(elements, l, mid, mid+1, r, result);
    }

    public void merge(Element[] elements, int sL, int eL, int sR, int eR, Integer[] result){
        Element[] tmp = new Element[eL-sL+1+eR-sR+1];
        int i = sL, j = sR, k = 0, c = 0;
        while (i <= eL && j <= eR){
           if (elements[i].compareTo(elements[j]) > 0 ){
               tmp[k++] = elements[j++];
               c++;
           } else {
               tmp[k++] = elements[i];
               result[elements[i++].index] += c;
           }
        }
        while (i <= eL){
            tmp[k++] = elements[i];
            result[elements[i++].index] += c;
        }
        while (j <= eR){
            tmp[k++] = elements[j++];
        }
//        System.arraycopy(elements, i, tmp, k, eL-i+1);
//        System.arraycopy(elements, j, tmp, k, eR-j+1);
        System.arraycopy(tmp, 0, elements, sL, tmp.length);
    }

    public List<Integer> countSmaller__(int[] nums) {
       Integer[] result = new Integer[nums.length];

        TreeNode tree = insert(nums[nums.length -1], null, result, nums.length -1, 0);
        for (int i = nums.length -2; i >=0; i--){
            insert(nums[i], tree, result, i, 0);
        }
        return List.of(result);

    }

    TreeNode insert(int val, TreeNode node, Integer[] result, int index, int count){
        if (node == null){
            result[index] = count;
            return new TreeNode(val, 0, 0);
        }
        int left = node.numLeft;
        int right = node.numRight;
        if (val > node.val){
            count = count + left+1;
            node.numRight = node.numRight + 1;
            node.right = insert(val, node.right, result, index, count);
        } else {
            node.numLeft = node.numLeft + 1;
            node.left = insert(val, node.left, result, index, count);
        }
        return node;
    }

    private static class TreeNode {
        int val;
        int numLeft;
        int numRight;
        TreeNode left;
        TreeNode right;
        TreeNode(){};
        TreeNode(int val, int nL, int nR){
            this.val = val;
            this.numLeft = nL;
            this.numRight = nR;
        }
    }
}
