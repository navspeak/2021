package nav.recurison;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 [00] [01] [02] [03]
 [10] [11] [12] [13]
 [20] [21] [22] [23]
 [30] [31] [32] [33]
 */
public class _9NQueen1 {
    public static void main(String[] args) {
        _9NQueen1 obj = new _9NQueen1();
        obj.solveNQueens(4).forEach(list -> System.out.println(Arrays.toString(list.toArray())));
    }

    public List<List<String>> solveNQueens(int n) {
        int board[][] = new int[n][n];
        List<List<String>> result = new ArrayList<>();
        int ways = placeQueenInRowNum(0, board, result);
        return result;
    }



    private int placeQueenInRowNum(int row, int[][] board, List<List<String>> result){
        int ways = 0;
        int n = board.length;
        if (row == n) {
            addToResult(board, result);
            return 1;
        }
        for (int col=0; col < board.length; col++){
            if (canPlace(row, col, board) == true){
                  board[row][col] = 1;
                ways = ways + placeQueenInRowNum(row+1, board, result);
                board[row][col] = 0; //backtrack
            }
        }
        return ways;
    }

    private void addToResult(int[][] board, List<List<String>> result) {
        List<String> oneResult = new ArrayList<>();
        int n = board.length;
        for (int i = 0; i < n; i++) {
            String oneRow = "";
            for (int j = 0; j < n; j++) {
               oneRow += board[i][j] == 1 ? "Q": ".";
            }
            oneResult.add(oneRow);
        }
        result.add(oneResult);
    }

    // watch Nick White's https://www.youtube.com/watch?v=_NSc3b5BIXc
    private boolean canPlace(int row, int col, int[][] board){
        int n = board.length;
        int[][] dirs = {{-1,-1},{-1,0},{-1,1}};
        for (int[] dir: dirs) {
            int i = row + dir[0];
            int j = col + dir[1];
            while (i >=0 && j >= 0 && i < n && j < n){
                if (board[i][j] == 1) {
                    return false;
                }
                i = i + dir[0];
                j = j + dir[1];
            }
        }
        return true;
    }
}
