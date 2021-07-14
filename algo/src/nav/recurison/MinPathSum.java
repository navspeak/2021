package nav.recurison;

import java.util.HashMap;
import java.util.Map;

public class MinPathSum {
    //static int min = Integer.MAX_VALUE;

//    public int minPathSum(int[][] grid) {
//        //min = Integer.MAX_VALUE;
//        return dfs(0,0,0, grid, new HashMap<>());
//    }
    //
//    private int dfs(int x, int y, int sum, int[][] grid){
//        if (x == grid.length -1 && y == grid[0].length -1)
//            return sum + grid[x][y];
//        if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length){
//            return Integer.MAX_VALUE;
//        }
//        sum = sum + grid[x][y];
//        int ret1 = dfs(x,y+1, sum, grid);
//        int ret2 = dfs(x+1,y, sum, grid);
//        return Math.min(ret1, ret2);
//    }
//Doesn't work
//    private int dfs(int x, int y, int sum, int[][] grid, Map<String, Integer> map){
//        String key = x+"-"+y;
//        if (map.containsKey(key))
//            return map.get(key);
//        if (x == grid.length -1 && y == grid[0].length -1){
//            int val = sum + grid[x][y];
//            //map.put(key, val); You do not put this to memo
//            return val;
//        }
//        if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length){
//            map.put(key, Integer.MAX_VALUE);
//            return Integer.MAX_VALUE;
//        }
//        sum = sum + grid[x][y];
//        int ret1 = dfs(x,y+1, sum, grid, map);
//        int ret2 = dfs(x+1,y, sum, grid, map);
//        int val = Math.min(ret1, ret2);
//        map.put(key, val);
//        return val;
//    }

    public int minPathSum_Rec(int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;

        return minpath(grid,n-1,m-1);

    }

    public int minPathSum(int[][] grid) {
        int[][] T = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
                if (i == 0 && j == 0)
                    T[i][j] = grid[i][j];
                else if (i == 0)
                    T[i][j] = grid[i][j] + T[i][j-1];
                else if (j == 0)
                    T[i][j] = grid[i][j] + T[i-1][j];
                else
                    T[i][j] = grid[i][j] + Math.min(T[i-1][j], T[i][j-1]);
            }
        }
        return T[grid.length-1][grid[0].length-1];
    }


    //recurisve - start from end
    public int minpath(int[][] grid,int r, int c){
        if(r == 0 && c == 0)
            return grid[r][c];
        else if(r == 0)
            return grid[r][c] + minpath(grid, r, c - 1);
        else if(c == 0)
            return grid[r][c] + minpath(grid, r- 1, c);
        else
            return grid[r][c] + Math.min(minpath(grid, r - 1, c), minpath(grid, r, c - 1));
    }

    public static void main(String[] args) {
        MinPathSum obj = new MinPathSum();
        System.out.println(obj.minPathSum(new int[][]{{1,3,1},{1,5,1},{4,2,1}}));
        System.out.println(obj.minPathSum(new int[][]{{1,2},{1,1}}));
    }
}
