package java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorCompletionServiceExample {
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
                    TimeUnit.SECONDS.sleep(4);
                    return "Worked for 4 secs";
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
//
//        Future<String> submit1 = executorService.submit(listOfCallables.get(0));
//        Future<String> submit2 = executorService.submit(listOfCallables.get(1));
//        Future<String> submit3 = executorService.submit(listOfCallables.get(2));
//
//        executorService.shutdown();// needed to exit
//
//        System.out.println(submit3.get());
//        System.out.println(submit2.get());// would have finished but will have to wait for 3
//        System.out.println(submit1.get());// would have finished but will have to wait for 3 and 2
//
//        System.out.println("----"); // will not exit till we do a shutdown
//
//        executorService.awaitTermination(45, TimeUnit.SECONDS); // 3rd one won't exec
//
        // we could do this too:
        CompletionService completionService = new ExecutorCompletionService(executorService);
        List<Future<String>> futures = new ArrayList<>();
        futures.add(completionService.submit(listOfCallables.get(0)));
        futures.add(completionService.submit(listOfCallables.get(1)));
        futures.add(completionService.submit(listOfCallables.get(2)));
        executorService.shutdown();
        try {
            while (!executorService.isTerminated()) {
                final Future<String> future = completionService.take();
                System.out.println(future.get());
            }
        } catch (ExecutionException | InterruptedException ex) { }

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