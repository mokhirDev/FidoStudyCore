package week.three.day.one;

import java.util.concurrent.locks.*;

class ProductCatalog {
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final StampedLock stampedLock = new StampedLock();

    // 1.ReentrantLock
    public void reentrantLockExample() {
        reentrantLock.lock();
        try {
            System.out.println("ReentrantLock: Performing task...");
        } finally {
            reentrantLock.unlock();
        }
    }

    // 2.ReadWriteLock
    public void readWriteLockExampleRead() {
        readWriteLock.readLock().lock();
        try {
            System.out.println("ReadWriteLock: Reading data...");
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void readWriteLockExampleWrite() {
        readWriteLock.writeLock().lock();
        try {
            System.out.println("ReadWriteLock: Writing data...");
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 3.StampedLock
    public void stampedLockExampleRead() {
        long stamp = stampedLock.tryOptimisticRead();
        try {
            System.out.println("StampedLock: Optimistic reading...");
            if (!stampedLock.validate(stamp)) {
                stamp = stampedLock.readLock();
                try {
                    System.out.println("StampedLock: Read lock acquired.");
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    public void stampedLockExampleWrite() {
        long stamp = stampedLock.writeLock();
        try {
            System.out.println("StampedLock: Writing data...");
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}

class LockDemo {
    public static void main(String[] args) {
        ProductCatalog catalog = new ProductCatalog();

        catalog.reentrantLockExample();
        catalog.readWriteLockExampleRead();
        catalog.readWriteLockExampleWrite();
        catalog.stampedLockExampleRead();
        catalog.stampedLockExampleWrite();
    }
}

