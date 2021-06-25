package nav.recurison;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class _4Subset {
    public static void main(String[] args) {
//        List<List<String>> orig = new ArrayList<>();
//        orig.add(new ArrayList<>(Arrays.asList("Apple", "Orange")));
//        orig.add(new ArrayList<>(Arrays.asList("Lion", "Tiger")));
//        System.out.println(Arrays.deepToString(orig.toArray())); // [[Apple, Orange], [Lion, Tiger]]
//        List<List<String>> dup = new ArrayList<>(); //
//        dup.addAll(orig);//dup will be new but whatever is inside it are a pointer to original
//        for(List<String> s: orig){
//            s.add("X");
//            dup.add(s);
//            s.remove(s.size() - 1);
//        }
//        System.out.println(Arrays.deepToString(orig.toArray()));//[[Apple, Orange, X], [Lion, Tiger, X]]
//        System.out.println(Arrays.deepToString(dup.toArray()));//[[Apple, Orange, X], [Lion, Tiger, X], [Apple, Orange, X], [Lion, Tiger, X]]
//        System.out.println(Arrays.deepToString(subsets(new int[]{1,2,3}, 0).toArray()));
//        System.out.println(Arrays.deepToString(subsets(new int[]{1,2,3}).toArray()));
        System.out.println(Arrays.deepToString(subsets(new int[]{4,4,4,1,4}).toArray())); //     Arrays.sort(nums); // important for subset with duplicate
    }

    public static List<List<Integer>> subsets(int[] nums, int i){

        if (i == nums.length ) {
            List<List<Integer>> ret = new ArrayList<>();
            ret.add(new ArrayList<>());
            return ret;
        }
        List<List<Integer>> subResult = subsets(nums, i+1);
        List<List<Integer>> result =  new ArrayList<>(subResult); // O(N)
        for(List<Integer> list: subResult){
            List<Integer> tmp = new ArrayList<>(list); // very important
            tmp.add(nums[i]);
            result.add(tmp);
        }
        return result;
    }

    /*

           0 000
           1 001
           2 010
           3 011
           4 100
           5 101
           6 110
           7 111
     */
    public static List<List<Integer>> subsets(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i< (1<<nums.length);i++){
            List<Integer> oneSubset = new ArrayList<>();
            for (int j = 0; j < nums.length ; j++) {
                if ((i & (1<<j)) == (1<<j)) { // (1<<j) is the check bit. We could also check >= 1
                    oneSubset.add(nums[j]);
                }
            }
            result.add(oneSubset);
        }
        return result;
    }
}
/*
[] = []



 */