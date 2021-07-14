package nav.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class SortKSortedArray {
    public static void main(String[] args) {
        int[] array = new int[] {6,5,3,2,8,10,9};
        System.out.println(Arrays.toString(sortKSortedArray(array, 3)));
    }
    public static int[] sortKSortedArray(int[] array, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();//Comparator.naturalOrder()
        int j = 0;
        for(int i=0; i<array.length;i++){
            if (minHeap.isEmpty()) {
                minHeap.add(array[i]);
                continue;
            }

            if (minHeap.size() == k){
                int smallestInHeap = minHeap.peek();
                if (array[i] < smallestInHeap)
                    array[j++] = array[i];
                else {
                    array[j++] = minHeap.remove();
                    minHeap.add(array[i]);
                }
            } else {
                minHeap.add(array[i]);
            }
        }


        while(j < array.length ){
            array[j++] = minHeap.remove();
        }
        return array;
    }

    /*
    int[][] times = new int[][] {{0, 2}, {1, 4}, {4, 6}, {0, 4}, {7, 8}, {9, 11}, {3, 10}}; => min 3 laptops
    These are time slots during which a student needs a laptop. Laptops can be reused if they are free
    for example the laptop used from 0-2 can be reused for 4-6, but not for 1-4 or 0-4 because they overlap
     */
    public int laptopRentals(ArrayList<ArrayList<Integer>> times) {
        if (times.size() == 0) return 0;
        Collections.sort(times, (a1, a2)-> {
            if (a1.get(0) != a2.get(0))
                return Integer.compare(a1.get(0), a2.get(0));
            else
                return Integer.compare(a1.get(1), a2.get(1));
        });
        PriorityQueue<Integer> minHeapForEndTime = new PriorityQueue<>();
        int l=times.get(0).get(0), r=times.get(0).get(1), c=0;
        for (int i=0; i< times.size(); i++){
            if (minHeapForEndTime.isEmpty()){
                minHeapForEndTime.add(times.get(i).get(1));
                c++;
                continue;
            }
            if (times.get(i).get(0) < minHeapForEndTime.peek()){
                minHeapForEndTime.add(times.get(i).get(1));
                c++;
            } else {
                minHeapForEndTime.remove();
                minHeapForEndTime.add(times.get(i).get(1));
            }
        }
        return c;
    }
}
