package eight;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        //A Java Callable is different from a Runnable in that the Runnable interface's run() method does not return a
        // value, and it cannot throw checked exceptions (only RuntimeExceptions).
        List<Callable<String>> listOfCallables = Arrays.asList(
                ()->{
                    TimeUnit.SECONDS.sleep(30);
                    return "Worked for 30 secs";
                },
                ()->{
                    TimeUnit.SECONDS.sleep(40);
                    return "Worked for 40 secs";
                },
                ()->{
                    TimeUnit.SECONDS.sleep(50);
                    return "Worked for 50 secs";
                },
                ()->{
                    TimeUnit.SECONDS.sleep(50);
                    if (1==1) throw new Exception("My Exception");
                    return "Worked for 50 secs";
                }

        );

        //List<Future<String>> futures = executorService.invokeAll(listOfCallables);

        Future<String> submit1 = executorService.submit(listOfCallables.get(0));
        Future<String> submit2 = executorService.submit(listOfCallables.get(1));
        Future<String> submit3 = executorService.submit(listOfCallables.get(2));

        executorService.shutdown();// needed to exit

        System.out.println(submit3.get());
        System.out.println(submit2.get());// would have finished but will have to wait for 3
        System.out.println(submit1.get());// would have finished but will have to wait for 3 and 2

        System.out.println("----"); // will not exit till we do a shutdown

        executorService.awaitTermination(45, TimeUnit.SECONDS); // 3rd one won't exec
// OUTPUT:
        /*
Worked for 50 secs
Worked for 40 secs
Worked for 30 secs
----
         */
        // we could do this too:
        //CompletionService completionService = new ExecutorCompletionService(executorService);
    }


}
/*
ExecutorService executorService =
  new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
  new LinkedBlockingQueue<Runnable>());

https://www.baeldung.com/java-executor-service-tutorial
executorService.shutdown();
try {
    if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
        executorService.shutdownNow();
    }
} catch (InterruptedException e) {
    executorService.shutdownNow();
}
 */