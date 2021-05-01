package nav.arrays;

import java.util.Arrays;

public class _7_3NumberSort {

    public static int[] threeNumberSort(int[] array, int[] order) {
        int first = 0, mid = 0, last = array.length - 1;
        while(mid<=last){
            int currElem = array[mid];
            if (currElem == order[0]){
                swap(array, mid, first);
                first++;
                mid++;
            } else if (currElem == order[1]){
                mid++;
            } else {
                swap(array, mid, last);
                last--;
            }
        }
        return array;
    }

    /*
        0 1 2 3 4 5 6        => [2,1,3]
        ===============
        2 2 2 1 1 1 3
              f   s

     */

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(threeNumberSort(new int[]{1, 0, 0, -1, -1, 0, 1, 1}, new int[] {0,1,-1})));
    }
}
