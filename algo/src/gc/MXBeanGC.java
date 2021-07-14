package gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class MXBeanGC {
    public static void main(String[] args) {
        List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean bean: list){
            System.out.println("Name "+ bean.getName());
            System.out.println("# of collections "+ bean.getCollectionCount());
            System.out.println("Collection Time (ms) "+ bean.getCollectionTime());

            for(String name: bean.getMemoryPoolNames() ){
                System.out.println("\t"+name);
            }

            System.out.println();
        }
    }
}
/*
-XX:+UseSerialGC - Serial GC
-XX:+UseParallelGC - Parallel for Young, serial for old
-XX:+UseParallelOldGC - Parallel for Young, and old
-XX:+UseConcMarkSweepGC & -XX:-UseParNewGC - Concurrent mark and sweep with serial young
-XX:+UseConcMarkSweepGC & -XX:+UseParNewGC - Concurrent mark and sweep with Parallel for young
-XX:+UseG1GC
 */
/*
Name G1 Young Generation
# of collections 0
Collection Time (ms) 0
	G1 Eden Space
	G1 Survivor Space
	G1 Old Gen

Name G1 Old Generation
# of collections 0
Collection Time (ms) 0
	G1 Eden Space
	G1 Survivor Space
	G1 Old Gen
 */