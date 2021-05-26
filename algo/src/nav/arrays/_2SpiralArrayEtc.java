package nav.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class _2SpiralArrayEtc {

    static List<Integer> traverseSpirally(int[][] arr){
        List<Integer> result = new ArrayList<>();
        if (arr.length == 0) return result;

        final int rowStart_outer = 0;
        final int rowEnd_outer = arr.length - 1;
        final int colStart_outer=  0;
        final int colEnd_outer = arr[0].length - 1;
        List<int[]> boundaries = new ArrayList<>();

        final int elements = arr[0].length * arr.length;
        int elementsAddedsoFar = 0;
        int loop = 0;
        while (elementsAddedsoFar < elements) {
            int rowStart = rowStart_outer + loop;
            int colStart = colStart_outer + loop;
            int colEnd = colEnd_outer - loop;
            int rowEnd = rowEnd_outer - loop;
            // add elements ->
            // row fixed at rowStart; col -> [colStart...colEnd]
            for (int i = colStart; i <= colEnd; i++) {
                result.add(arr[rowStart][i]);
                elementsAddedsoFar++;
            }
            if (elementsAddedsoFar >= elements) break;

            // add elements \/
            // col fixed at colEnd; row -> [rowStart+1...rowEnd]
            for (int i = rowStart + 1; i <= rowEnd; i++) {
                result.add(arr[i][colEnd]);
                elementsAddedsoFar++;
            }
            if (elementsAddedsoFar >= elements) break;

            // add elements <---
            // row fixed at rowEnd ; col -> [colEnd-1,...loop]
            for (int i = colEnd - 1; i >= loop; i--) {
                result.add(arr[rowEnd][i]);
                elementsAddedsoFar++;
            }
            if (elementsAddedsoFar >= elements) break;

            // add elements /\
            // col fixed at loop ; row -> [rowEnd-1,...loop]
            for (int i = rowEnd - 1; i > loop; i--) {
                result.add(arr[i][loop]);
                elementsAddedsoFar++;
            }
            if (elementsAddedsoFar >= elements) break;
            loop++;
        }
        return result;
    }

    enum STATUS {
        ASCENT,
        DESCENT,
        NONE
    };

    public int[][] mergeOverlappingIntervals(int[][] intervals) {
        if (intervals.length <= 0) return intervals;
        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0], b[0]));
        List<int[]> result = new ArrayList<>();
        int a = intervals[0][0];
        int b = intervals[0][1];
        for(int i = 1; i <= intervals.length; i++){
            if(i == intervals.length){
                result.add(new int[]{a,b});
            } else if (intervals[i][0] > b){
                result.add(new int[]{a,b});
                a = intervals[i][0];
                b = intervals[i][1];
            } else if(intervals[i][0] <= b && intervals[i][1]> b){
                b= intervals[i][1];
            }
        }
        int[][] ret = new int[result.size()][];
        for(int i = 0; i < result.size(); i++){
            ret[i] = result.get(i);
            //System.out.println(Arrays.toString(ret[i]));
        }
        return ret;
    }


// T: O(nlog), S: O(N)

    public static int longestPeak(int[] a) {
        if (a.length < 3) return 0;
        int maxLen = 0;
        STATUS status = STATUS.NONE;
        int[] table = new int[]{0,0}; //Index 0 - start of peak. Index 1 = middle, 2 = end
        for (int i = 0 ; i <= a.length -2; i++){
            if (i == a.length -2){
                switch (status) {
                    case DESCENT:
                        if (a[i+1] >= a[i]) {
                            maxLen = Math.max(maxLen, i - table[0] + 1);
                        } else {
                            maxLen = Math.max(maxLen, i - table[0] + 2);
                        }
                    case ASCENT:
                        if (a[i+1] < a[i])
                            maxLen = Math.max(maxLen, i - table[0] + 2);
                    case NONE:
                        break;
                }
            } else if (status == STATUS.NONE && a[i+1] > a[i]){
                status = STATUS.ASCENT;
                table[0] = i;
            } else if (status == STATUS.ASCENT){
                if (a[i+1] == a[i]){
                    status = STATUS.NONE;
                } else if (a[i+1] < a[i]) {
                    status = STATUS.DESCENT;
                    table[1] = i;
                } else {
                    status = STATUS.ASCENT; // No-op
                }
            } else if (status == STATUS.DESCENT){
                if (a[i+1] > a[i]){
                    status = STATUS.ASCENT;
                    maxLen = Math.max(maxLen, i - table[0] + 1);
                    table[0] = i;
                } else if (a[i+1] == a[i]){
                    status = STATUS.NONE;
                    maxLen = Math.max(maxLen, i - table[0] + 1);
                }
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
        System.out.println(Arrays.toString(traverseSpirally(arr).toArray()));
//        System.out.println(longestPeak(new int[]{1, 2, 3, 3, 4, 0, 10, 6, 5, -1, -3, 2, 3}));
//        System.out.println(longestPeak(new int[]{5, 4, 3, 2, 1, 2, 10, 12}));
//        System.out.println(longestPeak(new int[]{1,3,2}));
//        arr = new int[][]{};
//        System.out.println(Arrays.toString(traverseSpirally(arr).toArray()));
//        arr = new int[][]{{1}};
//        System.out.println(Arrays.toString(traverseSpirally(arr).toArray()));
        System.out.println(Arrays.deepToString(arr));
        Arrays.sort(arr,(a, b) ->Integer.compare(a[0], b[0]) );






    }
}
