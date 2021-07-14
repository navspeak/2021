package nav.arrays;
//https://leetcode.com/problems/gas-station/submissions/
public class GasStation {
    public static void main(String[] args) {
        System.out.println(canCompleteCircuit(new int[]{2,3,4}, new int[]{3,4,3}));
        System.out.println(canCompleteCircuit(new int[]{5,1,2,3,4}, new int[]{4,4,1,5,1}));
        /*
               0 1 2 3 4
               -------
        gas:   5 1 2 3 4
        cost:  4 4 1 5 1
               ---------
               1 -3 1 -2 3  (Total = 0 => if it were not 0 it were not possible to make a round trip
         */
    }
    public static int canCompleteCircuit(int[] gas, int[] cost) {
      int total = 0, startingIndex = 0, tank =0;
        for (int i = 0; i < gas.length; i++) {
            int gasMinusCost = gas[i] - cost[i];
            tank +=gasMinusCost;
            if (tank < 0) {
                startingIndex = i + 1;
                tank = 0;
            }
            total +=gasMinusCost;
        }

        return (total < 0) ? -1: startingIndex;
    }
}
