package week.three.day.two;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

public class ReadWriteLockExample {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    private int sharedData = 0;

    public int read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " malumotlar o'qilmoqda: " + sharedData);
            return sharedData;
        } finally {
            readLock.unlock();
        }
    }

    public void write(int value) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " malumotlar yozilmoqda: " + value);
            sharedData = value;
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockExample example = new ReadWriteLockExample();

        Runnable readTask = () -> {
            for (int i = 0; i < 5; i++) {
                example.read();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writeTask = () -> {
            for (int i = 0; i < 5; i++) {
                example.write(i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread writerThread = new Thread(writeTask, "Yozish oqimi 1");
        Thread readerThread1 = new Thread(readTask, "O'qish oqimi 1");
        Thread readerThread2 = new Thread(readTask, "O'qish oqimi 2");


        writerThread.start();
        readerThread1.start();
        readerThread2.start();
    }
}

