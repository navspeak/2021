package gc;

import java.util.EnumMap;
//https://www.youtube.com/watch?v=k4vkd0ahWjQ
public class GCBasic {
    String PermGenTuning = "java -XX:MaxPermSize=256m com.mycompany.MyApplication";
    String MetaSpace = "Located in native memory. Does not interfere w/regular heap obj" +
            "default size = amount of native memory available to the Java Process";
    String MetaSpaceTuning = "java -XX:MaxMetaspaceSize=256m com.nav.demo.GCDemo";
    // https://plumbr.io/handbook/garbage-collection-in-java
    String jstat = "jstat -gc -t 4235 1s"; // jvisualvm and jconsole to examine heap
    // jvisual VM  both heap and Thread dump
    String jstack = "jstack -l PID => gives thread Dump. Can help in locating deadlock etc and also GC for threads";
    String printGCDetails = "java -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC com.nav.demo.GCDemo";
    // Till jav8 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:<filepath>
    // Java 9 onwards : -Xlog:gc*:file=<file-path>
    enum TRADEOFFS {
        //http://tutorials.jenkov.com/java/enums.html
        ////https://www.youtube.com/watch?v=2AZ0KKeXJSo&t=260s
        // Read EnumSet, EnumMap
        MEMORY,//Or footprint (memory & cpu consumption) of the application
        THRUPUT,
        LATENCY
    }
//https://plumbr.io/handbook/garbage-collection-algorithms
    void tradeOffs( TRADEOFFS consideration){
        //EnumMap<TRADEOFFS, String> tradeoff = new EnumMap<>(TRADEOFFS.class);
        switch(consideration){
            case MEMORY:
                // Memory assigned(heap) to the program. Don't confuse w/ footprint
                // i.e. memory required by GC algo to run
                // aim for low value or predictable vale
                break;
            case LATENCY:
                // Time for which code pauses for GC to run. Generally millisec
                // can go upto secs
                break;
            case THRUPUT:
                // Avg time spent running code vs running GC
                // eg 99% means 1 % time GC ran
                break;
        }
   }
   void generationalHypotheis(){
       // Generational Hypothesis
       //---------------------------
       // Lot of GC happens in Young Generation as most objects die Young (Minor Collection)
       // If old gen goes beyound a threshold - Major GC
       // Full GC = Minor + Major + Metaspace
   }
   void whatAreGCRoots(){
        String LOCAL_VAR_AND_INPUT_PARAMS = "OF THE CURRENTLY EXECUTING METHODS";
        String ACTIVE_THREADS;
        String STATIC_FIELDS = "OF THE LOADED CLASSES";
        String JNI_REFERENCE;
        String STOP_THE_WORLD_PAUSES = "Marking: all application threads must be stopped";
        String SAFEPOINTS = "Situation when STW occurs for JVM to mark. Can occur" +
                "1. many reasons 2. but GC is one of the main reasons";
        String DURATION_OF_PAUSE = "depends NOT on heap size but # of ALIVE OBJECTS" +
                "So increasing heap size does not directly affect the duration during marking";
   }
   void markAndCopyInYoungGen(){ //Eden, S1, S2
        //S1. allocate in Eden -> Once Full trigger Minor GC
       // -> Mark all live objs in Eden-> copy it to S1 -> reclaim whole Eden space as empty

       //S2. allocate in Eden -> Once Full trigger Minor GC
       // -> Mark all live objs in Eden -> copy it to S2 -> copy live objs of S1 to S2
       //-> reclaim whole Eden space as empty
   }
   void justMarkAndSweep_Not_Effective(){ // Only Sweep
        // no compaction. Needs recording free-list for every region
       // free-list adds overhead.
       //there may exist plenty of free regions but if no single region is large enough to accommodate the allocation,
       // the allocation is still going to fail (with an OutOfMemoryError in Java).
   }

   void MarkSweepCompact_Using_BUMP_THE_POINTER(){ //Generally runs on Old Generation
        // Keeps allocating -> Once threshold is breached, Major/Full GC triggers
       // -> Marks all live objects (STW) -> sweeps/Reclaims dead -> Move all live objects
       // together (compaction) (bump the pointer)
       // Downside: increased GC pause for copying objects to new place and upgrade all references
       // Advantage: No fragmentation
   }
/* Types of GC */
   void serialGC(){ //java -XX:+UseSerialGC
       //mark-copy for the Young Generation
       //mark-sweep-compact for the Old Generation (bump the pointer)
       //Both of these collectors are single-threaded collectors, incapable of parallelizing the task at hand.
       //Both collectors also trigger stop-the-world pauses, stopping all application threads
       //Good for programs w/small memory, running on shared CPU
   }

    void Parallel_old(){ //XX:ParallelGCThreads=NNN, (Parallel - uses multple thread in Young and just 1 in Tenured */
        // java -XX:+UseParallelGC -XX:+UseParallelOldGC com.nav.Test
        //mark-copy for the Young Generation
        //mark-sweep-compact for the Old Generation (bump the pointer)
        //Both of these collectors run concurrently, but are STW
        //Both collectors also trigger stop-the-world pauses, stopping all application threads
        //Good for muti-processor system. Good for batch application
        // Higher thruput with Multi core. But not good latency
    }

    void concurrentMarkAndSweep(){  // XX:+UseConcMarkSweepGC
        // Concurrently Marks live object. Lower latency
        //This collector was designed to avoid long pauses while collecting in the Old Generation.
        // It achieves this by two means.
        // Firstly, it does not compact the Old Generation but uses free-lists to manage reclaimed space.
        // Secondly, it does most of the job in the mark-and-sweep phases concurrently with the application.
        // This means that garbage collection is not explicitly stopping the application threads to perform these phases.
        // It should be noted, however, that it still competes for CPU time with the application threads.
        // By default, the number of threads used by this GC algorithm equals
        // to ¼ of the number of physical cores of your machine.
        // More footprint (more Data structure). Less throughput

    }

    void GiGC(){        // -XX:+UseG1GC
        //Key goal- make the duration and distribution of STW predictable and configurable.
        //G1GC is a soft real-time garbage collector, meaning that you can set specific performance goals to it.
        //You can request the stop-the-world pauses to be no longer than x milliseconds within any given y-millisecond
        // long time range, e.g. no more than 5 milliseconds in any given second.
        // Garbage-First GC will do its best to meet this goal with high probability
        // (but not with certainty, that would be hard real-time).

        // Region Based. More footprint than CMS. Lower worst case latency, but higher average latency
        //maxTargetPauseTime
    }
    void Shenondoah(){
        // Lower average latency
        // Higher footprint
        // concurrennt evacuation - Load Reference Barrier (cost of Thruput)
        // ms STW - upto 100 ms
    }
    //https://www.overops.com/blog/garbage-collectors-serial-vs-parallel-vs-cms-vs-the-g1-and-whats-new-in-java-8/
    // String interning
    // https://mikolajkania.com/2019/01/02/all-you-need-to-know-about-string-performance-in-java/
    //-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps
    //https://www.youtube.com/watch?v=X8w3uqN-X98
}
