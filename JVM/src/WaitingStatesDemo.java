public class WaitingStatesDemo {
    public static void main(String[] args) {
        ThreadA someThread = new ThreadA();
        someThread.start();
        try {
            synchronized (someThread) {
                someThread.wait();
            }
        } catch (InterruptedException e){

        }
    }
}

class ThreadA extends Thread {
    @Override
    public void run() {
        try {
            synchronized (this) { // enters its own object monitor
                sleep(30000);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        notify();// will wake up main thread
    }
}
// See 1Waiting.png 1ThreadA.png