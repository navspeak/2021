package one;


import java.util.concurrent.atomic.AtomicInteger;

public class ThreeNumbers {

    public static class NumberPrinter implements Runnable {

        private int pos = -1;
        private AtomicInteger sharedInt = null;
        private Object mutex = null;

        public NumberPrinter(int pos, AtomicInteger val, Object mutex) {
            this.pos = pos;
            this.sharedInt = val;
            this.mutex = mutex;
        }

        @Override
        public void run() {
            doPrint();

        }

        private void doPrint() {
            synchronized (mutex) {
                while(sharedInt.get() < 15) {
                    if ((sharedInt.get() % 3 ) != pos) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.printf("Thread at pos = %d prints %d \n", pos + 1, sharedInt.incrementAndGet() );
                        mutex.notifyAll();
                    }
                }
            }

        }

    }



    public static void main(String[] args) throws InterruptedException {
        AtomicInteger sharedval = new AtomicInteger(0);
        Object mutex = new Object();
        NumberPrinter one = new NumberPrinter(0, sharedval, mutex);
        NumberPrinter two = new NumberPrinter(1, sharedval, mutex);
        NumberPrinter three = new NumberPrinter(2, sharedval, mutex);
        System.out.println(0%3);
        System.out.println(1%3);
        System.out.println(2%3);

        System.out.println(3%3);
        System.out.println(4%3);
        System.out.println(5%3);

        Thread t1 = new Thread(one);
        Thread t2 = new Thread(two);
        Thread t3 = new Thread(three);

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();;
        t3.join();;
    }

}