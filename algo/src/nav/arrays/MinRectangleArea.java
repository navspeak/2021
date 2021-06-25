package nav.arrays;

import java.util.*;

public class MinRectangleArea {
    static Map<String, Integer> mapNChoosek = new HashMap<>();
    public static void main(String[] args) {
        int[][] points = {
                {0, 0},
                {4, 4},
                {8, 8},
                {0, 8},
                {0, 4},
                {6, 0},
                {6, 4},
                {8, 0},
                {8, 4},
                {6, 2},
                {2, 4},
                {2, 0}
        };

        List<Integer[]> coords =
                new ArrayList<>(Arrays.asList(
                                new Integer[]{0, 0},
                                new Integer[]{0, 1},
                                new Integer[]{1, 1},
                                new Integer[]{1, 0},
                                new Integer[]{2, 1},
                                new Integer[]{2, 0},
                                new Integer[]{3, 1},
                                new Integer[]{3, 0},
                                new Integer[]{1, 3},
                                new Integer[]{3, 3}
                ));
        int numRec = rectangleMania(coords);
        System.out.println(numRec);

        int area = minArea(points);
        System.out.println(area);
    }

    private static class Edge {
        int y1; int y2;
        int x;
        String name;
        void set(int a, int b, int x){
            this.y1 = a;
            this.y2 = b;
            this.x = x;
            this.name = a+"-"+b;
        }
    }

    public static int minArea(int[][] points){
//        Arrays.sort(points, (x,y)->{
//            if (x[0] != y[0])
//                return Integer.compare(x[0],y[0]);
//            else
//                return Integer.compare(x[1],y[1]);
//        });

        Map<Integer, List<Integer>> Ys_At_an_X = new HashMap<>();
        Map<String,Integer> edges = new HashMap<>();
        Set<Integer> x_coordinates = new TreeSet<>();
        for(int i = 0; i < points.length; i++){
            int x = points[i][0], y = points[i][1];
            x_coordinates.add(x);
            if (Ys_At_an_X.containsKey(x)){
                int currY = Ys_At_an_X.get(x).get(0);
                if (y> currY){
                    Ys_At_an_X.get(x).add(y);
                } else{
                    Ys_At_an_X.get(x).add(0,y);
                }
            } else {
                List<Integer> ys = new ArrayList<>();
                ys.add(y);
                Ys_At_an_X.put(x,ys);
            }
        }


        int area = Integer.MAX_VALUE;

        for (int x: x_coordinates ){
            List<Integer> list = Ys_At_an_X.get(x);
            list.sort(Comparator.naturalOrder());
            for (int i = 0; i < list.size() -1; i++){
                int y1 = list.get(i);
                for (int j = i + 1; j < list.size(); j++) {
                    int y2 = list.get(j);
                    String edge = y1+","+y2;
                    if (edges.containsKey(edge)){
                        int newArea = (x - edges.get(edge))*(y2-y1);
                        area = Math.min(area, newArea);
                        edges.put(edge, x);
                    } else {
                        edges.put(edge, x);
                    }
                }

            }
        }

        return area == Integer.MAX_VALUE? 0: area;
    }

    public void addEdgesToEdgeMap(Map<String, List<Integer>> edgeMap, String key){

    }

    public static int rectangleMania(List<Integer[]> coords) {

        Collections.sort(coords, (a,b)->  (a[0] != b[0] )? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        System.out.println(Arrays.deepToString(coords.toArray()));

        Map<String, List<Integer>> edgeMap = new HashMap<>();// "y1-y2" : [x1,x2,x5]
        int i = 0;
        while (i< coords.size()){ // these two while loops do not cause quadratic time complexity but linear because it's always forward
            int x = coords.get(i)[0];
            Deque<Integer> stackOfYAtThisX = new ArrayDeque<>();
            stackOfYAtThisX.add(coords.get(i)[1]);
            while( i< coords.size() -1 && coords.get(i+1)[0] == x){
                i++;
                x = coords.get(i)[0];
                stackOfYAtThisX.add(coords.get(i)[1]);

            }
            while(!stackOfYAtThisX.isEmpty()){
                int y1 = stackOfYAtThisX.pop();
                for (int xCoord: stackOfYAtThisX){
                    String edgeKey = y1 + "-" + xCoord;
                    List<Integer> xCoordinates = edgeMap.get(edgeKey);
                    if (xCoordinates == null) {
                        xCoordinates = new ArrayList<>();
                    }
                    xCoordinates.add(x);
                    edgeMap.put(edgeKey, xCoordinates);
                }
            }
            i++;
        }
        int numOfRectangles = 0;
        for (List<Integer> xcoordinates: edgeMap.values()){
            if (xcoordinates.size() < 2)
                continue;
            int n = xcoordinates.size();
            int numOfRectangleAtThisEdge = nchoosek(n, 2);

            numOfRectangles+=numOfRectangleAtThisEdge;
        }
        return numOfRectangles;
    }

    static int nchoosek(int n, int k){
        String key = n+"-"+k;
        if (k > n) {
            mapNChoosek.put(key, 0);
            return 0;
        }
        if (n == 0 || k == n || k == 0) {
            mapNChoosek.put(key, 1);
            return 1;
        }
        if (mapNChoosek.containsKey(key))
            return mapNChoosek.get(key);
        else {
           int ret = nchoosek(n - 1, k) + nchoosek(n-1, k - 1);
           mapNChoosek.put(key, ret);
           return ret;
        }
    }

}
