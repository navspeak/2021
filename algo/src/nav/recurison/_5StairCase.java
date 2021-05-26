package nav.recurison;

public class _5StairCase {
    static int[] memo;

    /*
       0 1 2 3 4 5  6 7         (3)
       1 1 2 4 7 13 24
    T=1,2
      1 1 2 4 7
  a[i] = T + a[i-1] + a[i-1-maxSteps]
  a[3] = 2 + a[2] - a[-1] = 2 + 2 = 4
  a[4] = 4 + a[3] - a[0] = 4 + 4 -1 = 7
  a[5] = 7 + a[4] - a[1] = 7 + 7 - 1 = 13
     */

    public static int staircaseTraversal__(int height, int maxSteps) { //O(N)
        int [] T = new int[height+1];
        T[0] = 1;
        int ways = 0;
        for (int i = 1; i < height + 1 ; i++) {
            T[i] = ways + T[i - 1] - ((i > maxSteps) ? T[i - 1 - maxSteps] : 0);
            ways = T[i];
        }
        return T[height];
    }

    public int staircaseTraversal_(int height, int maxSteps) {
        int[] ans = new int[height+1];
		/*
		   h=4, n=3
			                         f(4) => 7
									4=f(3)                     2= f(2) f(1)	=1 =>3
						2=f(2)	  f(1)=1  f(0)=1
				1=f(1)   f(0)=1

		 */
        ans[0]  = ans[1] =1;

        for (int i=2; i< height+1; i++){
            for(int j=1; j<maxSteps+1; j++){
                if (i>=j)
                    ans[i] += ans[i-j];
            }
        }
        return ans[height];
    }

    public static int staircaseTraversal(int height, int maxSteps){
        return staircaseTraversal(height, maxSteps, new int[height+1]);
    }

    public static int staircaseTraversal(int height, int maxSteps, int[] memo) {
        if (memo[height] > 0) return memo[height]; // O(S^h) without memo reduced to O(S*h), space = O(n)
        if (height <= 1) {
            return 1;
        }
        int noOfWays = 0;
        for (int i = 1; i <= maxSteps; i++){
            if (height >= i)
                noOfWays+=staircaseTraversal(height-i, maxSteps, memo);
        }
        memo[height] = noOfWays;
        return noOfWays;
    }


    public static void main(String[] args) {
        System.out.println(staircaseTraversal__(7,3));
        System.out.println(staircaseTraversal(7,3));
    }
}
