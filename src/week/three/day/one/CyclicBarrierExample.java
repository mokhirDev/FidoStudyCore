package week.three.day.one;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int count = 2;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        for (int i = 0; i < 5; i++) {
            new Thread(new Worker(cyclicBarrier)).start();
        }
    }

    static class Worker implements Runnable {

        public CyclicBarrier cyclicBarrier;

        public Worker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("Thread: " + Thread.currentThread().getName() + " reaching to barrier");
            try {
                this.cyclicBarrier.await();
                System.out.println("Thread: " + Thread.currentThread().getName() + " continue task..");
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
