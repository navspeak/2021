### [What is a Monitor?](https://www.baeldung.com/cs/monitor)
- Synch mechanism that allows threads to have:
   * mutual exclusion – only one thread can execute the method at a certain point in time, using locks
   * cooperation – the ability to make threads wait for certain conditions to be met, using wait-set
- Java’s implementation of a monitor mechanism relies on two concepts : entry and wait sets
    * monitor = building (every object has an intrinsic lock aka monitor)
    * synchronized resource = exclusive room that allow 1 person
    * entry set = hallway
    * wait set = waiting room
    * threads = people wanting to get into exclusive room

### [Wait and Notify](https://www.baeldung.com/java-wait-notify)
- Object.wait() to suspend a thread
- Object.notify() to wake a thread up
- Why enclose wait() in while loop (spin lock)?
- [Spurious Wakeup](http://tutorials.jenkov.com/java-concurrency/thread-signaling.html#spurious-wakeups) - wake from wait without getting notified.
- Missed signals
- Never call wait() on constant string or global objects
- wait vs sleep: 
   - Wait non static method belongs to object. Thread static method belongs to Thread class
   - wait must be called on sync context. Sleep() there is no such need
   - wait releases lock during synch. Sleep doesn't
   - Similarity: Both are native method. Both put thread in non-running state (Wait -> Waiting, Sleep -> Timed waiting)
- join: also makes thread go into WAITING state:
```java
class Test{
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
}
```
### [Interrupted Exception](https://www.baeldung.com/java-interrupted-exception)
- The interrupt() method is a (non static) method of the Thread class - calls interrupt0 which is native
- interrupted() is a static method in Thread class. It checks interrupt status and clears the flag
- isInterrupted() is not static, and so can't clear the flag as it must be operated on Thread object
- JVM can also interrupt on a sleeping Thread, or on conditionOnj.wait()
- Stopping a thread (From [here](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-concurrency-basic)):
- How to timeout Java thread  say after 10 mins:
    * [use interrupt or volatile/AtomicBoolean](https://www.youtube.com/watch?v=_RSAS-gIjGo)
    * Can only interrupt a Java procession, not an IO Operation
```java
class Demo implements Runnable {
    public AtomicBoolean keepRunning = new AtomicBoolean(true);
    public static void main() {
        ExecutorService threadpool = Executors.newCachedThreadPool();
        Future<?> future = threadpool.submit(()-> {
            while(!Thread.currentThread().isInterrupted()){ }
        });
        Thread.sleep(10*60*1000);
        threadpool.shutdownNow(); //internally calls thread.interrupt for all running threads
        // future.cancel(true); // -do-

    }
    public void run(){
        while(keepRunning.get() == true ){
            
            jdbc.storedProcCall("Wait too long, and won't reach while loop above");
            // or httpClient.request();
           // rely on libraries timeout
        }
    }
    public void stop(){
        keepRunning.set(false);
    }
}
```
```
public class MyThread implements Runnable {
    private Thread worker;
    private int interval = 100;
    private AtomicBoolean running = new AtomicBoolean(false); // could have had volatile also
    private AtomicBoolean stopped = new AtomicBoolean(true);

        public void start() {
            worker = new Thread(this);
            worker.start();
        }
    
        public void stop() {
            running.set(false);
        }
    
        public void interrupt() {
            running.set(false);
            worker.interrupt();
        }
    
        boolean isRunning() {
            return running.get();
        }
    
        boolean isStopped() {
            return stopped.get();
        }
    
        public void run() {
            running.set(true);
            stopped.set(false);
            while (running.get()) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                	Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted, Failed to complete operation");
    	    }
                // do something
            }
            stopped.set(true);
        }
}
```
### [Reentrant Lock](https://www.youtube.com/watch?v=ahBC69_iyk4) 
- Why name is Re-entrant: If a thread has a lock, it can re-aquire without waiting (the hold count will increase though)
- lock.getHoldCount();
- call unlock in finally
- fairness | unfair locks are faster but can cause starvation
- tryLock() doesn't honour fairness i.e new ReentrantLock(true). You can use tryLock(0, TimeUnit.SECONDS);
- isHeldByCurrentThread
- lockInterruptibly

### Condition Class
 - Condition conditionMet = lock.newCondition()
 - conditionMet.await() => relenquish the monitor, waiting for this condition to be met
 - conditionMet.signal() => condition is met, so signal all awaiting on condition to reacquire the lock
 - [Producer Consumer](../../images/LockConditions.PNG)
 
 ### [ReadWriteLock](https://www.youtube.com/watch?v=7VqWkc9o7RM)
 - Lock lock = new ReentrantLock();
 - ReentrantLock.ReadLock readLock = lock.readLock();
 - ReentrantLock.WriteLock writeLock = lock.writeLock();
 - If 1 thread acquired writeLock, only that thread can have the WriteLock, all threads trying for writeLock or ReadLock will wait and be blocked.
 - If 1 thread acquired readLock, anyother thread trying for ReadLock can also acquire Readlock, but any thread trying to acquire writeLock will be blocked.
 - Conditions allowed on writeLocks
 - usecase: frequent reads and infequent writes
 
 ### JVM Compilation - https://www.youtube.com/watch?v=sJVenujWGjs
 
 