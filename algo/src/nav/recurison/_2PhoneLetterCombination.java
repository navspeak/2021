package nav.recurison;

import java.util.*;
// Time complexity: O(4^N * N)
public class _2PhoneLetterCombination {
    private static String[] map = { "0", "1","abc", "def","ghi","jkl","mno","pqrs","tuv","wxyz"};

    public static void main(String[] args) {
        List<String> strings = (new _2PhoneLetterCombination()).letterCombinations("23");
        strings = phoneNumberMnemonics("234");
        System.out.println(strings);
    }


    //O(4^N * N)  => all 9 or 7 | space O(4^N)
    /*
            P(123) 3^3
         1 P(23) = 3^2
           2 P(3) => abc to [d,e,f] => 3.3
             3 P(empty) => "def" to empty =>3 =>    total 3.3^4

     */
    public static List<String> phoneNumberMnemonics(String pno) {
        List<String> result = new ArrayList<>();
        if (pno.length() == 1){
            for ( char c : map[Integer.parseInt(pno)].toCharArray()){
                result.add(Character.toString(c));
            }
            return result;
        }
        char curr = pno.toCharArray()[0];
        List<String> subResult = phoneNumberMnemonics(pno.substring(1));
        String currLetters = map[Character.getNumericValue(curr)];
        return cross(currLetters, subResult);
    }

    private static List<String> cross(String letters, List<String> list) {
        List<String> result = new ArrayList<>();
        for (char c: letters.toCharArray()){
            for (String s: list) {
                result.add(c+s);
            }
        }
        return result;
    }

    public List<String> letterCombinations_ITR(String digits) {
        if (digits.length() == 0){
            return Collections.emptyList();
        }
        List<String> res = new LinkedList<>();
        int len = digits.length();
        for (int i = 0; i < len; i++){
            int index = Character.getNumericValue(digits.charAt(i));
            if (index <=1 || index > 9) continue;
            List<String> list = new LinkedList<>();
            for (char c: map[index].toCharArray()){
                if (res.isEmpty()) {
                    list.add(""+c);
                } else {
                    res.forEach(str ->{
                        list.add(str+c);
                    });
                }
            }
            res = list;
        }

        return res;
    }
    public List<String> letterCombinations(String digits) {
        Deque<String> input = new ArrayDeque<>();
        for (int i=0; i< digits.length();i++){
            String tmp = map[Character.getNumericValue(digits.toCharArray()[i])];
            if (tmp.length() > 0 )
                input.add(tmp);
        }

        if (input.size() == 0) return Collections.emptyList();
        return letterCombinationsRec(input);
    }

    private List<String> letterCombinationsRec(Deque<String> input){
        String currStr = input.remove();
        if(input.size() == 0){
            List<String> result = new ArrayList<>();
            for(char c: currStr.toCharArray())
                result.add(Character.toString(c));
            return result;
        }

        List<String> result =  letterCombinationsRec(input);
        List<String> newList = new ArrayList<>();
        for (char c: currStr.toCharArray()){
            for (String s: result){
                newList.add(c+s);
            }
        }
        return newList;
    }


}
