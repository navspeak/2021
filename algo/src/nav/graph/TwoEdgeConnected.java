package nav.graph;

import java.util.Arrays;
/*
Terms:
TreeEdge
BackEdge
CrossEdge
FwdEdge
Bridge

Check if the neighbour or its neigbour has a backedge => is two way connected.
Presence of bridge => not two way connected
 */

public class TwoEdgeConnected {
    public boolean twoEdgeConnectedGraph(int[][] edges) {
        if (edges.length == 0 ) return false; // just empty node, or even one node is two way connected
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
                if (neighbor != parentIndex) { // no need to dfs, just get value from arrivalTime array which is already populated
                    minArrivalTimeOfAnAncestor = Math.min(arrivalTime[neighbor], minArrivalTimeOfAnAncestor);
                }
            }
        }

        if (minArrivalTimeOfAnAncestor == currTime) {
            // it means it's a bridge meaning that to reach this you can only come via last anscestor
            // so if this is cut, the graph will be disconnected
            // however, exception is start node because we can't expect that to have a backedge
            if (parentIndex != -1) return -1; //bridge detected;
        }
        return minArrivalTimeOfAnAncestor; // if -1 was returned it would bubble up to start node signalling presence of bridge
    }
}


/*

           0          _X__            5
      /                                      \

   1        |                      |                4
       \
                                              /
          2         __               3



           0
      /

   1        |
       \

          2         __               3




 */