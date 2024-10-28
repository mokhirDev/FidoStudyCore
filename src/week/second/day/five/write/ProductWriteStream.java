package week.second.day.five.write;


import week.second.day.five.write.product.Product;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import static week.second.day.five.write.product.ProductGenerator.generateProducts;

public class ProductWriteStream{

    public static void main(String[] args) {
        List<Product> products = generateProducts(10);
        try(FileOutputStream fileOutputStream = new FileOutputStream("src/week/second/day/five/write/products.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(products);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
