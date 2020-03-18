package jdbc.lesson4;

import jdbc.Product;

import java.util.ArrayList;
import java.util.List;

public class Demo {


    public static void main(String[] args) {
        Product product1 = new Product(500, "test100", "1111111111", 100);
        Product product2 = new Product(600, "test200", "1111111111", 200);
        Product product3 = new Product(600, "test300", "1111111111", 300);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        TransactionDemo transactionDemo = new TransactionDemo();
        transactionDemo.save(products);
    }
}
