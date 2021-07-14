package nav.arrays;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _91MergeSort {

    public static void main(String[] args) {
        int[] arr = { 2, 3, 5, 5, 6, 8, 9};
        int [] auxArr = Arrays.copyOfRange(arr,0, arr.length);
        int[] left = Arrays.copyOfRange( arr,0, arr.length/2);
        int[] right = Arrays.copyOfRange( arr, arr.length/2, arr.length);
        mergeSort(arr, 0, arr.length -1, auxArr);
       System.out.println(Arrays.toString(arr));
//        System.out.println(Arrays.toString(left));
//        System.out.println(Arrays.toString(right));
        int[] arr1 = { 2, 3, 3, 1, 9, 5, 6};
        System.out.println(countInversions(arr1, 0, arr1.length - 1));
    }

    public static void mergeSort(int[] arr, int start, int end, int[] aux){
        if (start == end) return;
        int mid = start + (end -start)/2;
        mergeSort(aux, start, mid, arr);
        mergeSort(aux, mid+1, end, arr);
        merge(aux, start, mid, mid+1, end, arr);

    }

    public static void merge(int[] arr, int start1, int end1, int start2, int end2, int[] aux){
        int i = start1, j = start2, k = start1; // IMP: k should be wherever start1 is not 0
        while(i <= end1 && j <= end2){
            if (arr[i] < arr[j]){
                aux[k++] = arr[i++];
            } else {
                aux[k++] = arr[j++];
            }
        }
        System.arraycopy(arr,i,aux, k,end1 -i + 1);
        System.arraycopy(arr,j,aux, k,end2 -j + 1);
        System.arraycopy(aux,start1, arr, start1, end2-start2+1 + end1-start1+1);
    }

    public int countInversions(int[] array){
        if (array.length <= 1) return 0;
        // degree of unsortedness
        int count = countInversions(array, 0, array.length -1);
        return count;
    }

    private static int countInversions(int[] array,  int start, int end){
        if (start == end) return 0; // [1] => 0
        int mid = start + (end -start)/2;
        int leftInversions = countInversions(array, start, mid);
        int rightInversions = countInversions(array, mid+1, end);
        int mergeInversions = countAndMerge(array, start, mid, mid+1, end);
        return leftInversions+rightInversions+mergeInversions;
    }

    public static int countAndMerge(int[] arr, int start1, int end1, int start2, int end2){
        int i = start1, j = start2, k = 0;
        int[] tmp = new int[end1 - start1 + end2 - start2 + 2];
        int count = 0;
        while(i <= end1 && j <= end2){
            if (arr[i] <= arr[j]){
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
                count += (end1 - i)+1;
            }
        }
        System.arraycopy(arr, i, tmp, k,end1 - i +1);
        System.arraycopy(arr, j, tmp, k,end2 - j +1);
        System.arraycopy(tmp, 0, arr, start1,tmp.length);

        return count;
    }
}
