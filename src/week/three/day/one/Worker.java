package week.three.day.one;

import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {

    private final CountDownLatch latch;

    public Worker(CountDownLatch countDownLatch) {
        this.latch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": do task...");
        latch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        int numberOfWorkers = 3;
        CountDownLatch latch = new CountDownLatch(numberOfWorkers);
        for(int i=0; i<numberOfWorkers; i++) {
            new Thread(new Worker(latch)).start();
        }

        latch.await(); //Hamma vazifalar bajarib bo'lishini kutamiz.
        System.out.println("Hamma vazifalar tugadi!");
    }
}
