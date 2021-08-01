package javastuff;

public class BasicThreadStuff {
    // Threads Lifecycle: https://www.baeldung.com/java-thread-lifecycle
    //Object.wait() to suspend a thread
    //Object.notify() to wake a thread up
    // Thread.join() -> WAITING. Timed version
    // creates happen-before relationship
    // When we invoke join on a thread the calling thread goes into WAITING state until referenced thread terminates
    // join() method may also return if the reference thread was interrupted
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

        }

        synchronized (monitor){ //obj lock
           monitor.wait();// on Object class, non static method
        }
    }

    @Override
    public void run() {

    }
}