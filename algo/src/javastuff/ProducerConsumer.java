package javastuff;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        MyBlockingQueue<Integer> queue = new MyBlockingQueue<>(10);
        final Runnable producer = () -> {
            while(true) {
                try {
                    int e = new Random().nextInt(100);
                    System.out.println(e +  " produced by " + Thread.currentThread().getName());
                    queue.put(e);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        final Runnable consumer = () -> {
            while(true) {
                try {
                    Integer i = queue.take();
                    System.out.println(i +  " consumed by " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(producer).start();
        new Thread(producer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();
        Thread.sleep(1000);
    }
}

class MyBlockingQueue<E> {
    private Queue<E> queue;
    private int capacity ;
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();
    MyBlockingQueue(int capacity) {
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    public void put(E e) throws InterruptedException {
        lock.lock();

        try {
            while (queue.size() == capacity) {
                notFull.await();
            }
            queue.add(e);
            notEmpty.signalAll();
        } catch  (InterruptedException ex) {
            throw ex;
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == 0) {
                notEmpty.await();
            }
            E ret = queue.poll();
            notFull.signalAll();
            return ret;
        } catch  (InterruptedException ex) {
           throw ex;
        } finally {
            lock.unlock();
        }

    }
}