package nav.string;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> str = Arrays.asList("abde", "abc", "abd", "abcde", "ade", "ae", "1abde", "abcdef");

        System.out.println(Arrays.toString(str.toArray()));
        Collections.sort(str, (x,y)-> -Integer.compare(x.length(), y.length()));
        System.out.println(Arrays.toString(str.toArray()));

        String[] strs = new String[]{"abde", "abc", "abd", "abcde", "ade", "ae", "1abde", "abcdef"};
        Arrays.sort(strs);
        System.out.println(Arrays.toString(strs));
    }
}
