package week.three.day.one;

import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {

    private final CountDownLatch latch;

    public Worker(CountDownLatch countDownLatch) {
        this.latch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()
                + ": Ish bajarishni boshladi, count down: " + latch.getCount());
        try {
            Thread.sleep(2000);
            latch.countDown();
            System.out.printf("%s o'z ishini bajarib bo'ldi! count down:%d\n"
                    .formatted(Thread.currentThread().getName(), latch.getCount()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        int numberOfWorkers = 3;
        CountDownLatch latch = new CountDownLatch(numberOfWorkers);
        for (int i = 0; i < 5; i++) {
            new Thread(new Worker(latch)).start();
        }
        latch.await(); //Hamma vazifalar bajarib bo'lishini kutamiz.
    }
}
