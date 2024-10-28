package week.second.day.five.read;

import week.second.day.five.write.product.Product;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadProductStream {
    public static void main(String[] args) {
        Path productsPath = Paths.get("src/week/second/day/five/write/products.txt");
        try(FileInputStream inputStream = new FileInputStream(productsPath.toFile());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            List<Product>productList = (List<Product>)objectInputStream.readObject();
            for (Product product : productList) {
                String fullName = product.getName();
                String productName = fullName.substring(fullName.lastIndexOf(".")+1);
                System.out.println(productName);
            }
        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
