package week.three.day.two;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Database {
    private final Map<String, List<String>> data = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public List<String> readData(String key) {
        lock.readLock().lock();
        try {
            List<String> value = data.get(key);
            Thread.sleep(1000);
            System.out.printf("Ma'lumot o'qildi! %s - key: %s value: %s\n",
                    Thread.currentThread().getName(), key, value);
            return value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void writeData(String key, String value) {
        lock.writeLock().lock();
        try {
            if (data.containsKey(key)) {
                data.get(key).add(value);
                Thread.sleep(2000);
                System.out.printf("Ma'lumot qo'shildi! %s - key: %s value: %s\n",
                        Thread.currentThread().getName(), key, value);
            }else {
                data.put(key, new ArrayList<>(Arrays.asList(value)));
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Database db = new Database();
        Thread thread1 = new Thread(() -> {
            Thread.currentThread().setName("User: Mokhirbek");
            db.writeData("mokhirDev", "value1");
            db.writeData("mokhirDev", "value2");
            db.writeData("mokhirDev", "value3");
        });
        Thread thread2 = new Thread(() -> {
            Thread.currentThread().setName("User: Javohir");
            db.writeData("javohirDev", "value1");
            db.writeData("javohirDev", "value2");
            db.writeData("javohirDev", "value3");
            db.writeData("javohirDev", "value4");
        });


        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Thread thread3 = new Thread(() -> {
            Thread.currentThread().setName("User: Abdulloh");
            List<String> javohirDev = db.readData("javohirDev");
            List<String> mokhirDev = db.readData("mokhirDev");
            System.out.printf("%s :\n%s values: %s\n%s values: %s\n"
                    .formatted(Thread.currentThread().getName(),
                            thread1.getName(), mokhirDev,
                            thread2.getName(), javohirDev
                    ));
        });
        thread3.start();
        thread3.join();
        System.out.println("Result:");
        db.data.forEach((key, value) -> System.out.printf("%s: %s\n", key, value));
    }
}