package one;

public class BasicStuff {
    public static void main(String[] args) {
        // Create a new Thread and Pass runnable
        Thread t = new Thread(()-> System.out.println("Runnable: " + Thread.currentThread().getId()));
        t.start();
        System.out.println("Main" + Thread.currentThread().getId());
        /* output:
         Runnable: 22
         Main: 1 (order may change with Main before)
         */

        // Create a subclass of Thread. Implement run
        CustomThread ct = new CustomThread();
        ct.start(); // don't call run. No threading

        //Runnable and Callable (callable needs Executor service)
        // can't throw exeception - can
        // returns void - a generic type
        // submit returns Future with null - Future with generic
        // new Thread(new MyRunnable); - needs executor service
        // both functional interface

        // Sleep
        Thread t1 = new Thread(new RunThread());
        t1.start();
        //t1.interrupt();
        try {
            // join is a sync method and internally calls wait
            t.join(500); // wait for 500 ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(new RunThread());
        t2.start();
        //t2.interrupt();

    }
}
class RunThread implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Current Thread id " + Thread.currentThread() + " - " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.interrupted(); // when main thread calls interupt, a flag is set which interupts
                break;
            }
        }
    }
}

class CustomThread extends Thread {
    @Override
    public void run() {
        System.out.println("I'm a custom thread!");
    }
}
/*

public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }
 */