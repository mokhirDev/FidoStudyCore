package week.three.day.five;

class Resource {
    private boolean inUse = false;

    public synchronized boolean tryUse() {
        if (!inUse) {
            inUse = true;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        inUse = false;
    }
}

public class LiveLockExample {
    public static void main(String[] args) {
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();

        Thread t1 = new Thread(() -> {
            while (true) {
                if (resource1.tryUse()) {
                    System.out.println("Thread 1: захватил ресурс 1");
                    try { Thread.sleep(10); } catch (InterruptedException e) {}
                    if (resource2.tryUse()) {
                        System.out.println("Thread 1: захватил ресурс 2");
                        break;
                    } else {
                        System.out.println("Thread 1: освобождает ресурс 1");
                        resource1.release();
                    }
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                if (resource2.tryUse()) {
                    System.out.println("Thread 2: захватил ресурс 2");
                    try { Thread.sleep(10); } catch (InterruptedException e) {}
                    if (resource1.tryUse()) {
                        System.out.println("Thread 2: захватил ресурс 1");
                        break;
                    } else {
                        System.out.println("Thread 2: освобождает ресурс 2");
                        resource2.release();
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }
}
class LiveLockSolutionWithOrder {
    public static void main(String[] args) {
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();

        Thread t1 = new Thread(() -> {
            int attempts = 0;
            while (attempts < 10) {
                if (resource1.tryUse()) {
                    System.out.println("Thread 1: захватил ресурс 1");
                    try { Thread.sleep((int) (Math.random() * 50)); } catch (InterruptedException e) {}

                    if (resource2.tryUse()) {
                        System.out.println("Thread 1: захватил ресурс 2");
                        break;
                    } else {
                        System.out.println("Thread 1: освобождает ресурс 1");
                        resource1.release();
                        attempts++;
                    }
                }
            }
            System.out.println("Thread 1 завершил попытки");
        });

        Thread t2 = new Thread(() -> {
            int attempts = 0;
            while (attempts < 10) {
                if (resource1.tryUse()) {
                    System.out.println("Thread 2: захватил ресурс 1");
                    try { Thread.sleep((int) (Math.random() * 50)); } catch (InterruptedException e) {}

                    if (resource2.tryUse()) {
                        System.out.println("Thread 2: захватил ресурс 2");
                        break;
                    } else {
                        System.out.println("Thread 2: освобождает ресурс 1");
                        resource1.release();
                        attempts++;
                    }
                }
            }
            System.out.println("Thread 2 завершил попытки");
        });

        t1.start();
        t2.start();
    }
}

