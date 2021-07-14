package nav.string;

public class RegexMatch {
    public static void main(String[] args) {
        String p1 = "a*";
        char x = Character.forDigit(9, 10);
        System.out.println(x);
        System.out.println("abcde".substring(1, "abcde".length())); //bcde
        System.out.println("abcde".substring(1, "abcde".length()-1)); //bcd
        System.out.println("abcde".substring(2, "abcde".indexOf("c")+1)); //c
        String s = "aaa";
        int i = 0;
        while(i < s.length() && s.charAt(i) == 'a') i++;
        System.out.println(i + " - " + s.substring(i));
        System.out.println(s.substring(3));
        boolean isFirstCharAlphabet = Character.isAlphabetic(p1.charAt(0));
        boolean isSecondCharAlphabet = Character.isAlphabetic(p1.charAt(0));
        System.out.println(isMatch("aaaa", "a*"));
        System.out.println(isMatch("mississippi", "mis*is*p*.")); // false
        System.out.println(isMatch("aab", "c*a*b")); // true
        System.out.println(isMatch("ab", ".*")); // true

        int a = 'a' - 'a';
        int b = 'b' - 'a';
        System.out.println(a);
        System.out.println(b);
        int xplus2 = ('x' - 'a' + 2)%26;
        System.out.println(xplus2);
        char zInnum = 25+'a';
        System.out.println(zInnum);

        char[] alphabets = new char[52];
        String characters = "BstehetsiogEAxpelrtx";
        for (char c: characters.toCharArray()){
            int index = c > 'Z'? (c - 'a') + 26 : c - 'A';
            alphabets[index] +=1;
        }

        for (int j = 0; j < 52; j++) {
            int intVal = j < 26 ? 'A'+j : (j - 26) + 'a';
            char c = (char) intVal;
            System.out.println(c);

        }

    }

    public static boolean isMatch(String s, String p) {
        if (p.length() == 0) return s.length() == 0;
        if (p.length() == 1 && Character.isAlphabetic(p.charAt(0)))
            return s.length() == 1 && s.charAt(0) == p.charAt(0);
        if (p.length() == 1 && p.charAt(0) == '.'){
            return s.length() == 1;
        }
//        if (p.length() == 1 && p.charAt(0) == '*'){ // Not valid
//            return false;
//        }
        /*
             p =  a*
               =  a*xxx
               =  .*xxx
               =  axxxx
               =  .xxxx
         */
        if (p.charAt(1) == '*'){
             // abc, a* => a & a match =>bc,a*
            // abc, .* => a & . will match => bc, .* => b & . match => c, .* (base case)
            // aaab a* => aab a* => ab a* => b
            if (s.length() == 2 && p.charAt(0) == '.'  || s.charAt(0) == p.charAt(0) ){
                return isMatch(s.substring(1), p.substring(2));
            }
            if (p.charAt(0) == '.'  || s.charAt(0) == p.charAt(0))
                return isMatch(s.substring(1), p);

            int i = 0;
            while(i < s.length() && s.charAt(i) == p.charAt(0)){
                i++;
            }
            return isMatch(s.substring(i), p.substring(2));
        }
        // p = .xxx
        if (p.charAt(1) == '.'){
            return isMatch(s.substring(1),p.substring(1));
        }
        return isMatch(s.substring(1), p.substring(1));
    }
}
