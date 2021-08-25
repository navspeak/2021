package javastuff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentModification {

    //static List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
    static List<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
    static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Thread iteratorThread = new Thread(ConcurrentModification::iterate);
        Thread mutateThread = new Thread(ConcurrentModification::mutate);
        iteratorThread.start();
        mutateThread.start();
        iteratorThread.join();
        mutateThread.join();
        System.out.println("Done");
        //mutate();
        list.forEach(System.out::println);

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        map.put("First", 10);
        map.put("Second", 20);
        map.put("Third", 30);
        map.put("Fourth", 40);

        Iterator<String> iterator = map.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            map.put("Fifth", 50);
        }

        map.forEach((k,v)->{
            System.out.println(k + "->" + v);
        });
    }

//    How to avoid ConcurrentModificationException in a multi-threaded environment?
//    To avoid the ConcurrentModificationException in a multi-threaded environment, we can follow the following ways-

//    Instead of iterating over the collection class, we can iterate over the array.
//       In this way, we can work very well with small-sized lists, but this will deplete the performance if the array size is very large.
//    Another way can be locking the list by putting it in the synchronized block.
//       This is not an effective approach as the sole purpose of using multi-threading is relinquished by this.
//    JDK 1.5 or higher provides with ConcurrentHashMap and CopyOnWriteArrayList classes.
//      These classes help us in avoiding concurrent modification exception.

//    How to avoid ConcurrentModificationException in a single-threaded environment?
//      By using iterator's remove() function, you can remove an object from an underlying collection object.

    private static  /*synchronized*/ void iterate()  {
            for (int i : list) {
                System.out.println(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock();
                }
            }
    }

    private static /*synchronized*/  void mutate(){

            Iterator<Integer> itr = list.iterator();
            while (itr.hasNext()) {
                Integer next = itr.next();
                if (next == 5) {
                    //itr.remove(); // CopyOnWrite has does not support remove
                    list.remove(4);
                }
            }
/*
CopyOnWriteArrayList makes a copy on mutation time only
 public boolean add(E e) {
        synchronized (lock) {
            Object[] es = getArray();
            int len = es.length;
            es = Arrays.copyOf(es, len + 1);
            es[len] = e;
            setArray(es);
            return true;
        }
    }
 */
    }
}


