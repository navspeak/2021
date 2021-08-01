package javastuff;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyDemo {
}

class Task implements Runnable {
    Lock l = new ReentrantLock();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "Trying to acquire lock");
        l.lock();// interruptedExecption not thrown
        // in case of simple lock, if thread was interrupted nothing would have happened.
        try {
            l.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
           boolean x = l.tryLock(0, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}