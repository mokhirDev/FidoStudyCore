package week.three.day.one;

import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<String> ex = new Exchanger<>();
        new Thread(new Worker(ex, "Data from Thread 1")).start();
        new Thread(new Worker(ex, "Data from Thread 2")).start();
    }
    static class Worker implements Runnable {
        private final Exchanger<String> exchanger;
        private final String data;

        public Worker(Exchanger<String> exchanger, String data) {
            this.exchanger = exchanger;
            this.data = data;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " sent: " + data);
                String response = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + " received: " + response);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
