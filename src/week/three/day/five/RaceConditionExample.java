package week.three.day.five;

import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionExample {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                bankAccount.increment(1);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                bankAccount.increment(1);
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Bank account balance: " + bankAccount.getBalance());
    }
}

class BankAccount {
//    private int balance = 0;

    private AtomicInteger balance = new AtomicInteger(0);

    public void increment(int amount) {
        balance.addAndGet(amount);
//        balance += amount;
    }

    public void decrement(int amount) {
        balance.addAndGet(-amount);
//        balance -= amount;
    }

    public int getBalance() {
        return balance.get();
//        return balance;
    }
}