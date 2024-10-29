package week.three.day.three;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchProduct {
    public static void main(String[] args) {
        int numProducts = 10;
        List<Product> productList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numProducts; i++) {
            double price = 100 + random.nextInt(901);
            productList.add(new Product("Product: " + i, price, random.nextInt(100)));
        }

        ForkJoinPool pool = new ForkJoinPool();
        MyRecursiveTask task = new MyRecursiveTask(0, productList.size(), productList, 500.0);
        List<Product> foundProducts = pool.invoke(task);
        for (Product product : foundProducts) {
            System.out.println(product);
        }
    }
}

class Product {
    String name;
    double price;
    int salesCount;

    public Product(String name, double price, int salesCount) {
        this.name = name;
        this.price = price;
        this.salesCount = salesCount;
    }

    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + ", salesCount=" + salesCount + '}';
    }
}

class MyRecursiveTask extends RecursiveTask<List<Product>> {
    public int start = 0;
    public int end = 0;
    public static int MAX_COUNT = 1000;
    public List<Product> productList;
    public Double maxPrice;

    public MyRecursiveTask(int start, int end, List<Product> productList, Double maxPrice) {
        this.start = start;
        this.end = end;
        this.productList = productList;
        this.maxPrice = maxPrice;
    }

    @Override
    protected List<Product> compute() {
        List<Product> result = new ArrayList<>();
        if (end - start <= MAX_COUNT) {
            for (int i = start; i < end; i++) {
                if (productList.get(i).price <= maxPrice) {
                    result.add(productList.get(i));
                }
            }
            return result;
        } else {
            int middle = (start + end) / 2;
            MyRecursiveTask left = new MyRecursiveTask(start, middle, productList, maxPrice);
            MyRecursiveTask right = new MyRecursiveTask(middle, end, productList, maxPrice);
            left.fork();
            List<Product> rightResult = right.compute();
            List<Product> leftResult = left.join();
            result.addAll(leftResult);
            result.addAll(rightResult);
            return result;
        }
    }

}
