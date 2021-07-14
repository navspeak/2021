package nav.mt;

import java.util.function.IntConsumer;

public class BasicThreadStuff {
}

class Test1 implements Runnable{
    Object monitor = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1000);
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