package nav.recurison;

import java.util.*;

public class _3Permutations {
    public static void main(String[] args) {
        List<List<Integer>> perms = getPermutations(new ArrayList<>(Arrays.asList(1,2,3)));
        System.out.println(Arrays.deepToString(perms.toArray()));
        printPermutations("abc".toCharArray(), 0);
    }



    //O(N!N.N) Space: O(N.N!)
    public static List<List<Integer>> getPermutations(List<Integer> array) {
        List<List<Integer>> result = new ArrayList<>(); //[]
        if (array.size() == 0) {
            result.add(new ArrayList<>());//[[]]
            return result;
        }
        int curr = array.remove(0);
        List<List<Integer>> subResult = getPermutations(array);
        for(List<Integer> list: subResult){
            for(int i = 0; i <= list.size(); i++){
                List<Integer> tmpList = new ArrayList<>(list);
                tmpList.add(i, curr);
                result.add(tmpList);
            }
        }

        return result;
    }

    // swap method - using string just for variation
    /*
         .abcd
      a.bcd
    ab.cd
  abcd  acbd

     */
    private static void printPermutations(char[] str, int i /*, List<String> result*/) {
        if (i == str.length-1) {
            System.out.println(str);
            return;
        }
        for (int j = i; j <str.length; j++){
            swap(str, i,j);
            printPermutations(str, i+1/*, result*/);
            swap(str,j,i);
        }
    }

    private static void swap(char[] s, int i, int j){
        char tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }

    private static void swap(int i, int j, List<Integer> list){
        int tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }
}

/*
123
 */