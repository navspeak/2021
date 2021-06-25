package nav.arrays;

import java.util.Arrays;

public class SubarraySort {

    public static void main(String[] args) {
        int[] ret = subarraySort(new int[]{1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19}); // 3,9
        ret = subarraySort(new int[]{1,2,3,4}); // 0,1
        System.out.println(Arrays.toString(ret));
    }

    //https://leetcode.com/problems/shortest-unsorted-continuous-subarray/submissions/
    public static int[] subarraySort(int[] a) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int [] ret= new int[]{-1,-1};
        int len = a.length;
        for (int i = 0; i < len; i++ ){
            if ((i > 0 && a[i]<a[i-1]) || (i < len -1 && a[i]> a[i+1])){
                if (a[i]>max) {
                    max = a[i];
                }

                if (a[i]<min) {
                    min = a[i];
                }
            }
        }
        boolean minFound = false;
        for (int i = 0; i < len; i++ ){
            if(!minFound && a[i] > min){
                minFound = true;
                ret[0] = i;
            }

            if(minFound &&  a[i]  < max){
                minFound = true;
                ret[1] =i;
            }
        }
        return ret;
        /*
        if (ret[0] == -1) return 0;
        return ret[1]-ret[0]+1 ; // if have to return size of subarray
         */
    }
}
