package nav.recurison;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class IslandProblem {
    // https://leetcode.com/problems/max-area-of-island/submissions/
    public int maxAreaOfIsland(int[][] grid) {
        int area = 0;
        for (int i = 0; i < grid.length; i++){
            for(int j =0; j<grid[0].length;j++){
                if (grid[i][j] == 1){
                    area = Math.max(area, dfs(i,j,grid));
                }
            }
        }
        return area;
    }

    private int dfs(int x, int y, int[][] grid){
        if (x < 0  || y < 0 || x >= grid.length || y>=grid[0].length || grid[x][y] != 1) return 0;
        grid[x][y] = 2;
        return 1 + dfs(x+1,y, grid)
                + dfs(x-1,y,  grid)
                + dfs(x,y+1,  grid)
                + dfs(x,y-1,  grid);
    }

    public static void main(String[] args) {
        int[][] input =
                new int[][] {
                        {1, 0, 0, 0, 0, 0},
                        {0, 1, 0, 1, 1, 1},
                        {0, 0, 1, 0, 1, 0},
                        {1, 1, 0, 0, 1, 0},
                        {1, 0, 1, 1, 0, 0},
                        {1, 0, 0, 0, 0, 1},
                };
        int[][] expected =
                new int[][] {
                        {1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 1, 1},
                        {0, 0, 0, 0, 1, 0},
                        {1, 1, 0, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 0, 1},
                };
        RemoveIsland obj1 = new RemoveIsland();
        System.out.println(Arrays.deepToString(obj1.removeIslands(input)));

        MinPassToRemoveNegatives obj2 = new MinPassToRemoveNegatives();
        System.out.println(obj2.minimumPassesOfMatrix(new int[][] {{0, -1, -3, 2, 0}, {1, -2, -5, -1, -3}, {3, 0, 0, -4, -1}})); // expected 3
    }
}

class RemoveIsland {

    private static int[][] grid;
    private int R;
    private int C;

    public int[][] removeIslands(int[][] matrix) {
        grid = matrix;
        R = matrix.length;
        C = matrix[0].length;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (matrix[i][j] == 1) {
                    markLandConnectedToBorder(i, j);
                }
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (matrix[i][j] == 2) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    void markLandConnectedToBorder(int x, int y) {
        if (x < 0 || y < 0 || x >= R || y >= C || grid[x][y] != 1) return;
        if (x == 0 || y == 0 || x == R-1 || y == C-1 || grid[x - 1][y] == 2 || grid[x + 1][y] == 2 || grid[x][y - 1] == 2 || grid[x][y + 1] == 2) {
            grid[x][y] = 2;
        } else return;

        markLandConnectedToBorder(x, y + 1);
        markLandConnectedToBorder(x, y - 1);
        markLandConnectedToBorder(x + 1, y);
        markLandConnectedToBorder(x - 1, y);



    }
}

class MinPassToRemoveNegatives {
    public int minimumPassesOfMatrix(int[][] matrix) {
        Queue<int[]> queue = new LinkedList<>();
        final int R = matrix.length;
        final int C = matrix[0].length;
        int negatives = 0;
        for(int i = 0; i<R; i++){
            for(int j = 0; j<C; j++){
                if (matrix[i][j] > 0)
                    queue.add(new int[]{i,j});
                else if (matrix[i][j] < 0) negatives++;
            }
        }
        queue.add(new int[]{-1,-1});
        int count = 0;
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        while (!queue.isEmpty()){
            int[] curr = queue.poll();
            int cx = curr[0];
            int cy = curr[1];
            if (cx == -1 ) {
                count++;
                if (!queue.isEmpty()) {
                    queue.add(new int[]{-1,-1});
                }
                continue;
            }
            for(int[] dir: dirs){
                int x = cx + dir[0];
                int y = cy + dir[1];
                if (x < R && y < C && x >= 0 && y >= 0 && matrix[x][y] < 0){
                    matrix[x][y] = matrix[x][y] * -1;
                    negatives--;
                    queue.add(new int[]{x,y});
                }
            }

        }

        return negatives == 0 ? count-1: -1;
    }
}
