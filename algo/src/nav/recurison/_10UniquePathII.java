package nav.recurison;

import java.util.HashMap;
import java.util.Map;

public class _10UniquePathII {
    Map<String, Integer> map = new HashMap<>();
    public int uniquePathsWithObstacles(int[][] grid) {
        return move(grid, 0,0);
    }
    //https://leetcode.com/submissions/detail/503942354/
    private int move(int[][] grid, int x, int y){
        // without memo - O(2^(M*N)). with memo - O(m*n)
        String key = x+":"+y;
        if (map.containsKey(key)) return map.get(key);
        int m = grid.length;
        int n = grid[0].length;

        if (x <0 || y < 0 || x == m || y == n || grid[x][y] > 0) return 0;
        if (x == m-1 && y == n-1) return 1;
        grid[x][y] = 3;
        int ways = move(grid, x,y+1) + move(grid, x+1, y);
        grid[x][y] = 0;
        map.put(key, ways);
        return ways;
    }
}
