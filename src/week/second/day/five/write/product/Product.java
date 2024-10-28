package week.second.day.five.write.product;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Comparable<Product>, Serializable {
    private static final long serialVersionUID = 1L;


    private long id;
    private String name;
    private String category;
    private BigDecimal price;
    private int stock;

    public Product() {}

    public Product(long id, String name, String category, BigDecimal price, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    @Override
    public int compareTo(Product o) {
        return this.price.compareTo(o.getPrice());
    }
//    public static Map<String, Map<String, List<week.second.day.five.write.product.Product>>> groupProductsByCategoryAndPriceRange(List<week.second.day.five.write.product.Product> products) {
//        return products.stream()
//    }


    // toString for better debugging
    @Override
    public String toString() {
        return "week.second.day.five.write.product.Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}