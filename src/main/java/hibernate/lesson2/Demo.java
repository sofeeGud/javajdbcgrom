package hibernate.lesson2;

import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        Product product1 = new Product();
        product1.setName("table new111");
        product1.setDescription("grey & blue");
        product1.setPrice(70);
        productDAO.save(product1);

        Product product2 = new Product();
        product2.setName("table new222");
        product2.setDescription("grey & blue");
        product2.setPrice(80);
        productDAO.delete(product2);

        Product product3 = new Product();
        product3.setName("table new333_new");
        product3.setDescription("grey & blue");
        product3.setPrice(90);
        productDAO.update(product3);

        List<Product> products = Arrays.asList(product1, product2, product3);
        productDAO.saveAll(products);
        //productDAO.updateAll(products);
        //productDAO.deleteAll(products);
    }
}
