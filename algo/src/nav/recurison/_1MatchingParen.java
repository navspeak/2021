package nav.recurison;

import java.util.ArrayList;
import java.util.List;

public class _1MatchingParen {

    private final List<String> result = new ArrayList<>();
    private static int N;

    public List<String> generateParenthesis(int n) {
        generateParenthesisRec(n, "", n, n);
        return result;
    }

    public static void main(String[] args) {
        (new _1MatchingParen()).generateParenthesis(2);
    }

/*Add open brace: if remainingOpen > 0
Add closing brace: if remainingClose > remainingOpen
Base: remainingOpen == remainingClose == 0*/


    // Time complexity factorial : maybe catalan no.
    /*
                     f("", 2,2)
                 (,1,2     (),1,1
               *((,0,1     ()()


 (())
 ()()

     */
    private void generateParenthesisRec(int n, String str, int remOpen, int remClose){
        //base
        if (remOpen == 0 && remClose == 0) {
            result.add(str);
            return;
        }

        if (remOpen > 0) generateParenthesisRec(n, str+"(", remOpen-1, remClose);
        if (remClose > remOpen) generateParenthesisRec(n, str+")",remOpen, remClose-1);
    }


}

class BruteForceSolution {
    public List<String> generateParenthesis(int n) {
        List<String> combinations = new ArrayList();
        generateAll(new char[2 * n], 0, combinations);
        return combinations;
    }

    public void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length) {
            if (valid(current))
                result.add(new String(current));
        } else {
            current[pos] = '(';
            generateAll(current, pos+1, result);
            current[pos] = ')';
            generateAll(current, pos+1, result);
        }
    }

    public boolean valid(char[] current) {
        int balance = 0;
        for (char c: current) {
            if (c == '(') balance++;
            else balance--;
            if (balance < 0) return false;
        }
        return (balance == 0);
    }
}