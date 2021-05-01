package nav.arrays;

import java.util.Arrays;

public class _92BinarySearch {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},-1))); //-1,0
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},1))); // 0,0
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},11))); //0 1
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},20))); // 1 1
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},21))); // 1,2
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},30))); // 2, 2
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},35))); // 2, 3
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},75))); //3,4
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},80))); //4,4
        System.out.println(Arrays.toString(getRange(new int[]{1, 20, 30, 70, 80},85))); //4,5

        System.out.println(Arrays.toString(searchForRange(new int[] {0, 1, 21, 33, 45, 45, 45, 45, 45, 45, 61, 71, 73}, 45)));
        //===
        int[][] matrix = {
                {1, 4, 7, 12, 15, 1000},
                {2, 5, 19, 31, 32, 1001},
                {3, 8, 24, 33, 35, 1002},
                {40, 41, 42, 44, 45, 1003},
                {99, 100, 103, 106, 128, 1004}
        };
        System.out.println(Arrays.toString(searchInSortedMatrix(matrix, 4)));
    }


    // input  6 7  1 2 5 target 2
    public static int shiftedBinarySearch(int[] array, int target) {
        return shiftedBinarySearch(array, 0, array.length -1, target);
    }

    public static int shiftedBinarySearch(int[] array, int left, int right, int target) {
        if (left > right) return -1;
        int mid = (left + right) / 2;
        if (target == array[mid]) {
            return mid;
        } else if (array[left] < array[mid]) { //left half is sorted
            if (target < array[mid] && target >= array[left])
                return shiftedBinarySearch(array, left, mid - 1, target);
            else
                return shiftedBinarySearch(array, mid + 1, right, target);
        } else { //right half is sorted
            if (target > array[mid] && target <= array[right])
                return shiftedBinarySearch(array, mid + 1, right, target);
            else
                return shiftedBinarySearch(array, left, mid - 1, target);
        }
    }

    public static int[] searchInSortedMatrix_BETTER(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] colArray = new int[rows];
        int i = 0, j = cols -1;
        while (i<rows && j >= 0){
            if (target < matrix[i][j]){
                j--;
            } else if (target > matrix[i][j]) {
                i++;
            } else {
                return new int[]{i, j};
            }
        }
        return new int[]{-1, -1};
    }

    public static int[] searchInSortedMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] colArray = new int[rows];
        for (int j = 0; j < cols; j++){
            for (int i =0; i< rows; i++){
                colArray[i] = matrix[i][j];
            }
            int[] foundRange = getRange(colArray, target);
            if (foundRange[0] == foundRange[1])
                return new int[]{foundRange[0],j};
        }
        return new int[]{-1, -1};
    }


    public static boolean search(int[] array, int target){
        int left = 0, right = array.length -1;
        int mid = (left + right)/2;
        while(left <= right){
            mid = (left + right)/2;
            if (target > array[mid]){
                left = mid+1;
            } else if (target < array[mid]){
                right = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public static int[] getRange(int[] array, int target){
        int left = 0, right = array.length -1;
        int mid = (left + right)/2;
        while(left <= right){
            mid = (left + right)/2;
            if (target < array[mid]){
                right = mid - 1;
            } else if (target > array[mid]){
                left = mid+1;
            } else {
                return new int[]{mid, mid};
            }
        }
        return new int[]{right, left};
    }


    public static int[] searchForRange(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while(left<= right){
            int mid = (left + right)/2;
            if (array[mid] == target){
                left = right = mid;
                while(left >= 0 && array[left] == target){
                    left--;
                }
                while(right < array.length && array[right] == target) {
                    right++;
                }
                return new int[] {left+1, right-1};
            } else if (target > array[mid]){
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return new int[]{-1, -1};
    }
}
