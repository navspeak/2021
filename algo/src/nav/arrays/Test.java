package nav.arrays;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        //            (8,4) (8,2),(8,1)
        //               (4,2), (4,1)
        //                    (2,1)
//        int [] arr = {8, 4, 2, 1};
//        int inv = countInversions(arr, 0, arr.length - 1);
//        System.out.println(inv);
        int[] arr = new int[]{1, 2, 2};
        System.out.println(Arrays.deepToString((new Test()).subsetsWithDup(arr).toArray()));

        int[][][] edges = {
                {{1, 7}},
                {{2, 6}, {3, 20}, {4, 3}},
                {{3, 14}},
                {{4, 2}},
                {},
                {}
        };

        for (int i = 0; i < edges.length; i++){
            for (int[] edge: edges[i]){

            }
        }
    }


    public List<List<Integer>> subsetsWithDup(int[] nums) {
        return List.copyOf(subset(nums, 0));

    }

    public Set<List<Integer>> subset(int[] nums, int index){
        Set<List<Integer>> result = new HashSet<>();

        if (index == nums.length){
            result.add(new ArrayList<>());
            return result;
        }

        int curr = nums[index];
        Set<List<Integer>> subResult = subset(nums, index+1);
        result.addAll(subResult);
        for(List<Integer> list: subResult){
            List<Integer> newList = new ArrayList<>(list);
            newList.add(curr);
            result.add(newList);
        }

        return result;
    }
    public static int countInversions(int[] arr, int s, int e){
        if (s >= e) return 0;
        int m = s + (e-s)/2;
        int leftInv = countInversions(arr, s, m);
        int rightInv = countInversions(arr,  m+1, e);
        int mergeInv = mergeAndCountInv(arr, s, m, m+1, e);
        return leftInv+rightInv+mergeInv;
    }

    public static int mergeAndCountInv(int[] arr, int s1, int e1, int s2, int e2){
      int[] tmp = new int[e1-s1+1+e2-s2+1];
      int i=s1, j=s2, k =0;
      int invs = 0;
      while(i<=e1 && j<=e2){
          if (arr[i] <= arr[j]){
              tmp[k++] = arr[i++];
          } else {
              tmp[k++] = arr[j++];
              invs += e1-i+1;
          }
      }
      System.arraycopy(arr,i,tmp,k,e1-i+1);
      System.arraycopy(arr,j,tmp,k,e2-j+1);
      System.arraycopy(tmp,0,arr,s1,e1-s1+1+e2-s2+1);
      return invs;
    }


    /*

            8 4 2 1

            4 8 | 1 2 => 4,1 4,2 8,1 8,2

            4 8 | 1 9 => 4,1 4,9

            1 4 8
            2




     */


}
