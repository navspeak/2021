package javastuff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.Thread.sleep;

//Retrieve prices from N sources waiting for max 3 seconds
public class ScatterNGather {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadpool = Executors.newFixedThreadPool(4);
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
        int[] prodId = {8788, 8909, 9900};
        CountDownLatch latch = new CountDownLatch(3);
        threadpool.submit(new GetPriceTask("http://xyz.com", prodId[0], prices, latch));
        threadpool.submit(new GetPriceTask("http://abc.com", prodId[1], prices, latch));
        threadpool.submit(new GetPriceTask("http://pqr.com", prodId[2], prices, latch));
        latch.await(3, TimeUnit.SECONDS);
        System.out.println(Arrays.toString(prices.toArray()));

    }

    public static void option2(String[] args) throws InterruptedException, TimeoutException, ExecutionException {
        ExecutorService threadpool = Executors.newFixedThreadPool(4);
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
        int[] prodId = {8788, 8909, 9900};
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(new GetPriceTask("http://xyz.com", prodId[0], prices, null));
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(new GetPriceTask("http://abc.com", prodId[1], prices, null));
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(new GetPriceTask("http://pqr.com", prodId[2], prices, null));

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);
        allTasks.get(3, TimeUnit.SECONDS);
        System.out.println(Arrays.toString(prices.toArray()));

    }
    public static void completableFutureDemo(){
        Supplier<Void> getOrder = () -> {
            System.out.println("GET ORDER");
            return null;
         };
        Consumer<Order> enrichOrder = (o) -> {
            System.out.println("ENRICH ORDER" + o);
         };
        Consumer<Order> performPayment = (o) -> {
            System.out.println("PERFORM PAYMENT" + o);
        };
        Consumer<Order> dispatchOrder = (o) -> {
            System.out.println("DISPATCH ORDER" + o);
        };

        for (int i = 0; i < 100; i++) {
//            CompletableFuture.supplyAsync(()-> getOrder)
//                    .thenApply(o->enrichOrder)
//                    .thenApply(o->performPayment)
//                    .thenApply(o->dispatchOrder)
//                    .thenApply(o->dispatchOrder)
//                    .thenAccept(o->sendEmail(o));

            ExecutorService cpubound = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            ExecutorService iobound = Executors.newCachedThreadPool();
            CompletableFuture.supplyAsync(()-> getOrder, iobound)
                    .thenApplyAsync(o->enrichOrder, cpubound)
                    .thenApplyAsync(o->performPayment, iobound)
                    .thenApplyAsync(o->dispatchOrder)
                    .thenAccept(o->sendEmail(o));

            // If we don;t use any Executors threadpool, it will use ForkJoinPool.commonPool()
        }
    }

    private static<T> void sendEmail(T o) {
        System.out.println("Sending email about the order" + o);
    }

    static class Order {

    }
    @Data
    @AllArgsConstructor
    static class GetPriceTask implements Runnable {
        private String url;
        private int productId;
        private Set<Integer> prices;
        private CountDownLatch latch = null;

        @Override
        public void run() {
            // make http call to get price
            try {
                sleep(1000);
                prices.add(new Random().nextInt(1000));
                if (latch !=null) latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

