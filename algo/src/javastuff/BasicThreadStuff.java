package javastuff;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class BasicThreadStuff {
    // Threads Lifecycle: https://www.baeldung.com/java-thread-lifecycle
    // interuppted: https://dzone.com/articles/why-do-we-need-threadcurrentthreadinterrupt-in-int
    // Volatile only solves visibilty issues not synchronization issues
    //Object.wait() to suspend a thread
    //Object.notify() to wake a thread up
    // Thread.join() -> WAITING. Timed version
    // creates happen-before relationship
    // When we invoke join on a thread the calling thread goes into WAITING state until referenced thread terminates
    // join() method may also return if the reference thread was interrupted

    public static void main(String[] args) throws InterruptedException {
        AtomicBoolean running = new AtomicBoolean(true);
        AtomicBoolean stopped = new AtomicBoolean(false);
        //https://www.baeldung.com/java-interrupted-exception
        Callable<String> c = ()-> {
            while(running.get() == true) {
                try {
                      Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " is running");
                } catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted, Failed to complete operation");
                }
                stopped.set(true);
            }
            return "test";
        };
        Runnable r = ()-> {
            while(/*running.get() == true*/ !Thread.currentThread().isInterrupted()) {
                try {
//                    if (Thread.currentThread().isInterrupted()){
//                       Thread.currentThread().interrupt();
//                    }
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " is running");
                } catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted, Failed to complete operation");
                    //running.set(false);
                }

            }
        };


        FutureTask<String> ft = new FutureTask<>(c);
        //Thread t = new Thread(ft);
        Thread t = new Thread(r);
        //t.interrupt();
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + "Sleeping ...");
        t.start();
        t.interrupt();
        Thread.sleep(1000);
        running.set(false);
//        try {
//            //t.join();
//            System.out.println(ft.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }


    }
}

class Test1 implements Runnable{
    Object monitor = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1000);
        Thread t1 = new Thread(()-> System.out.println("000"));
        t1.join();
        Thread.currentThread().interrupt();
        Thread.interrupted();
        Thread.currentThread().isInterrupted();
        //IntConsumer printNumber = new In
    }
    void test () throws InterruptedException {
        synchronized (Test1.class){ // class level lock, synch static members
            Test1.class.wait();
        }

        synchronized (monitor){ //obj lock
           monitor.wait();// on Object class, non static method
        }
    }

    @Override
    public void run() {

    }
}