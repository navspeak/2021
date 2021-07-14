package one;

import java.util.LinkedList;
import java.util.Queue;

class BoundedBlockingQueue {
    Queue<Integer> queque;
    int capacity;
    public Object lock = new Object();

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        queque = new LinkedList<>();
    }

    public void enqueue(int element) throws InterruptedException {
        //if(queque.size() == capacity){
        synchronized (lock) {
            while(queque.size() == capacity){
                lock.wait();
            }
            queque.add(element);
            lock.notify();
        }
        // } else {
        //     queue.add(element);
        // }
    }

    public int dequeue() throws InterruptedException {
        int ret = 0;
        synchronized (lock) {
            while(queque.size() == 0){
                lock.wait();
            }
            ret = queque.remove();
            lock.notify();
        }
        return ret;
    }

    public int size() {
        return queque.size();
    }
}