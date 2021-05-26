package nav.arrays;

import java.util.Arrays;

public class _9HeapSort {

    public static void main(String[] args) {
        int[] array = new int[]{888888, 10,15,8999, 8,7,1,2,100, 6, 18};
        System.out.println(Arrays.toString(array));
        //heapify(array, array.length - 1);
        heapSort(array, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    public static int[] heapSort(int[] array) {
        heapSort(array, array.length - 1);
        return array;
    }

    public static void heapSort(int[] arr, int end){
        if (end == 0) return;
        heapify(arr, end);
        swap(arr, end, 0);
        heapSort(arr, end -1);
    }


    /*
         0 1 2 3 4 5 6 7
         ================
         1 2 5 8 7          =>

               [0-1]
        [1-2]               [2-5]
  [3-8]

     */
     public static void heapify(int[] array, int end){
         if (end <= 0) return;
         int parentIdx = (end - 1)/2;
         int leftChildIdx = 2*parentIdx + 1;
         int leftChildVal = array[leftChildIdx];

         int rightChildIdx = 2*parentIdx + 2;
         int rightChildVal = Integer.MIN_VALUE;
         if (rightChildIdx <= end) {
             rightChildVal = array[rightChildIdx];
         }
         int greaterChildIdx = leftChildVal > rightChildVal ?leftChildIdx: rightChildIdx;
         if (array[parentIdx] < array[greaterChildIdx]){
             swap(array, parentIdx, greaterChildIdx);
         }
         int prevParent = parentIdx - 1;
         int nextRightChild = 2*prevParent + 2;
         heapify(array, nextRightChild);
    }

    private static void swap(int[] arr, int i, int j){
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

}
