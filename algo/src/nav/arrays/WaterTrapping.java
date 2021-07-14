package nav.arrays;

public class WaterTrapping {
    public static int waterArea(int[] array) {
        if (array.length == 0) return 0;
        int leftMax = array[0], rightMax = array[array.length -1], v =0;
        int i = 0, j = array.length - 1;
        while (i < j){
            if (array[i] < array[j]){
                i++;
                leftMax = Math.max(array[i], leftMax);
                v = v + (leftMax - array[i]);
            } else {
                j--;
                rightMax = Math.max(array[j], rightMax);
                v = v + (rightMax - array[j]);
            }
        }

        return v;
    }
}
/*
    i       j
    .       .
    1,3,4,2,5
		                --
			      --    --
			   -- --    --
			   -- --    --
	       -- -- -- -- --
		  -- -- -- -- -- --
      ----------------------------

*/
