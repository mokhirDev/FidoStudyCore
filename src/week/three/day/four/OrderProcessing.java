package week.three.day.four;


import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class OrderProcessing {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    public static void main(String[] args) {
        CompletableFuture<Void> orderingProccess = CompletableFuture.supplyAsync(() -> getUserData(123))
                .thenCombineAsync(getOrderingAddress(123), (userData, address) -> {
                    log("Foydalanuvchi haqida ma'lumot topildi, manzil: " + address);
                    return address;
                })
                .thenCombineAsync(checkProductFromAllWarehouses("product123"), (userData, stockLocation) -> {
                    if (stockLocation == null) {
                        throw new RuntimeException("Mahsulot barcha omborlarda mavjud emas");
                    }
                    log("Mahsulot omborda topildi, ombor manzili: " + stockLocation);
                    return stockLocation;
                })
                .thenCompose(OrderProcessing::calculateDelivery)
                .thenComposeAsync(cost -> tryTask(() -> paymentProcess(cost), 3))
                .thenAcceptBothAsync(sendNotification(), (paymentStatus, notificationStatus) -> {
                    if (paymentStatus && notificationStatus) {
                        log("Buyyurtma muvafaqiyatli hisoblanib chiqilindi va tasdiqlash jo'natilindi");
                    }
                })
                .orTimeout(10, TimeUnit.SECONDS)
                .exceptionally(ex -> {
                    log("Buyurtma amaliyoti jarayonida hatolik yuzaga keldi: %s".formatted(ex.getMessage()));
                    return null;
                });

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(
                orderingProccess,
                logOrderDetails(),
                generateReceipt()
        );

        allTasks.join();

    }

    public static String getUserData(int userId) {
        wait(1000);
        log("Id orqali foydalanuvchi haqida ma'lumot olish: %d".formatted(userId));
        return "UserId";
    }

    public static CompletableFuture<String> getOrderingAddress(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            wait(1000);
            log("Foydalanuchining adressi qabul qilindi, ID: %d".formatted(userId));
            return "UserAddress";
        });
    }

    public static CompletableFuture<String> checkProductFromAllWarehouses(String productId) {
        List<CompletableFuture<String>> completableFutures = List.of(
                existStock(productId, "Warehouse A"),
                existStock(productId, "Warehouse B"),
                existStock(productId, "Warehouse C")
        );
        return CompletableFuture.anyOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply(result -> (String) result);
    }

    public static CompletableFuture<String> existStock(String productId, String warehouse) {
        return CompletableFuture.supplyAsync(() -> {
            wait(600);
//            boolean existStock = ThreadLocalRandom.current().nextBoolean();
            boolean existStock = true;
            log(warehouse + ":" + (existStock ? "Mahsulot mavjud" : "Mahsulot mavjud emas"));
            return existStock ? warehouse : null;
        });
    }

    public static CompletableFuture<Double> calculateDelivery(String stockLocation) {
        return CompletableFuture.supplyAsync(() -> {
            wait(1000);
            double costOfDel = ThreadLocalRandom.current().nextDouble(10, 100);
            log("Yetkazib berish hizmati narhi: %f, %s omboridan.".formatted(costOfDel, stockLocation));
            return costOfDel;
        });
    }

    public static CompletableFuture<Boolean> paymentProcess(double amount) {
        return CompletableFuture.supplyAsync(() -> {
            wait(1000);
            boolean paymentStatus = ThreadLocalRandom.current().nextBoolean();
            log("To'lov: %f miqdorida %s"
                    .formatted(amount, (paymentStatus ? "muvafaqiyatli o'tdi" : "bajarilmadi")));
            return paymentStatus;
        });
    }

    public static <T> CompletableFuture<T> tryTask(Supplier<CompletableFuture<T>> task, int maxAttempts) {
        return task.get().handle((result, ex) -> {
            if (ex == null || maxAttempts <= 1) {
                return CompletableFuture.completedFuture(result);
            } else {
                log("Hatolik sabab boshqatdan urinish: " + ex.getMessage());
                return tryTask(task, maxAttempts - 1);
            }
        }).thenCompose(Function.identity());
    }

    public static CompletableFuture<Boolean> sendNotification() {
        return CompletableFuture.supplyAsync(() -> {
            wait(1000);
            boolean notificationSent = true;
            log("Muvafaqiyatli jo'natildi: " + notificationSent);
            return notificationSent;
        });
    }

    public static CompletableFuture<Void> logOrderDetails() {
        return CompletableFuture.runAsync(() -> {
            wait(500);
            log("Buyurtma tafsilotlari chop etildi");
        });
    }

    public static CompletableFuture<Void> generateReceipt() {
        return CompletableFuture.runAsync(() -> {
            wait(500);
            log("Kvitansiya yaratildi");
        });
    }


    private static void wait(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void log(String log) {
        System.out.println("Log: " + log);
    }
}
