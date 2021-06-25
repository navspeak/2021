package nav.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintMatrixZigZag {

    public static void main(String[] args) {
        List<List<Integer>> array = new ArrayList<>();
        array.add(Arrays.asList(1,3,4,10));
        array.add(Arrays.asList(2,5,9,11));
        array.add(Arrays.asList(6,8,12,15));
        array.add(Arrays.asList(7,13,14,16));
        System.out.println(Arrays.toString(zigzagTraverse(array).toArray()));
    }

    public static List<Integer> zigzagTraverse(List<List<Integer>> array) {
        int[][] grid = new int[array.size()][array.get(0).size()];
        List<Integer> result = new ArrayList<Integer>();
        if (array.size() == 0) return result;
        int i = 0, j = 0;
        for (List<Integer> list: array){
            for (int a: list){
                grid[i][j++] = a;
            }
            i++;
            j=0;
        }
        i=0; j=0;
        while(i < grid.length && i >= 0 && j < grid[0].length && j >= 0){
            int[] ret = goDown(i,j,grid, result);
            ret = goDiagonallyUp(ret[0],ret[1],grid, result);
            ret = goRight(ret[0],ret[1],grid, result);
            ret = goDiagonallyDown(ret[0],ret[1],grid, result);
            i = ret[0];
            j = ret[1];
        }
        return result;
    }
    /*



     */

    private static int[] goDown(int i, int j, int[][] grid, List<Integer> result){
        if (i >= grid.length) return new int[]{i,j};
        result.add(grid[i][j]);
        return new int[]{i+1, j};
    }

    private static int[] goDiagonallyUp(int i, int j, int[][] grid, List<Integer> result){
        if (i <0 || j >=grid[0].length) return new int[]{i,j};
        while (i >= 0 && j <= grid[0].length){
            result.add(grid[i][j]);
            i--; j++;
        }
        return new int[]{i, j};
    }

    private static int[] goRight(int i, int j, int[][] grid, List<Integer> result){
        if (i >= grid.length) return new int[]{i, j};
        result.add(grid[i][j]);
        return new int[]{i, j+1};
    }

    private static int[] goDiagonallyDown(int i, int j, int[][] grid, List<Integer> result){
        if (i >= grid.length || j < 0) return new int[]{i, j};
        while (i < grid.length && j >= 0) {
            result.add(grid[i][j]);
            i++; j--;
        }
        return new int[]{i, j};
    }

}
