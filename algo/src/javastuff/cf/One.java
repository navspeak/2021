package javastuff.cf;

import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class One {
    public static int compute(){
        System.out.println("Compute: "+ Thread.currentThread());
        return 2;
    }
    public static void print(int data){
        System.out.println("print: "+ Thread.currentThread());
        System.out.println(data);
    }
    public static CompletableFuture<Integer> create(){
        return CompletableFuture.supplyAsync(One::compute);
    }
    public static void main(String[] args) {
        System.out.println("Main: "+ Thread.currentThread());
        create().thenAccept(One::print);
    }
}
