package week.three.day.one;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ThreadLockExample {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int count = 0;

    // Lock ishlatish namunasi
    public void performTask() {
        lock.lock();  // Qulifni qo'lga kiritish
        try {
            count++;
            System.out.println("Count: " + count);
        } finally {
            lock.unlock();  // finally blokida qulfni bo'shatish
        }
    }

    // tryLock() ishlatish na'munasi
    public boolean tryPerformTask() {
        if (lock.tryLock()) {  // Qulfni qo'lga kiritishga urinish
            try {
                count++;
                System.out.println("Count (tryLock): " + count);
                return true;
            } finally {
                lock.unlock();  // Qulfni bo'shatish
            }
        } else {
            System.out.println("Qulfni qo'lga kiritib bo'lmadi!");
            return false;
        }
    }

    // lockInterruptibly() ni ishlatish na'munasi, hozirgi ishlab turgan oqimni to'xtatadi
    public void performTaskInterruptibly() throws InterruptedException {
        lock.lockInterruptibly();  // Qulfni qo'lga kiritish uslubi
        try {
            // Критическая секция
            count++;
            System.out.println("Count (interruptibly): " + count);
        } finally {
            lock.unlock();
        }
    }

    // Condition ishlatish uslubi, kutish holatini boshqarish uchun
    public void waitForCondition() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Shart bajarilishini kutyabman...");
            condition.await();  // Oqim signalni kutmoqda
            System.out.println("Shart bajarilib bo'ldi");
        } finally {
            lock.unlock();
        }
    }

    public void signalCondition() {
        lock.lock();
        try {
            System.out.println("Shart uchun signal jo'natish...");
            condition.signal();  // condition.await() ni jo'natgan oqim uchun signal jo'natish
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ThreadLockExample example = new ThreadLockExample();

        example.performTask();  // Qulfni standart uslubda qo'lga kiritish
        example.tryPerformTask();  // tryLock ni na'munasi

        // Condition ishlatishni uslubi
        new Thread(() -> {
            try {
                example.waitForCondition();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);  // Kutish, signal ishlash tarzini namoyish qilish uchun
                example.signalCondition();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
