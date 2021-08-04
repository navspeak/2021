package one;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class CompletionService {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        /*
         return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
         */
        //Executors.newFixedThreadPool();
        /*
         public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());

         */

        List<Callable<String>> tasks = Arrays.asList(new Task<>("task1"),
                new Task<>("task2"),
                new Task<>("task3")
        );

//        Future<String> submitTask = executorService.submit(new Task<>("SubmitTask"));
//        try {
//            System.out.println(submitTask.get())
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // without executorCompletionSvc tasks can be processed in order which they are submitted
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<String>(executorService);
        Future<String> f1 = completionService.submit(tasks.get(0));
        Future<String> f2 = completionService.submit(tasks.get(1));
        Future<String> f3 = completionService.submit(tasks.get(2));


        /*for (int i = 0; i < 5;i++) {
            System.out.println(completionService.take().get());
        }*/
		try {
            while (!executorService.isTerminated()) {
                final Future<String> future = completionService.take();
                System.out.println(future.get());
            }
        } catch (ExecutionException | InterruptedException ex) { }


    }

    public static class Task<T> implements Callable<String> {
        String taskName;

        public Task(String name) {
            taskName = name;
        }

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " is running " + taskName);
            TimeUnit.SECONDS.sleep(30);
            System.out.println(Thread.currentThread().getName() + " has run " + taskName);
            return taskName+" Finished";
        }

    }
}
