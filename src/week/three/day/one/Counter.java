package week.three.day.one;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private int counter =0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            counter++;
        }finally {
            lock.unlock();
        }
    }

    public int getCounter() {
        return counter;
    }
}
