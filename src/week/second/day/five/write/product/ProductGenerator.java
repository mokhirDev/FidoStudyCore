package week.second.day.five.write.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class ProductGenerator {

    private static final String[] CATEGORIES = {"Electronics", "Clothing", "Home", "Toys", "Sports", "Books"};

    public static List<Product> generateProducts(int count) {
        List<Product> products = new ArrayList<>(count);
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            long id = i + 1;
            String name = "week.second.day.five.write.product.Product" + id;
            String category = CATEGORIES[random.nextInt(CATEGORIES.length)];
            BigDecimal price = BigDecimal.valueOf(50 + (5000 - 50) * random.nextDouble()); // Random price between 50 and 5000
            int stock = random.nextInt(1000); // Random stock between 0 and 1000

            Product product = new Product(id, name, category, price, stock);
            products.add(product);
        }

        return products;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Product> products = generateProducts(100); // Generating 1 million products

//        List<week.second.day.five.write.product.Product> sortedProducts = products.parallelStream().filter(product -> product.getCategory().equals("Electronics"))
//                .limit(100)
//                .map(pr -> {
//                    week.second.day.five.write.product.Product product = new week.second.day.five.write.product.Product();
//                    product.setName(pr.getName());
//                    product.setPrice(pr.getPrice());
//                    return product;
//                })
//                .sorted(Comparator.comparing(week.second.day.five.write.product.Product::getPrice))
////                .forEach(System.out::println)
//                .peek(System.out::println)
//                .toList();
//
//        sortedProducts.stream().forEach(product -> {
//            System.out.print("week.second.day.five.write.product.Product name: " + product.getName());
//            System.out.println(", price: " + product.getPrice());
//        });
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(()->{
            products.parallelStream()
                    .filter(product -> product.getPrice().compareTo(new BigDecimal(2000))<0)
                    .forEach(product -> System.out.println(Thread.currentThread().getName() + ": " + product.getName()+", price: "+product.getPrice()+", stock: "+product.getStock()));
        }).get();
    }

}