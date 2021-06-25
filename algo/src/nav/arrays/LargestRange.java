package nav.arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LargestRange {
    public static void main(String[] args) {
        // -7, 7
        System.out.println(Arrays.toString(largestRange(new int[]{0, -5, 9, 19, -1, 18, 17, 2, -4, -3, 10, 3, 12, 5, 16, 4, 11, 7, -6, -7, 6, 15, 12, 12, 2, 1, 6, 13, 14, -2})));
    }

    public static int[] largestRange(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (int a: array) set.add(a);
        int start = array[0], end = array[0];

        for(int a: set){
            int localStart = a, localEnd = a;
            int x = a;
            while (set.contains(x)){
                localStart = x--;
            }
            x=a;
            while (set.contains(x)){
                localEnd = x++;
            }
            if (localEnd - localStart > end - start){
                start = localStart;
                end = localEnd;
            }
        }
        return new int[]{start, end};

    }
    public static int[] largestRange_(int[] array) {
        if (array.length == 1) return new int[]{array[0], array[0]};
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
        int[] range = new int[]{array[0], array[0]};
        boolean rangeFound = false;
        int start = array[0], end = array[0];
        for (int i = 0; i < array.length -1; i++) {
            if(array[i] == array[i+1] ) continue;
           if(!rangeFound && array[i+1] - array[i] == 1){
               rangeFound = true;
               start = array[i];
               end = array[i+1];
               continue;
           }

            if( rangeFound && array[i+1] - array[i] == 1 ){
                end = array[i+1];
                continue;
            }

            if( rangeFound && array[i+1] - array[i] != 1){
                rangeFound = false;
                if(end - start > range[1] - range[0]){
                    range[0] = start;
                    range[1] = end;
                }
            }
        }

        if (rangeFound == true && end - start > range[1] - range[0]){
            range[0] = start;
            range[1] = end;
        }
        return range;
    }
}
