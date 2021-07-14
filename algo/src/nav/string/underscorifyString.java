package nav.string;

import java.util.ArrayList;
import java.util.List;

public class underscorifyString {

    public static void main(String[] args) {
        System.out.println(underscorifySubstring
                ("thitest", "test"));
        System.out.println(underscorifySubstring
                ("thitest!", "test"));
        System.out.println(underscorifySubstring
                ("thitest testtest test", "test"));
        System.out.println(underscorifySubstring
                ("this is test! a testtest test testament tes!", "test"));
    }

    public static String underscorifySubstring(String str, String substr) {

        List<int[]> subStrCoordinates = new ArrayList<>();
        int start = -1, len = 0;
        int i = 0, j = 0;
        while(i<str.length()){
            if (str.charAt(i) == substr.charAt(j)) { // potential of finding substring match
                start = i;
                while(i< str.length() && j< substr.length() && str.charAt(i) == substr.charAt(j)){
                    i++;
                    j++;
                }
                if (j == substr.length()){
                    len = i;
                    j = 0;
                    subStrCoordinates.add(new int[]{start, len});
                }
                start = -1;
                j = 0;
            } else {
                i++;
            }
        }

        int x = 0, y = -1;
        String ret = "";
        for (int k = 0; k < subStrCoordinates.size(); ) {
            int tmpStart = subStrCoordinates.get(k)[0];
            int tmpEnd = subStrCoordinates.get(k)[1];
            k++;
            while (k < subStrCoordinates.size() && subStrCoordinates.get(k)[0] == tmpEnd){
                tmpEnd = subStrCoordinates.get(k)[1];
                k++;
            }
            String part1 = str.substring(x,tmpStart);
            String part2 = str.substring(tmpStart, tmpEnd);
            if (!part1.endsWith("-")) part1 =part1+"_";
            if (!part2.endsWith("-")) part2 =part2+"_";
            ret +=part1+ part2;
            x = tmpEnd;
        }
        if (x < str.length()){
            ret = ret + str.substring(x);
        }

        return ret;
    }
}
