package nav.arrays;

import java.util.Arrays;

public class _4LongestPeak {

    enum TREND { UNKNOWN,ASCENT,DESCENT };
    public static int longestPeak(int[] array) {
        if (array.length < 3) return 0;
        int maxLen = 0;
        int start = 0;
        TREND t = TREND.UNKNOWN;
        //1, 2, 3, 3, 4, 0, 10, 6, 5, -1, -3, 2, 3        start = 3
        for (int i=0; i<= array.length-2; i++){
            switch (t){
                case UNKNOWN:
                    if (array[i+1] > array[i]){
                        t = TREND.ASCENT;
                        start = i;
                    }
                    break;
                case ASCENT:
                    if (array[i+1] < array[i]) {
                        t = TREND.DESCENT;
                        if (i == array.length-2) {
                            maxLen = Math.max(maxLen, (i+1)-(start)+1);
                            break; //no-op
                        }
                    } else if (array[i+1] == array[i]){
                        t = TREND.UNKNOWN;
                    } else {
                        t =  TREND.ASCENT; //No-op, continue in same trend
                    }
                    break;
                case DESCENT:
                    if (array[i+1] > array[i]){
                        t = TREND.ASCENT;
                        // one peak completed
                        maxLen = Math.max(maxLen, (i)-(start)+1);
                        start = i; // start of a new ascent
                        break; //no-op
                    } else if (array[i+1] == array[i]){
                        t = TREND.UNKNOWN;
                        maxLen = Math.max(maxLen, (i)-(start)+1);
                        break; //no-op
                    } else if (array[i+1] < array[i]){
                        t = TREND.DESCENT;
                        if (i == array.length-2) {
                            maxLen = Math.max(maxLen, (i+1)-(start)+1);
                            break; //no-op
                        }
                    }
                    break;
            }

        }


        return maxLen ;

    }
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                {1, 2, 3, 4},
                {12, 13, 14, 5},
                {11, 16, 15, 6},
                {10, 9, 8, 7}
        };

        System.out.println(longestPeak(new int[]{1, 2, 3, 3, 4, 0, 10, 6, 5, -1, -3, 2, 3})); //6
        System.out.println(longestPeak(new int[]{5, 4, 3, 2, 1, 2, 10, 12}));
        System.out.println(longestPeak(new int[]{1, 3, 2}));
    }
}
