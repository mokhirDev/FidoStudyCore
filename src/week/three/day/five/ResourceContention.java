package week.three.day.five;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ResourceContention {
    Semaphore semaphore = new Semaphore(5);

    public void useResource() {
        try {
            semaphore.acquire();
            Thread.sleep(10);
            System.out.println(Thread.currentThread().getName() + ": use resource...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

    public synchronized void releaseResource() {
        try {
            Thread.sleep(10);
            System.out.println(Thread.currentThread().getName() + ": use resource...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10_000);
        ResourceContention resource = new ResourceContention();
        Runnable runnable = resource::useResource;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i++) {
            service.submit(runnable);
        }
        service.shutdown();
        while (!service.isTerminated()) {}
        long endTime = System.currentTimeMillis();
        System.out.println("Duration: " + (endTime - startTime));
    }
}
