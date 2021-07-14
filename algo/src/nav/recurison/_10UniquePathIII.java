package nav.recurison;
// https://leetcode.com/problems/unique-paths-iii/
// each path must traverse all empty paths
public class _10UniquePathIII {
    public static void main(String[] args) {
        _10UniquePathIII obj = new _10UniquePathIII();
        int[][] grid = {{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 2, -1}};
        int ways = obj.uniquePathsIII(grid);
        System.out.println(ways);
    }

    /*
    On a 2-dimensional grid, there are 4 types of squares:

1 represents the starting square.  There is exactly one starting square.
2 represents the ending square.  There is exactly one ending square.
0 represents empty squares we can walk over.
-1 represents obstacles that we cannot walk over.
Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once.


     */

    // catch is that you have to cover all the cells
    public int uniquePathsIII(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;
        int x=0,y=0,emptyCells=0;

        for(int i = 0; i <m; i++){
            for(int j = 0; j<n; j++){
                if (grid[i][j] == 1) {
                    x = i;
                    y = j;
                }

                if (grid[i][j] == 0) {
                    emptyCells++;
                }
            }
        }

        return dfs(grid, x,y, emptyCells);

    }

    private int dfs(int[][] grid, int x, int y, int emptyCells){
        int m = grid.length;
        int n = grid[0].length;

        if (x<0 || y<0 || x>=m || y>=n || grid[x][y] == -1 || grid[x][y] == 3) return 0; // we choose to mark 3 as visited
        if (grid[x][y] == 2 ) { // end
            if ( emptyCells == -1)
                return 1;
            else
                return 0;
        }

        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        int ways = 0;
        for(int[] dir: dirs){
            grid[x][y] = 3;
            int newX = x+dir[0];
            int newY = y+dir[1];
            ways = ways + dfs(grid, newX, newY,emptyCells - 1);
            grid[x][y] = 0;
        }
        return ways;
    }
}
