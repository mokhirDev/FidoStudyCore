package week.three.day.five;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class StarvationExample {
    public static void main(String[] args) {
//        Object lock = new Object();
        Semaphore semaphore = new Semaphore(1);
        Thread thread1 = new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    semaphore.acquire();
                    Thread.sleep(500);
                    System.out.println("High priority Thread: " + i++);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    semaphore.acquire();
                    Thread.sleep(500);
                    System.out.println("Low priority Thread: " + i++);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            }
        });

        thread1.setPriority(Thread.MAX_PRIORITY);
        thread2.setPriority(Thread.MIN_PRIORITY);
        thread1.start();
        thread2.start();
    }
}

class StarvationSolution {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        Thread thread1 = new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    lock.lock();
                    Thread.sleep(500);
                    System.out.println("High priority Thread: " + i++);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    lock.lock();
                    Thread.sleep(500);
                    System.out.println("Low priority Thread: " + i++);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });

        thread1.setPriority(Thread.MAX_PRIORITY);
        thread2.setPriority(Thread.MIN_PRIORITY);
        thread1.start();
        thread2.start();
    }
}
