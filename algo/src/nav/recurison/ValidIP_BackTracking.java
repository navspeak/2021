package nav.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValidIP {
    public static void main(String[] args) {
        List<String> validIPs = validIPAddresses("1921680");
        System.out.println(Arrays.deepToString(validIPs.toArray()));
    }

    private static List<String> validIPAddresses(String s) {
        if (s.length() > 12) return Collections.emptyList();
        List<String> validIPs = new ArrayList<>();
        placeDot(s, 0, 0, "",validIPs);
        return validIPs;
    }

    private static void placeDot(String s, int index, int dots, String currFormation, List<String> result) {
        // base condition
        if (dots == 3){
            String curr = s.substring(index);

          if  (index == s.length() || Integer.valueOf(curr) > 255 || (curr.length() > 1 && curr.startsWith("0")) )
               return ;
           currFormation += s.substring(index);
           result.add(currFormation);
           return ;
        }

        // try with length of 1,2 and 3 in this portion of IP address
        for (int len = 1; len <= 3; len++) {
            if (index + len >= s.length()) continue;
            String curr = s.substring(index, index+len);
            if  (Integer.valueOf(curr) > 255 ||(curr.length() > 1 && curr.startsWith("0")) )
                continue ;
            String potentialFormation = currFormation + curr + ".";
            placeDot(s,index+len, dots+1, potentialFormation, result);
            // backtrack, since we did not add to currFormation but used another string, nothing is needed to be done
            // during backtracking
        }
    }
}
