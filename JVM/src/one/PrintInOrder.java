package one;
// https://leetcode.com/problems/print-in-order/
/*
The same instance of Foo will be passed to three different threads.
Thread A will call first(), thread B will call second(), and thread C will call third().
Design a mechanism and modify the program to ensure that second() is executed after first(),
and third() is executed after second().

Note:

We do not know how the threads will be scheduled in the operating system, even though the numbers
in the input seem to imply the ordering. The input format you see is mainly to ensure our tests'
comprehensiveness.

Note we have to use NotifyAll here
 */
public class PrintInOrder {
    private int i = 0;
    private Object lock = new Object();
    public PrintInOrder() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        synchronized(lock){
            while(i % 3 != 0){
                lock.wait();
            }
            printFirst.run();
            i++;
            lock.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized(lock){
            while(i % 3 != 1){
                lock.wait();
            }
            printSecond.run();
            i++;
            lock.notifyAll();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        //printSecond.run();
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized(lock){
            while(i % 3 != 2){
                lock.wait();
            }
            printThird.run();
            i++;
            lock.notifyAll();
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        // printThird.run();
    }
}
