package nav.string;

public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("abbdba"));
        System.out.println(longestPalindrome("aacabdkacaa"));
    }



    // Nick White: https://www.youtube.com/watch?v=y2BD4MJqV20

    public static String longestPalindrome(String s) {
        if (s == null) return "";

        int start = 0;
        int end = 0;

        for (int i =0; i < s.length(); i++){
            int len = Math.max(
                    expandfromMid(s,i,i),
                    expandfromMid(s,i,i+1));
            if (len > end - start){
                start = i - ((len -1)/2);
                end = i + len/2;
            }

        }

        return s.substring(start, end+1);
    }

    private static int expandfromMid(String s, int left, int right){
        if (left>right || s== null) return 0;

        while(left>=0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return right - left - 1;
    }


    // it's not working for aacabdkacaa
    public static String longestPalindrome_DP_WRONG(String s) {
        //"abbdba"
        int[][] t = new int[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            t[i][i] = 1;
        }

        int x = 0, y =0, len = 1;

        /*            0 1 2 3 4 5
                      a b b d b a
     substring  len = 0,1,2,3,4,5 i.e. < s.length() i.e. 6 in this case
                  when len = 5, only applicable coordinates are 0,5 i.e. row = 0 to s.length() - 5 - 1

         */

        for (int l = 1; l < s.length()   ; l++) { // l => length of substring under consideration
            for (int i = 0; i < s.length()  - l ; i++) { // i = starting character
                int j = i + l;
                if (s.charAt(i) == s.charAt(j)){
                    t[i][j] = 2 + t[i+1][j-1];
                } else{
                    t[i][j] = Math.max(t[i+1][j], t[i][j-1]);
                }

                if (t[i][j] > len){
                    len = t[i][j];
                    x = i;
                    y = j;
                }
            }
        }

       return s.substring(x, y+1);
    }


}
