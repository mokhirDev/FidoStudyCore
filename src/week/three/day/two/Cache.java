package week.three.day.two;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

class Cache {
    private final Map<String, String> cache = new HashMap<>();
    private final StampedLock lock = new StampedLock();

    public String getValue(String key){
        try {
            long stamp = lock.tryOptimisticRead();
            String value = cache.get(key);
            Thread.sleep(5000);
            if (!lock.validate(stamp)) {
                System.out.printf("%s: stamp:%d not valid, value:%s\nreading locking...\n"
                        .formatted(Thread.currentThread().getName(), stamp, value));
                stamp = lock.readLock();
                try {
                    value = cache.get(key);
                } finally {
                    System.out.printf("%s: value:%s\nreading unlocking...\n"
                            .formatted(Thread.currentThread().getName(), value));
                    lock.unlockRead(stamp);
                }
            }else{
                System.out.printf("%s: stamp:%d is valid, value:%s%n\n", Thread.currentThread().getName(), stamp, value);
            }
            return value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateCache(String key, String value) throws InterruptedException {
        Thread.sleep(1000);
        long stamp = lock.writeLock();
        System.out.printf("%s: value:%s\nwriting locking...\n", Thread.currentThread().getName(), value);
        try {
            cache.put(key, value);
        } finally {
            System.out.printf("%s: value:%s\nwriting unlocking...\n", Thread.currentThread().getName(), value);
            lock.unlockWrite(stamp);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Cache cache = new Cache();
        Runnable user1 = ()->{
            try {
                cache.updateCache(Thread.currentThread().getName(), "cache 1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable user2 = ()->{
            try {
                cache.updateCache(Thread.currentThread().getName(), "cache 2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };


        Thread userThread1 = new Thread(user1);
        Thread userThread2 = new Thread(user2);
        userThread1.setName("Mokhirbek");
        userThread2.setName("Javokhir");
        userThread1.start();
        userThread2.start();
        userThread1.join();
        userThread2.join();

        Runnable getCache = ()->{
            String value = cache.getValue(userThread1.getName());
            System.out.println("cache of user 1: " + value);
        };
        Thread getCacheThread = new Thread(getCache);
        getCacheThread.start();
//        getCacheThread.join();

        Thread lastTask = new Thread(user1);
        lastTask.start();
        lastTask.join();
        System.out.println("Process finished!");
    }
}
