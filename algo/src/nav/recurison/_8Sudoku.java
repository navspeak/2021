package nav.recurison;

import java.util.Arrays;
import java.util.HashSet;

public class _8Sudoku {

    static final int ROWS = 9;
    static final int COLS = 9;
    static final int GRID = 3;
    static final int[][] board = new int[ROWS][COLS];
    public boolean isValidSudoku(char[][] board) {
        HashSet<String> seen = new HashSet<>();


        for(int i = 0; i < 9; i++){
            for(int j = 0; i < 9; j++){
                char val = board[i][j];
                if (Character.isDigit(val)){
                    boolean seenInRow = !seen.add(val+" found at row "+ i); // ADD returns false if already present
                    boolean seenInCol = !seen.add(val+" found at col "+ j);
                    boolean seenInbox = !seen.add(val+" found at box ["+ i/3 + "," + j/3 + "]");
                    if (seenInRow || seenInCol || seenInbox) return false;
                }
            }

        }
        return true;
    }
    // NP complete - O(9^(empty space))
    public static void main(String[] args) {
        char x = Character.forDigit(9 , 10);
        System.out.println(x == '9');
        int[][] board =  new int[][] {
                {7, 8, 0, 4, 0, 0, 1, 2, 0},
                {6, 0, 0, 0, 7, 5, 0, 0, 9},
                {0, 0, 0, 6, 0, 1, 0, 7, 8},
                {0, 0, 7, 0, 4, 0, 2, 6, 0},
                {0, 0, 1, 0, 5, 0, 9, 3, 0},
                {9, 0, 4, 0, 6, 0, 0, 0, 5},
                {0, 7, 0, 3, 0, 0, 0, 1, 2},
                {1, 2, 0, 0, 0, 7, 4, 0, 0},
                {0, 4, 9, 2, 0, 6, 0, 0, 7}
        };

        solve(board, 0,0);

        for (int[] row: board)
        System.out.println(Arrays.toString(row));

    }

    static boolean  solve (int[][] board, int row, int col){

        if (row == 9) {
            if (col == 8) return true;
            col++;
            row = 0;
        }

        if (board[row][col] != 0)
            return solve(board,row+1,col);
        for(int num =1 ; num<=9; num++) {
            if (placementValid(board, num, row, col)) {
                board[row][col] = num;
                if (solve(board, row+1, col) == true)
                    return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    static boolean placementValid(int[][] board, int num, int row, int col){
        for(int i = 0; i<9; i++){
            if (board[row][i] == num) return false;
            if (board[i][col] == num) return false;
            if(board[3 * (row/3) + i/3][3 * (col/3) + i%3] == num) return false;
        }

        /*
                           i=0,1,2,3,4,5,6,7,8,9
             00  01  02     (0+0/3,0+0%3)  (0+1/3, 0+1%3) ...
             10  11  12
             20  21  22

         */

//        // row check
//        for(int j = 0; j < 9; j++){
//            if (board[row][j] == num) return false;
//        }
//        // col check
//        for(int i = 0; i < 9; i++){
//            if (board[i][col] == num) return false;
//        }
//        // grid check
//        int rowOffset = row / 3;
//        int colOffset = col / 3;
//        for (int i = rowOffset*3; i < rowOffset*3 + 3; i++){
//            for (int j = colOffset*3; j < colOffset*3 + 3; j++) {
//                if (board[i][j] == num) return false;
//            }
//        }
        return true;
    }

    /*
         0 1 2 | 3 4 5 | 6 7 8
       0 1     |
       1       |
       2       |
       --------
       3
       4
       5
       ---------
       6
       7
       8


        boolean f(i,j) {
            if (i == 9) { i=0, j= j+1 }
            if (t[i][j] != 0)
                return f(i+1,j);
            for(int k =1 ; k<=9; k++) {
               if placementValid(k, i, j) {
                    T[i][j] = k;
                    if (f(i+1, j) == true) return true;
                    T[i][j] = 0
               }
            }
            return false
        }


        boolean placementValid(int num, int i, int j) {
            // row check
            for(int k = 0; k < COLS; k++) {
               if (T[i][k] == num) return false;
            }
            // col check
            for(int k = 0; k < ROWS; k++) {
               if (T[k][i] == num) return false;
            }
            // grid check

           rowOffSet = i /3;
           colOffset = j / 3;
           for (int row = (rowOffset)*3; row < (rowOffset)*3+3;row++){
              for (int col = (colOffset)*3; col < (colOffset)*3+3;col++){
                    if (T[row][col] == T[i][j]) {
                        return false;
                    }
               }
           }

           // all checks passed
           return true;

        }





     */


}
