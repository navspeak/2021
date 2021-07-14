package nav.dp;
// https://www.youtube.com/watch?v=UZRkpGk943Q
public class CommonSubstring {
    public static void main(String[] args) {
        System.out.println(findLCSLength("a", "a"));
        System.out.println(findLCSLength("abdca", "cbda"));
        System.out.println(findLCSLength("passport", "ppsspt"));
        System.out.println(findLCSLength("a", 0, "a", 0, 0));
        System.out.println(findLCSLength("abdca", 0, "cbda", 0, 0));
        System.out.println(findLCSLength("passport",0,  "ppsspt", 0, 0));
    }

    /*
    Because of the three recursive calls, the time complexity of the above algorithm is exponential O(3^{m+n})
    where ‘m’ and ‘n’ are the lengths of the two input strings. The space complexity is O(m+n),
    this space will be used to store the recursion stack.

    memo => (i1 + “|” i2 + “|” + count))
     */
    private static int findLCSLength(String s1, int i, String s2, int j, int c) {
        if (i == s1.length() || j == s2.length()) return c;
        if(s1.charAt(i) == s2.charAt(j)){
            c=  findLCSLength(s1,i+1, s2, j+1, c+1);
        }

        int o1 = findLCSLength(s1, i+1, s2, j, 0);
        int o2 = findLCSLength(s1, i, s2, j+1, 0);
        return Math.max(Math.max(o1,o2),c);
    }

    private static int findLCSLength(String s1,  String s2){
        int T[][] = new int[s1.length()][s2.length()];
        int max = -1;
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)){
                    if (i >=1 && j>= 1)
                        T[i][j]  = 1 + T[i-1][j-1];
                    else
                        T[i][j] = 1;
                    if (T[i][j] > max){
                        max = T[i][j];
                    }
                }
            }
        }
        return max;
    }
}
