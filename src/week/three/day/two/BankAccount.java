package week.three.day.two;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private double balance;
    private final Lock lock;
    public String bankName;

    public BankAccount(ReentrantLock reentrantLock, double initialBalance, String bankName) {
        this.lock = reentrantLock;
        this.balance = initialBalance;
        this.bankName = bankName;
    }

    public boolean transferTo(BankAccount transferTo, double amount) throws InterruptedException {
        if (transferTo.equals(this)) {
            System.out.printf("O'zidan o'ziga o'tkazib bo'lmaydi: %s \n".formatted(this.bankName));
            return false;
        }
        if (this.lock.tryLock() && transferTo.lock.tryLock()) {
            try {
                if (balance >= amount) {
                    Thread.sleep(2000);
                    this.balance -= amount;
                    transferTo.balance += amount;
                    System.out.println("From: " + this.bankName + " transferred: " + amount + " to: " + transferTo.bankName);
                    return true;
                } else {
                    System.out.printf("Bank: %s hisobida yetarlicha mablag' mavjud emas! Mablag': %f \n"
                            .formatted(this.bankName, amount));
                    return false;
                }
            } finally {
                transferTo.lock.unlock();
                this.lock.unlock();
            }
        } else {
            System.out.printf("O'tkazuv bekor qilindi! %s yoki %s bant!\n"
                    .formatted(this.bankName, transferTo.bankName));
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        ReentrantLock lock = new ReentrantLock();
        BankAccount bankAccount1 = new BankAccount(new ReentrantLock(), 90, "Bank 1");
        BankAccount bankAccount2 = new BankAccount(new ReentrantLock(), 40, "Bank 2");
        BankAccount bankAccount3 = new BankAccount(new ReentrantLock(), 70, "Bank 3");
        BankAccount bankAccount4 = new BankAccount(new ReentrantLock(), 60, "Bank 4");

//        bankAccount1.transferTo(bankAccount2, 200);
//        bankAccount1.transferTo(bankAccount1, 20);
//        bankAccount1.transferTo(bankAccount1, 20);
//        bankAccount1.transferTo(bankAccount1, 20);
        Thread thread1 = new Thread(() -> {
            try {
                bankAccount1.transferTo(bankAccount2, 20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                bankAccount2.transferTo(bankAccount4, 40);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

//        bankAccount1.transferTo(bankAccount2, 20);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Bank1 account: " + bankAccount1.balance);
        System.out.println("Bank2 account: " + bankAccount2.balance);
        System.out.println("Bank4 account: " + bankAccount4.balance);
    }
}

