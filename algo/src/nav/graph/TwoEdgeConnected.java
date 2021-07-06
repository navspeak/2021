package nav.popularAlgo;

import java.util.Arrays;
/*
TreeEdge
BackEdge
CrossEdge
FwdEdge

Check if the neighbour or its neigbour has a backedge
 */

public class TwoEdgeConnected {
    public boolean twoEdgeConnectedGraph(int[][] edges) {
        int[] arrivalTime = new int[edges.length];
        Arrays.fill(arrivalTime, -1);
        if (getMinArrivalTimeDfs(0 , -1,  0, arrivalTime, edges) == -1) return false;
        for(int t: arrivalTime){
            if (t == -1) return false;
        }
        return true;
    }

    // get min arrival time of any of its ancestor & tells if there is a backedge or not
    private int getMinArrivalTimeDfs(int currIndex, int parentIndex,  int currTime, int[] arrivalTime, int[][] edges) {
        arrivalTime[currIndex] = currTime;
        int minArrivalTimeOfAnAncestor = currTime;
        for (int neighbor: edges[currIndex]){
            if (arrivalTime[neighbor] == -1){ // not visited
                minArrivalTimeOfAnAncestor = Math.min(minArrivalTimeOfAnAncestor, getMinArrivalTimeDfs(neighbor, currIndex, currTime+1, arrivalTime, edges));
            } else {
                if (neighbor != parentIndex) {
                    minArrivalTimeOfAnAncestor = Math.min(arrivalTime[neighbor], minArrivalTimeOfAnAncestor);
                }
            }
            if (minArrivalTimeOfAnAncestor)
        }
    }
}


/*

           0          _X__            5
      /                                      \

   1        |                      |                4
       \
                                              /
          2         __               3







 */