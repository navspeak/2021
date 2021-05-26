package nav.arrays;

import java.util.Arrays;

public class _5SubArraySort {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new int[] {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19}));
        System.out.println(Arrays.toString(subArraySort(new int[] {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19})));
    }

    private static int[] subArraySort(int[] array) {
        /*
            -----------------
            0 1 2 3 4 5 6 7 <= indices
            ---------------
            1 1 2 3 3 6 9 9 <= sorted
            1 1 3 6 9 2 3 9 <= input array
            ---------------
            index = 2
            ----------------


         */
        return null;

    }

}
