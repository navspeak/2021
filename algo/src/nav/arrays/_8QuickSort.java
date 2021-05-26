package nav.arrays;

import java.util.Arrays;
import java.util.Random;

import static nav.arrays._6Sorting.swap;

public class _8QuickSort {
    public static void main(String[] args) {
        int[] array = new int[]{10,15,8,7,1,2,6};
        System.out.println(Arrays.toString(array));
        sort(array, 0, array.length -1);
        System.out.println(Arrays.toString(array));
        array = new int[]{10,15,8,7,1,2,6};
        System.out.println(findKthMin(array, 0, array.length - 1,5));

    }

    public static int findKthMin(int arr[], int left, int right, int k){
        if (left == right) return arr[left];
        int partionIdx = partition(arr, left, right);
        if (partionIdx == k-1) {
            return arr[partionIdx];
        } else if (partionIdx > k -1) {
            return findKthMin(arr, left, partionIdx-1, k );
        } else {
            return findKthMin(arr, partionIdx+1, right, k );
        }

    }

    // best case: pivot divides in middle - O(nlogn) | worst: every time pivot is at end of the array
    public static void sort(int[] array, int left, int right){
        if (left >= right) return;
        int partitionIdx = partition(array, left, right);
        sort(array, left, partitionIdx -1);
        sort(array,  partitionIdx +1,right);
    }

    public static int partition(int[] array, int left, int right){
        int randomIdx = (left + (new Random()).nextInt(right - left));
        swap(array, left, randomIdx);
        // 10 5 6 7 8 [9]
        int pivotIdx = right;
        int pivot = array[right];
        //[<L---9----R>]
        for (int i = left; i < right; i++) {
            if (array[i] < pivot){
                swap(array, i, left++);
            }
        }
        swap(array, pivotIdx,left);
        return left;
    }
}
/*
    0  1  2 3 4 5 7
    ================
    10 15 8 7 1 2 6
 pivot = 7 , pivotIdx = 6
 ===
 10 15 8 7 1 2 6
 L           R P   10>6 => should move to right eventually, just move i
 i
 10 15 8 7 1 2 6   15>6 => should move to right eventually, just move i
  L i        R P
 10 15 8 7 1 2 6   8>6
  L    i     R P
 10 15 8 7 1 2 6   7>6
  L      i   R P
 10 15 8 7 1 2 6   1>6, so move it to left side, and increment left pointer
  L        i R P
  1 15 8 7 10 2 6   1<6, so move it to left side, and increment left pointer
     L      i R P
  1 15 8 7 10 2 6   1<6, so move it to left side, and increment left pointer
     L      i R P
  1 15 8 7 10 2 6   2<6, so move it to left side, and increment left pointer
     L        R P
              i
  1 2 8 7 10 15 6   i reached end => exit loop & swap pivot with left
      L       R P
                i
   1 2 6 7 10 15 8   i reached end => exit loop & swap pivot with left
       L      R P
                i

 */
 /*

 Aim: L<-------->R/P => <less than Pivot> L <More than Pivot> P => <less than Pivot> L(swap a[P]) <More than Pivot> a[L]
  Li
 10 15 8 7 2 1 [6] => move left only when we encounter anything less than or equal to Pivot
  L i  ->  i                                    L                   L
 10 15 8 7 2 1 [6] => here insert to left => 2 15 8 7 10 1 [6] => 2 6 8 7 15 1 [15]
  */
