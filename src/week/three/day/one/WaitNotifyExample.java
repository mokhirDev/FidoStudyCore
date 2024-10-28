package week.three.day.one;

public class WaitNotifyExample {
    private final Object lock = new Object();
    private boolean conditionMet = false;

    public void awaitCondition() throws InterruptedException {
        synchronized (lock) {
            while (!conditionMet) {
                System.out.println(Thread.currentThread().getName() + " kutmoqda...");
                lock.wait();
            }
            System.out.println(Thread.currentThread().getName() + " shart bajarildi!");
        }
    }

    public void signalCondition() {
        synchronized (lock) {
            conditionMet = true;
            System.out.println(Thread.currentThread().getName() + " habar bermoqda...");
            lock.notify();
        }
    }

    public void signalAllConditions() {
        synchronized (lock) {
            conditionMet = true;
            System.out.println(Thread.currentThread().getName() + " barchasiga habar bermoqda...");
            lock.notifyAll();
        }
    }

    public static void main(String[] args) {
        WaitNotifyExample example = new WaitNotifyExample();

        Thread t1 = new Thread(() -> {
            try {
                example.awaitCondition();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                example.signalCondition();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}

