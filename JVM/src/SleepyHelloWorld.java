// #threadDumps
public class SleepyHelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, world! \nZzZZZ");
        sleep();
    }

    private static void sleep() {
        try{
            Thread.sleep(600000);//10 minutes
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/*
jstack $PID
- JVM threads - Some
- Application threads - Many
- Heap report - One

"main" #1 prio=5 os_prio=0 cpu=343.75ms elapsed=548.00s tid=0x000001c1da400800 nid=0x9cb8 waiting on condition  [0x00000003084fe000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(java.base@11.0.1/Native Method)
	at SleepyHelloWorld.sleep(SleepyHelloWorld.java:9)
	at SleepyHelloWorld.main(SleepyHelloWorld.java:4)

1. Name = main
2. NumberID = #1
3. JVM Priority = prio=5
4. OS priority = os_prio=0 (cpu=343.75ms elapsed=548.00s)
5. Thread Address =  tid=0x000001c1da400800
6. Native ID = nid=0x9cb8
7. Condition = waiting on condition  [0x00000003084fe000]
8. Thread State = TIMED_WAITING (sleeping)
9. Thread Call Stack

Thread States:
====
Runnable - actively invoking code (watch out for Native methods - running precompiled native code. )
Waiting - will wait indefinitely unless some other thread wakes it up.
        - Object.wait() and LockSupport.park => transitions thread to this state)
Timed_Waiting - Similar to WAITING.
        - Thread.sleep() and LockSupport.parkNanos => transitions thread to this state)
Blocked - Waiting for a monitor lock to become available
New
Terminated

* One common real life ex where u can see the state (WAITING) is ThreadPoolExecutor class which has a work queue that
stores tasks that need to be excecuted. The executor also has worker threads that poll the queue for tasks, and these threads
need to wait when there aren't any tasks in the queue
 */