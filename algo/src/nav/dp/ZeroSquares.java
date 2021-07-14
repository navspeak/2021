package nav.dp;

import java.util.ArrayList;
import java.util.List;

public class ZeroSquares {
    public static void main(String[] args) {
        int[][] matrix = {
                {0,1,0},
                {1,0,0},
                {0,0,0}
        };
//        List<List<Integer>> list = new ArrayList<>();
//        int[][] matrixFromList = new int[list.size()][list.get(0).size()];
//        for (int i = 0; i < list.size(); i++) {
//            for (int j = 0; j < list.get(0).size(); j++) {
//                matrixFromList[i][j] = list.get(i).get(j);
//            }
//        }

        System.out.println(squareOfZeroes(matrix));
    }
    public static boolean squareOfZeroes(int[][] matrix) {

        List<Square> squares  = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                findAllSquares(matrix, i,j, 1, squares);
            }
        }

        for (Square sq: squares){
            if (sq.traverseBottomRow(matrix) == false &&
                    sq.traverseTopRow(matrix) == false &&
                    sq.traverseLeftCol(matrix) == false &&
                    sq.traverseTopRow(matrix) == false)
                continue;
            return true;
        }
        return true;
    }

    static void  findAllSquares(int[][] matrix, int x, int y, int end, List<Square> squares ){
        if (end ==matrix.length) return;
        Square sq = new Square(x,y,end+1);
        squares.add(sq);
        findAllSquares(matrix, x,y, end+1, squares);
    }

    static class Square {
        int x; // top left x
        int y; // top right y
        int len;
        Square(int x, int y, int len){
            this.x = x;
            this.y = y;
            this.len = len;
        }

        public boolean traverseTopRow(int[][] matrix){
            for (int i = 0; i < len; i++) {
                if (matrix[x][y+i] == 1) return false;
            }
            return true;
        }

        public boolean traverseBottomRow(int[][] matrix){
            for (int i = 0; i < len; i++) {
                if (matrix[x+len-1][y+i] == 1) return false;
            }
            return true;
        }

        public boolean traverseLeftCol(int[][] matrix){
            for (int i = 0; i < len; i++) {
                if (matrix[x+i][y] == 1) return false;
            }
            return true;
        }

        public boolean traverseRightCol(int[][] matrix){
            for (int i = 0; i < len; i++) {
                if (matrix[x+i][y+len-1] == 1) return false;
            }
            return true;
        }
    }
}
/*
      [xy]                      => square of size 1


      [xy]       [x,y+1]         => square of size 2
      [x+1,y]    [x+1,y+1]

      [xy]     ..      [x,y+len-1]         => square of size len
      ..
      [x+len-1,y]  ..  [x+len-1,y+len-1]

      ===
      [xy] => len => To define a square I need starting coords and len



 */