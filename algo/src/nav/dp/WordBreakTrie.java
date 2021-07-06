package nav.dp;

import java.util.*;
import java.util.stream.Collectors;

public class WordBreak {
    public static void main(String[] args) {
//        Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
//        Output: ["cats and dog","cat sand dog"]
        String s = "catsanddog";
        List<String> wordDict = Arrays.asList("cat","cats","and","sand","dog");
        System.out.println(Arrays.toString(new WordBreak().wordBreak(s, wordDict).toArray()));
    }
    //0(2^N)
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> set = wordDict.stream().collect(Collectors.toSet());
        List<String> ans = new ArrayList<>();
        helper(s, set,"", ans);
        return ans;
    }

    private static void helper(String s, Set<String> wordDict, String curr, List<String> answer){
        if (wordDict.contains(s)){
            answer.add(curr + s);
            //return;
        }
        boolean match = false;
        for (int i =1; i< s.length(); i++){
            String str1 = s.substring(0, i);
            String str2 = s.substring(i);
            if (!wordDict.contains(str1)) continue;
            String newCurr = curr + str1 + " ";
            match = true;
            helper(str2, wordDict, newCurr, answer);
        }
        //if (match == false)
    }
}
/*
ass Program {
  public static int numbersInPi(String pi, String[] numbers) {
		Set<String> setOfNumbers = new HashSet<>();
    for(String number: numbers){
			setOfNumbers.add(number);
		}
		Map<String, Integer> map = new HashMap<>();
    int ret = numbersInPi(pi, setOfNumbers, 0, map) ;
		return ret == Integer.MAX_VALUE? -1: ret;
  }

	static int numbersInPi(String s, Set<String> set, int currCount){

		    if (set.contains(s)){
 						return currCount;
				}

		   int ways = Integer.MAX_VALUE;
		   for (int i =1; i< s.length(); i++){
            String str1 = s.substring(0, i);
            String str2 = s.substring(i);
            if (!set.contains(str1)) continue;
            ways = Math.min(ways, numbersInPi(str2, set, currCount+1));
        }
		  return ways;
	}
 */