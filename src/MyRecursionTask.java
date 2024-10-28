import week.second.day.five.write.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyRecursionTask extends RecursiveTask<List<Product>> {
    private final int start;
    private final int end;
    private final List<Product> products;
    private final BigDecimal price;

    // Конструктор
    public MyRecursionTask(int start, int end, List<Product> products, BigDecimal price) {
        this.start = start;
        this.end = end;
        this.products = products;
        this.price = price;
    }

    @Override
    protected List<Product> compute() {
        List<Product> resultProducts = new ArrayList<>();
        if (end - start <= 1000) {
            for (int i = start; i <end; i++) {
                if (products.get(i).getPrice().compareTo(price) > 0) {
                    resultProducts.add(products.get(i));
                }
            }
            return resultProducts;
        } else {
            int mid = (start + end) / 2;
            MyRecursionTask left = new MyRecursionTask(start, mid, products, price);
            MyRecursionTask right = new MyRecursionTask(mid + 1, end, products, price);
            left.fork();
            List<Product> rightResult = right.compute();
            List<Product> leftResult = left.join();
            resultProducts.addAll(leftResult);
            resultProducts.addAll(rightResult);
            return resultProducts;
        }
    }
}
