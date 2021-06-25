package nav.recurison;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _9NQueen {

    // also watch https://www.youtube.com/watch?v=_NSc3b5BIXc
    public static void main(String[] args) {
        (new _9NQueen()).placeQueen(4);

    }

    private static int[][] board;
    private static int[] possibleColumns;



    public void placeQueen(int n) {
        board = new int[n][n];
        int ways = 0;
        placeQueenRec(0, n);
        //System.out.println(w);
        for (int i = 0; i < n; i++){
            int sum = 0;
            for(int j = 0; j < n; j++){
                System.out.printf("%d ", board[i][j]);
            }
            System.out.println();
        }

    }


    private boolean placeQueenRec(int col, int n){
          if (col == n) {
            return true;
        }
        for (int i = 0; i < n; i++){

            if (canPlaceQueen(i,col) == true){
                board[i][col] = 1;
                if (placeQueenRec(col+1, n) == true) {
                    return true;
                }
                board[i][col] = 0;
            }
        }
        return false;
    }

    private boolean canPlaceQueen(int x, int y) {
        int n = board.length;
        int[][] dirs = {{0,-1}, {-1,-1}, {-1,1}}; // we are going column wise so no need to go right side
        for (int dir[]: dirs){
            while(x+dir[0] >= 0 && y+dir[1] >= 0 && x+dir[0] < n && y+dir[1] < n){
                x = x+dir[0];
                y = y+dir[1];
                if (board[x][y] == 1) return false;
            }
        }

//        int[] dir = {-1, 0, 1};
//        for (int dx : dir) {
//            for (int dy : dir) {
//                if (dx == 0 && dy == 0) continue;
//                int i = x + dx, j = y + dy;
//                while (i >= 0 && j >= 0 && i < n && j < n) {
//                    if (board[i][j] == 1) return false;
//                    i = i + dx;
//                    j = j + dy;
//                }
//
//            }
//        }
        return true;

    }
//        int n = board.length;
//        int sum = 0;
//        // Row wise ->
//        for (int i = 0; i< n; i++){
//            if (board[x][i] == 1) return false;
//        }
//
//        // col wise \/
//        for (int i = 0; i< n; i++){
//            if (board[i][y] == 1) return false;
//        }
//
//        //      /
//        //     /  Towards bottom left
//        int i = x, j = y;
//        sum = 0;
//        while(i < n && j >= 0){
//            sum = sum + board[i++][j--];
//            if (sum >= 1) return false;
//        }
//
//        //      / Towards Top right
//        //     /
//        i = x; j = y;
//        while(i >= 0 && j < n){
//            sum = sum + board[i--][j++];
//            if (sum >= 1) return false;
//        }
//
//        //      \ Towards Top Left
//        //       \
//        i = x; j = y;
//        sum = 0;
//        while(i >= 0 && j >=0){
//            sum = sum + board[i--][j--];
//            if (sum >= 1) return false;
//        }
//
//        //      \
//        //       \ Towards bottom right
//        i = x; j = y;
//        while(i < n && j < n){
//            sum = sum + board[i++][j++];
//            if (sum >= 1) return false;
//        }
//
//        return true;


}
