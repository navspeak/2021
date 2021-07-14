package nav.graph;

import java.util.Arrays;

class RangeSumQuery {
    public static void main(String[] args) {
//        Input
//                ["NumArray", "sumRange", "update", "sumRange"]
//[[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
//        Output
//                [null, 9, null, 8]
//
//        Explanation
//        NumArray numArray = new NumArray([1, 3, 5]);
//        numArray.sumRange(0, 2); // return 1 + 3 + 5 = 9
//        numArray.update(1, 2);   // nums = [1, 2, 5]
//        numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8

        for (int i = 0; i < 20; i++) {
            System.out.println(i + " - "+ (new RangeSumQuery(new int[]{1,2})).nextpowof2(i) + " - " + new RangeSumQuery(new int[]{1,2}).nextpowof2_(i));
        }
    }
    private int[] segTree;
    private int[] nums ;
    private int length = 0;

    public RangeSumQuery(int[] nums) {
        if (nums == null || nums.length == 0){
            return;
        }
        this.length = nums.length;
        this.nums = Arrays.copyOf(nums, nums.length);
        int len = 2*nextpowof2(nums.length)+1;
        segTree = new int[len];
        createSegTree(nums, 0, nums.length -1, 0);
    }

    private int createSegTree(int[] nums, int lo, int hi, int pos){
        if(lo == hi){
            segTree[pos] = nums[lo];
            return nums[lo];
        }
        int mid = lo + (hi - lo)/2;
        segTree[pos] = createSegTree(nums, lo, mid, 2*pos + 1) +
                createSegTree(nums, mid+1, hi, 2*pos + 2);
        return segTree[pos];
    }

    public void update(int i, int val) {
        if (length == 0) return;
        int diff = val - nums[i];
        nums[i] = val;
        update(i, diff, 0, nums.length -1, 0);
    }

    private void update(int i, int diff, int numsLeft, int numsRight, int pos){
        if (i > numsRight || i < numsLeft) return;
        segTree[pos] +=diff;
        if (numsLeft == numsRight) return;
        int mid = numsLeft + (numsRight - numsLeft)/2;;
        update(i, diff, numsLeft, mid, 2*pos+1);
        update(i, diff, mid+1, numsRight, 2*pos+2);

    }

    public int sumRange(int i, int j) {
        return length != 0 ? getSum(i,j, 0, nums.length - 1, 0): 0;
    }

    private int getSum(int i, int j, int numsLeft, int numsRight, int pos) {
        if (i<=numsLeft && j>=numsRight)
            return segTree[pos];
        if (i>numsRight || j<numsLeft)
            return 0;
        int mid = numsLeft + (numsRight - numsLeft)/2;
        return getSum(i,j,numsLeft,mid, 2*pos+1) + getSum(i,j,mid+1, numsRight, 2*pos+2);
    }

    private int nextpowof2(int n){
        n--;
        for(int i =0; i<=4;i++){
            n = n | (n>>(1<<i));
        }
        return ++n;
    }

    private int nextpowof2_(int n){
        if (n <= 1) return n;
        if (n%2 == 0) return n;
        return n+n%2;
    }

}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */