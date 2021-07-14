package nav.recurison;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rough {

    public static void main(String[] args) {
       List<List<Integer>> subsets = new Rough().subsets(Arrays.asList(1,2,3), 0);
        System.out.println(Arrays.toString(subsets.toArray()));
    }

    List<List<Integer>> subsets(List<Integer> array){
        List<List<Integer>> result = new ArrayList<>();
        if (array.size() == 0){
            result.add(new ArrayList<>());
            return result;
        }

        int curr = array.remove(0);
        List<List<Integer>> interim = subsets(array);
        for(List<Integer> one : interim){
            List<Integer> tmp = new ArrayList<>(one);
            tmp.add(curr);
            result.add(tmp);
        }
        result.addAll(interim);
        return result;
    }

    List<List<Integer>> subsets(List<Integer> array, int i){
        List<List<Integer>> result = new ArrayList<>();
        if (i == array.size()){
            result.add(new ArrayList<>());
            return result;
        }

        int curr = array.get(i);
        List<List<Integer>> interim = subsets(array, i+1);
        for(List<Integer> one : interim){
            List<Integer> tmp = new ArrayList<>(one);
            tmp.add(curr);
            result.add(tmp);
        }
        result.addAll(interim);
        return result;
    }

}
