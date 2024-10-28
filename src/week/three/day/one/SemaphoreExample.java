package week.three.day.one;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 10; i++) {
            new Thread(new Worker(semaphore)).start();
        }
    }

    static class Worker implements Runnable {
        private final Semaphore semaphore;
        public Worker(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try{
                this.semaphore.acquire(); //access olamiz
                System.out.println("Thread: "+Thread.currentThread().getId()+" doing task...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                System.out.println("Thread: "+Thread.currentThread().getId()+" done!");
                this.semaphore.release();//Accessni bo'shatamiz
            }
        }
    }
}
