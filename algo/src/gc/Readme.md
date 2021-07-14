##Types of GC
1. Do Nothing
2. Reference Counting. e.g. COM. Suseptible to circular reference (one references other)
3. Mark and Sweep (fragmentation may happen)
4. Copying (follow Mark and Sweep copying live objects to another contiguously)
5. Generational (Long lived objects promoted to old generation )
6. Incremental GC 

##Things to Consider
1. Stop the world events
2. Memory Fragmentation
3. Throughput
4. Different GCs - generational, copying, Mark & sweep
5. Multi-core

## Basics
- Eden space - initial obj allocated here
- Young Generation - Two survivor spaces (S0, S1)
- Old/Tenured Generation - Long lived objects
- Permanent Generation (Used by Java, class info, static content, String pool before Java 7 etc) 
- [why Java PermGen is Gone](https://www.infoq.com/articles/Java-PERMGEN-Removed/). Read Metaspace Java 8 
- Minor GC: Happens when is Young generation full. Objs allocated in Eden. GC runs, objects are copied to a newer survivor space. Objs from older survivor space copied to newer survivor space. Survivor spaces are swapped: Old becomes new and vice versa.
- Major GC: Slower, when old gen is full. Larger section of heap. In Java, there is actually Full GC that collects both Young and old. In Java during Full GC promotion to old gen also happens.
- You can use -XX:+AlwaysTunure flag to tell JVM to create objects in tenured space always
- Question: When Minor GC is triggered, what happens to objects in Young generation referenced by old gen. Does the GC look at old gen?
    * Well, no as that would defeat the purpose.
    * when old gen allocates object in young gen it goes thru a write barrier in JVM and a __CARD TABLE__ is updated with the memory address of young gen obj
    * Minor GC scan the card table to find live objects
- Java Stack and heap - https://www.youtube.com/watch?v=_y7k_0edvuY
- HashMap: https://www.youtube.com/watch?v=c3RVW3KGIIE    
- https://stackoverflow.com/questions/10655239/rehashing-process-in-hashmap-or-hashtable
    
## Different GC types in Java
* -XX:+UseSerialGC - Serial GC 
* -XX:+UseParallelGC - Parallel for Young, serial for old
* -XX:+UseParallelOldGC - Parallel for Young, and old
* -XX:+UseConcMarkSweepGC & -XX:-UseParNewGC - Concurrent mark and sweep with serial young 
* -XX:+UseConcMarkSweepGC & -XX:+UseParNewGC - Concurrent mark and sweep with Parallel for young 
* -XX:+UseG1GC
---
* Serial Collector: Single Threaded. Stop the world collector. Mark and sweep
* Parallel Collector: Multiple thread for minor. Single for major. Fewer stop the world. Parallel Old run multiple threads for old as well
* Concurrent Mark and sweep - Deprecated from Java 9. Only collect old space. Causes heap fragmentation. Inital Mark : stop the world. Concurrent Mark
---
GC type not affects how quickly or slowly GC happens, but also how memory is allocated.
## GC Tools
- MXBeans
- Jstat -gutil pid | jstat -gccause pid | jstat -gccapacity pid
- visual VM, visual GC

## Java Reference Classes
* Strong > soft > weak > phantom
* Strong reference - Not Gc'd till strong ref is there
* java.lang.ref package:
    * Soft, weak and Phantom - can be GC'd 
```java
Person p = new Person();
WeakReference<Person> wr = new WeakRefernce<Person>(p); //returns strong ref to Person
p = null; // has no strong ref, but has wr. If no memory pressure GC may not collect Person which is referenced by wr
```
* WeakReference - WeakHashMap, associate metadata
* SoftRef - caching
* Phantom Ref - interaction with GC

### WeakHashMap
- Key is a Weak Ref object, Value generally is some metadata. When key is null'd, val us gone

### GC logging 
* https://www.baeldung.com/java-verbose-gc
