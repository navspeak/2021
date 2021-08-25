package nav.graph;
// https://www.youtube.com/watch?v=VkKmmwzfIG4
// Why building of Heap takes O(n) time
public class MinHeap {
    int count;
    int[] array;

    MinHeap(int size){
        array = new int[size];
        count = 0;
    }

    int size() { return count;}

    void add(int value) throws HeapFullException {
        if (count == array.length) throw new HeapFullException();
        array[count++] = value;
        siftup(count - 1);
    }

    int remove() throws HeapEmptyException {
        if (count == 0) throw new HeapEmptyException();
        int ret = array[0];
        array[0] = array[count-1];
        count--;
        siftdown(0);
        return ret;
    }

    //incomplete
    private void siftdown(int index) {
        int leftChildIndex = 2*index + 1;
        int rightChildIndex = 2*index + 2;
        if (leftChildIndex >= count) return;
        if(array[leftChildIndex] < array[rightChildIndex]){
            if (array[index] > array[leftChildIndex]){
                swap(index, leftChildIndex);
                index = leftChildIndex;
            }
        } else {
            if (array[index] > array[rightChildIndex]){
                swap(index, rightChildIndex);
                index = rightChildIndex;
            }
        }
        siftdown(index);
    }

    private void siftup(int index){
        if (index <= 0) return;
        int parentIndex = (index - 1)/2;
        if (array[parentIndex] > array[index]){
            swap(parentIndex, index);
        }
        siftup(parentIndex);
    }

    private void swap(int i, int j){
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }


}

class HeapFullException extends Exception {

}
class HeapEmptyException extends Exception {

}
