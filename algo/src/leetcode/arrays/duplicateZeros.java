package leetcode.arrays;
//https://www.youtube.com/watch?v=io3N5YTFbxM&t=354s
public class duplicateZeros {
    //https://leetcode.com/problems/duplicate-zeros/submissions/
    public void duplicateZeros(int[] arr) {
        int zeros=0;
        for(int i=0; i<arr.length;i++){
            if (arr[i] == 0) {
                zeros++;
            }
        }
        int from = arr.length -1;
        int to = from + zeros;
        while ( from < to){
            insert(from, to, arr);
            to--;
            if (arr[from] == 0){
                insert(from, to, arr);
                to--;
            }
            from--;
        }
    }

    void insert(int from, int to, int[] arr){
        if (to >= arr.length) return;
        arr[to] = arr[from];
    }

    public static void main(String[] args) {
        new duplicateZeros().removeElement(new int[]{3,2,2,3}, 3);
    }

    public int removeElement(int[] nums, int val) {
        int i=0;
        for( ; i<nums.length;i++){
            if (nums[i] == val){
                int j = i+1;
                while (j<nums.length && nums[j] == val){
                    j++;
                }
                if (j >= nums.length) break;
                swap(nums, i, j);
            }
        }
        return i;
    }

    void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
// Do https://leetcode.com/problems/merge-sorted-array/
// Hint: use three pointer starting largest element
/*
https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 public int removeDuplicates(int[] nums) {
        if (nums.length == 0 || nums.length == 1) return nums.length;
        // 1 1 2 3 3 4
        // j i
        int j = 0;
        for (int i = 1;i<nums.length; i++){
            if (nums[i] != nums[j]){
                nums[++j] = nums[i];
            }
        }
        return j+1;
    }
 */