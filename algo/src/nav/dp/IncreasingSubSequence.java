package nav.dp;

import java.util.*;

public class IncreasingSubSequence {
    public static void main(String[] args) {
        System.out.println(LIS(new int[]{1,9,6,7,8}));
        int[] nums = {4,1,2,6,10,1,12}; //32
        System.out.println(findMSIS(nums));
        nums = new int[]{-4,10,3,7,15}; //25
        System.out.println(findMSIS(nums));
    }

    public static int LIS (int[] array){

        int [] T = new int[array.length];
        Arrays.fill(T, 1);
        int maxIndex = -1, maxLen = 1; // Do not use Integer.MIN_VALUE or -1 as that will fail for say [1,1,1]
        for (int i = 1; i < array.length; i++) {
           for (int j = 0; j < i; j++){
               if (array[i]>array[j]){
                   if (T[i] < T[j]+1){
                       T[i] = T[j]+1;
                       if (T[i] > maxLen){
                           maxLen = T[i];
                           maxIndex = i;
                       }
                   }
               }
           }
        }

        int i = maxIndex;
        List<Integer> subsequence = new ArrayList<>();
        subsequence.add(array[i]);
        int lastVal = array[i--];
        while(i>=0){
            if (array[i] <lastVal) {
                subsequence.add(array[i]);
                lastVal = array[i];
            }
            i--;
        }
        System.out.println(Arrays.toString(subsequence.toArray()));
        return maxLen;

    }

    public static int findMSIS (int[] array){
        int [] T = array.clone();
        int maxIndex = -1, maxSum = Integer.MIN_VALUE; // mind the boundry check
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++){
                if (array[i]>array[j]){
                    if (T[i] < T[j]+array[i]){
                        T[i] = T[j]+array[i];
                        if (T[i] > maxSum){
                            maxSum = T[i];
                            maxIndex = i;
                        }
                    }
                }
            }
        }

        int i = maxIndex;
        List<Integer> subsequence = new ArrayList<>();
        subsequence.add(array[i]);
        int lastIndex = i;
        int lastVal = T[i--];
        while(i>=0){
            if (lastVal == T[i] + array[lastIndex]) {
                subsequence.add(array[i]);
                lastVal = T[i];
                lastIndex = i;
            }
            i--;
        }
        System.out.println(Arrays.toString(subsequence.toArray()));
        return maxSum;

    }
}
