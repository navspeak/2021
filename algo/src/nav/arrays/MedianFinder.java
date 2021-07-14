package nav.arrays;

import java.util.Comparator;
import java.util.PriorityQueue;

class MedianFinder {

    PriorityQueue<Integer> lowerNums, higherNums;

    /** initialize your data structure here. */
    public MedianFinder() {
        lowerNums = new PriorityQueue<>(Comparator.reverseOrder()); // 3 1 2 => max heap
        higherNums = new PriorityQueue<>(Comparator.naturalOrder()); // 7 9 12 => min heap

    }

    public void addNum(int num) {
        if (lowerNums.isEmpty() && lowerNums.isEmpty()) {
            lowerNums.add(num);
            return;
        }

        if (num > lowerNums.peek()){
            higherNums.add(num);
        } else {
            lowerNums.add(num);
        }

        int lowerNumSize = lowerNums.size();
        int higerNumSize = higherNums.size();
        if (Math.abs(higerNumSize - lowerNumSize ) > 1){
            if (higerNumSize > lowerNumSize ){
                lowerNums.add(higherNums.remove());
            } else {
                higherNums.add(lowerNums.remove());
            }
        }

    }

    public double findMedian() {
        int lowerNumSize = lowerNums.size();
        int higerNumSize = higherNums.size();
        if (higerNumSize == lowerNumSize ) {
            return (lowerNums.peek() + higherNums.peek())/2.0;
        } else if (higerNumSize > lowerNumSize){
            return higherNums.peek();
        } else{
            return lowerNums.peek();
        }
    }
}

