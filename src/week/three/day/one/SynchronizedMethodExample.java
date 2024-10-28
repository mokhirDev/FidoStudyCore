package week.three.day.one;

public class SynchronizedMethodExample {
    private int count = 0;
    public synchronized void increment() {
        System.out.println("Thread is: "+Thread.currentThread().getName());
        count++;
        System.out.println("Thread is: "+Thread.currentThread().getName()+", count: "+getCount());
    }
    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        SynchronizedMethodExample example = new SynchronizedMethodExample();
        for (int i = 0; i < 10; i++) {
            new Thread(example::increment).start();
        }
    }
}
