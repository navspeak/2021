package nav.misc;

import java.util.*;

public class SimplifyDirectoryPath {
    public static void main(String[] args) {

        /*
             / => /
             /home => /home
             /home/ => /home
             /home/.. => /home
             /home/../a/./b => /a/b
            /a/./b/../../c/ => /c

         */
        System.out.println(shortenPath("/a/./b/../../c"));
        System.out.println(shortenPath("/a/../x/./y/"));
        System.out.println(shortenPath("/home/../a/./b"));
        System.out.println(shortenPath(" /home/../a/./b "));
        System.out.println(shortenPath("../../foo/bar/baz")); //=>same
        System.out.println(shortenPath("/../../foo/../../bar/baz")); //=>same
    }

    public static String shortenPath(String path) {
        path = path.trim();
        boolean startsWithRoot = path.charAt(0) == '/';
        String[] tokens = path.split("/");
        List<String> tokenList = Arrays.asList(tokens);
        Stack<String> stack = new Stack<>();
        for(String s : tokenList){
            if (s.equals(".") || s.length() == 0) {
                continue;
            }

            if (s.equals("..")) {
                if (!stack.isEmpty() && !stack.peek().equals("..")) { // "../../foo/bar/baz"
                    stack.pop();
                } else if (stack.isEmpty() && startsWithRoot == false) { // "/../../../bar/baz"
                    stack.push("..");
                } else
                    continue;
            }

            stack.push(s); // is addFirst
        }
        StringBuilder sb = new StringBuilder();
        if (startsWithRoot) sb.append("/");
        for (int i = 0; i< stack.size(); i++){
           sb.append(stack.get(i)).append("/");
        }
//        for (int i = stack.size()-1; i>=0; i--){
//            sb.append(stack.get(i)).append("/");
//        }
        String ret = sb.toString();
        return ret.substring(0,ret.length() - 1);
        // NOTE stack with have [0th-index b, next a]  for /a/b if we use Deque
//        if (startsWithRoot && !stack.isEmpty()){
//            return String.join("/", stack);
//        } else if (!stack.isEmpty()){
//            return stack.toString();
//        } else
//            return "";
    }
}
