package java;

import lombok.SneakyThrows;

import java.lang.annotation.Inherited;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {

    // use: kill -3 pid or jstack pid


    /*
    1. If threads acquire locks in order that creats circular dependency
    2. Is it easy to detect by looking at code:
       - No. although in this example it is easy
       - Thread 1 tries to acquires lock A and then lock B. Relaese lockB first
       - Thread 2 tries to acquires lock B and then lock A. Release lockA first
    3. Real use case:
        - More than 1 threads with more than two locks
        -
     */
    private Lock lockA = new ReentrantLock();
    private Lock lockB = new ReentrantLock();

    private void simulateSimpleDeadLock(){
        new Thread(this::processThis).start();
        new Thread(this::processThat).start();
    }

    @SneakyThrows
    //https://projectlombok.org/features/SneakyThrows
    private void realLockScenarios_MultipleLockSituations(){
        System.out.println("One is ReentrantLock");
        Lock l = new ReentrantLock();
        l.lock();

        System.out.println("BlockingQueue.take");
        BlockingQueue<Integer> q = new ArrayBlockingQueue(10);
        q.take(); // throws interupptedExecption

        System.out.println("Semaphores acquire");
        Semaphore sem = new Semaphore(1);
        sem.acquire();// throws interupptedExecption

        synchronized (this){
            System.out.println("Lock on this");
           //public synchronized void process(){};
        }
        synchronized (DeadLock.class){
            System.out.println("Lock on class");
            //public static synchronized void process(){};
        }
    }
    private void realLockScenarios_MultipleThreads(){
        // CPU scheduling makes it difficult to know when an instruction is run
        Thread t1 = new Thread();
        t1.start();

        ExecutorService pool = Executors.newFixedThreadPool(10);
        pool.submit(this::processThat); // tasks may acquire locks

        ScheduledExecutorService schedulers = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        schedulers.schedule(()-> System.out.println("Some task"), 10, TimeUnit.SECONDS);

//        @RequestMapping("/users/32")
//        public void userDetails(){
//            System.out.println(Thread Pools used by frameworks);
//        }
    }
    private static void detectDeadLock(){
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadBean.findDeadlockedThreads();
        boolean deadLock = threadIds !=null && threadIds.length >0;
        System.out.println("Deadlocks Found: " + deadLock);
    }
    // avoid Deadlock => tryLock with timeOut, acquire locks in order
    // GlobalOdering can be tricky
    private void Transfer (Account from, Account to, int amount){
        Account greater = from.compareTo(to) > 0? from:to;
        Account smaller = from.compareTo(to) < 0? from:to;
        synchronized (greater) {
            synchronized (smaller){
                from.val = from.val - amount;
                to.val = to.val + amount;
            }
        }
        //caller code - if we had not used greater and smaller, the below would have caused deadlock
//        new Thread(this::transfer(john, marie, 100)).start()
//        new Thread(this::transfer(marie, john, 300)).start()

    }
    public void processThis(){ // called by thread 1
        lockA.lock();
        //process Resource A
        lockB.lock();
        //process Resource B and also A

        lockA.unlock();
        lockB.unlock();
    }

    public void processThat(){ // called by thread 2
        lockB.lock();
        //process Resource A
        lockA.lock();
        //process Resource B and also A

        lockA.unlock();
        lockB.unlock();
    }

    //http://tutorials.jenkov.com/java-util-concurrent/blockingqueue.html
}
class Account implements Comparable<Account>{
    int val;

    @Override
    public int compareTo(Account o) {
        return Integer.compare(this.val, o.val);
    }
}