package nav.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// O(V+E): can be used to calculate shortest path for DAG if topological order exits
public class TopologicalOrdering {
    public static void main(String[] args) {
        int[][] prereqs =  {{1,0}};
        TopologicalOrdering obj = new TopologicalOrdering();
        System.out.println(Arrays.toString(obj.findOrder(2, prereqs)));
    }
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Vertex[] vertices = new Vertex[numCourses];
        for (int i = 0; i < numCourses; i++){
            vertices[i] = new Vertex(i);
        }
        for (int i = 0; i < prerequisites.length; i++ ){
            int prereqIndex = prerequisites[i][1];
            int dependentIndex = prerequisites[i][0];
            vertices[dependentIndex].prereqs.add(vertices[prereqIndex]);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
           if ( topologicalSort(vertices, i, result) == true)
               return new int[]{};
        }

         int[] ret = new int[numCourses];
         for(int i=0; i< numCourses; i++){
             ret[i] = result.get(i);
         }
         return ret;
    }

    boolean topologicalSort( Vertex[] vertices, int i, List<Integer> result){
        if (vertices[i].beingVisited == true) return true; //cycle
        if (vertices[i].visited == true) return false;
        if (vertices[i].prereqs.size() == 0) {
            vertices[i].visited = true;
            result.add(i);
            return false;
        }
        vertices[i].beingVisited = true;
        for (Vertex v: vertices[i].prereqs){
            if (topologicalSort(vertices, v.index, result)) return true;
        }
        result.add(i);
        vertices[i].visited = true;
        vertices[i].beingVisited = false;
        return false;
    }

    static class Vertex {
        int index;
        boolean visited;
        boolean beingVisited;
        List<Vertex> prereqs;
        Vertex(int index){
            this.index = index;
            this.prereqs = new ArrayList<>();
        }
    }
}

