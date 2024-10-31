package week.three.day.five;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {
    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                lock1.lock();
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()
                        + ": Lock 1 ni qulfladi va Lock 2 ni ochishga harakat qilmoqda...");
                lock2.lock();
                System.out.println(Thread.currentThread().getName()
                        + ": Lock 2 qulifi ochildi!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock1.unlock();
                lock2.unlock();
            }


        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                lock2.lock();
                System.out.println(Thread.currentThread().getName()
                        + ": Lock 2 ni qulfladi va Lock 1 ni ochishga harakat qilmoqda...");
                lock1.lock();
                System.out.println(Thread.currentThread().getName()
                        + ": Lock 1 qulifi ochildi!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock2.unlock();
                lock1.unlock();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t1.join();
        System.out.println("Process tugadi!");
    }
}

class SolutionDeadLock {
    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                lock1.lock();
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()
                        + ": Lock 1 ni qulfladi va Lock 2 ni ochishga harakat qilmoqda...");
                lock1.unlock();
                lock2.lock();
                System.out.println(Thread.currentThread().getName()
                        + ": Lock 2 qulifi ochildi!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock2.unlock();
            }


        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                lock2.lock();
                System.out.println(Thread.currentThread().getName()
                        + ": Lock 2 ni qulfladi va Lock 1 ni ochishga harakat qilmoqda...");
                lock2.unlock();
                lock1.lock();
                System.out.println(Thread.currentThread().getName()
                        + ": Lock 1 qulifi ochildi!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock1.unlock();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t1.join();
        System.out.println("Process tugadi!");
    }
}

class DeadlockSolution {
    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> lockResources(resourceA, resourceB));
        Thread t2 = new Thread(() -> lockResources(resourceA, resourceB));

        t1.start();
        t2.start();
    }

    private static void lockResources(Object r1, Object r2) {
        synchronized (r1) {
            System.out.println(Thread.currentThread().getName() + " locked " + r1);

            synchronized (r2) {
                System.out.println(Thread.currentThread().getName() + " locked " + r2);
            }
        }
    }
}
