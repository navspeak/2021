package nav.dp;

public class LevenShteinDistance {

    public static void main(String[] args) {
        System.out.println(levenshteinDistance("abc", "yabd"));
        System.out.println(levenshteinDistance("ab", "yabd"));
        System.out.println(levenshteinDistance("abyyyyyyy", "yabd"));
    }

    // s1 => s2
    public static int levenshteinDistance(String s1, String s2) {
        if (s1.length() == 0) return s2.length(); // so many inserts as to get "" -> s2
        if (s2.length() == 0) return s1.length(); // so many deletes as to get s2 -> ""
        return minDistance(s1, 0, s2, 0);
    }

    public static int minDistance(String s1, int i, String s2, int j) {
        if (j == s2.length()) return s1.length() - i;
        if (i == s1.length()) return s2.length() - j;
        /*
          abc
          adx
         */
        if (s1.charAt(i) == s2.charAt(j)) return minDistance(s1,i+1, s2, j+1);
        /*
         012
         abc
         xby
         .
         012 i=0   0
         abc       xabc
         xby       xby

         abc
         xby
         */
        int remove = 1 + minDistance(s1,i+1,s2,j);
        int insert = 1 + minDistance(s1,i,s2,j+1);
        int replace = 1 + minDistance(s1,i+1,s2,j+1);
        return Math.min(Math.min(remove, insert), replace);
    }
}
