package nav.arrays;

import java.util.ArrayDeque;
import java.util.Arrays;

public class _6Sorting {

    //  start with smaller array of size 1, take element at 2 and insert at right position
    //  increase size = 2, insert element at 3 in proper position
    public static int[] insertionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++){ // Important
            int j = i+1;
            while(j > 0 && a[j] < a[j-1]) {
                swap(a, j, j - 1);
                j--;
            }
        }

        return a;
    }
    // similar but here we insert smallest element in end of an earlier sorted array that had all elements shorter than
    // one found now
    public static int[] selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++){
            int smallest = Integer.MAX_VALUE;
            int smallestIndex = 0;
            for (int j=i; j < array.length; j++){
                if(array[j] < smallest){
                    smallest = array[j];
                    smallestIndex = j;
                }
            }
            swap(array, i, smallestIndex);
        }
        return array;
    }

    // take arr at start, compare all with it, and swap if smaller. At end beginning contains smallest
    public static int[] bubbleSort(int[] array) {
        for(int i = 0; i < array.length; i++){
            for(int j=i+1; j <array.length; j++){
                if (array[i]>array[j]) swap(array, i, j);
            }
        }
        return array;
    }

    public static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static int binarySearch(int[] array, int target) {
        int i = 0, j = array.length - 1;
        while(i <= j){
            int mid = ((i+j)/2);
            if (array[mid] > target) {
                j = mid -1;
            } else if (array[mid] < target){
                i = mid + 1;
            } else
                return mid;
        }
        return -1;
    }


    public static void main(String[] args) {
        int [] a = {8, 5, 2, 9, 5, 6, 3};
        System.out.println(Arrays.toString(insertionSort(a)));
        System.out.println(Arrays.toString(insertionSort(a)));
    }
}
/* 8, 5, 2, 9, 5, 6, 3
   -
   5, 8

 */