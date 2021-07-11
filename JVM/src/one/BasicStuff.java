package one;

public class Creating {
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

    }
}

class CustomThread extends Thread {
    @Override
    public void run() {
        System.out.println("I'm a custom thread!");
    }
}
