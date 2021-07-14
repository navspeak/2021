package nav.graph;

import java.util.*;
// O(V+E)logV time and o(V) space
public class Dijkstras {
    /*
             [0]                   [4]
                 \7/         /3\                             \7/ = [0->7]
                        [1]
                \6/           \20/                     1->14, 20
             [2]     14 >            [14]

     */
    public static void main(String[] args) {
        PriorityQueue<Vertex> heap = new PriorityQueue<>();
        Vertex v1 = new Vertex();
        v1.index = 0;
        v1.minDistance = 10;

        Vertex v2 = new Vertex();
        v2.index = 0;
        v2.minDistance = 12;
        System.out.println(v1.equals(v2));

        Vertex v3 = new Vertex();
        v3.index = 0;
        v3.minDistance = 12;
        System.out.println(v1.equals(v3));

        heap.add(v1);
        heap.add(v2);
        heap.add(v3);
        System.out.println(heap.size()); // note that there is no use of writing your own equals

        Dijkstras obj = new Dijkstras();
        int start = 0;
        int[][][] edges = {
                {{1, 7}},
                {{2, 6}, {3, 20}, {4, 3}},
                {{3, 14}},
                {{4, 2}},
                {},
                {}
        };

        int[][][] edges1 = {
                {{1, 2}},
                {{2, 3}, {3, 10}},
                {{3, 1}},
                {},
        };
        int[] expected = {0, 7, 13, 27, 10, -1};
        int[] expected1 = {0, 2, 5, 6};
        int [] actual = obj.dijkstrasAlgorithm(start, edges1);
        System.out.println(Arrays.toString(actual));
        int[][] times = new int[][]{{2,1,1}, {2,3,1},{3,4,1}};
        System.out.println(obj.networkDelayTime(times, 4, 2)); // expected 2
        times = new int[][]{{1,2,1}};
        System.out.println(obj.networkDelayTime(times, 2, 1)); // expected 1
        times = new int[][] {{1,2,1},{2,3,2},{1,3,4}};
        System.out.println(obj.networkDelayTime(times, 3, 1)); // 3
    }
    public int[] dijkstrasAlgorithm(int start, int[][][] edges) {
        PriorityQueue<Vertex> heap = new PriorityQueue<>();
        int [] minDistance = new int[edges.length];
        Arrays.fill(minDistance, -1);
        minDistance[start] = 0;
        Vertex startVertex = new Vertex();
        startVertex.index = start;
        startVertex.minDistance = 0;
        startVertex.pred = -1;
        heap.add(startVertex);

        Set<Integer> visited = new HashSet<>();
        while(!heap.isEmpty()){
            Vertex currVertex = heap.remove();
            int currIndex = currVertex.index;
            if (visited.add(currIndex) == false) continue; //set.add returns false if present. so this is checking if already visited
            for (int[] edge: edges[currIndex]){
                Vertex v = new Vertex();
                v.index = edge[0];
                int minDistanceOfThisNode = minDistance[edge[0]] == -1? Integer.MAX_VALUE:  minDistance[edge[0]]  ;
                if (currVertex.minDistance + edge[1] < minDistanceOfThisNode) {
                    v.pred = currIndex;
                    v.minDistance = currVertex.minDistance + edge[1];
                    minDistance[edge[0]] = v.minDistance;
                    heap.add(v);
                }
            } // O(V+ElogV)
        }
        return minDistance;
    }


    //https://leetcode.com/problems/network-delay-time/submissions/
    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, Vertex> vertices = new HashMap<>();
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 0; i < times.length; i++) {
            vertices.put(times[i][0], new Vertex(times[i][0], Integer.MAX_VALUE));
            vertices.put(times[i][1], new Vertex(times[i][1], Integer.MAX_VALUE));
            int[] edge = new int[]{times[i][1], times[i][2]};
            graph.compute(times[i][0], (key, val) -> {
                List<int[]> value = graph.get(key);
                if (val == null) {
                    value = new ArrayList<>();
                    value.add(edge);
                    return value;
                }
                value.add(edge);
                return value;
            });
        }

        vertices.get(k).minDistance = 0;
        PriorityQueue<Vertex> minHeap = new PriorityQueue<>((v1, v2)-> Integer.compare(v1.minDistance, v2.minDistance));
        minHeap.add(vertices.get(k));
        int[] minDistanceArray = new int[n+1];
        Arrays.fill(minDistanceArray, -1);
        Set<Vertex> visited = new HashSet<>();
        while (!minHeap.isEmpty()) {
            Vertex curr = minHeap.remove();
            if (curr.explored == true) continue;
            curr.explored = true;
            visited.add(curr);
            if (!graph.containsKey(curr.index)) continue;
            for (int[] neighborEdge : graph.get(curr.index)) {
                Vertex neighbor = vertices.get(neighborEdge[0]);
                int distance = curr.minDistance + neighborEdge[1]; // [Edge, Weight]
                if (distance < neighbor.minDistance) {
                    neighbor.minDistance = distance;
                }
                minDistanceArray[neighbor.index] =  neighbor.minDistance;
                minHeap.add(neighbor);
            }

        }
        Arrays.sort(minDistanceArray);
        if (visited.size() < n) return -1;
        return  minDistanceArray[n];
    }



    private static class Vertex implements Comparable<Vertex>{
        int index;
        int minDistance = Integer.MAX_VALUE;
        int pred;
        boolean explored = false;
        Vertex(){};
        Vertex(int index, int minDistance){
            this.index = index;
            this.minDistance = minDistance;
        }
        public int compareTo(Vertex v){
            return Integer.compare(this.minDistance, v.minDistance);
        }
    }
}
