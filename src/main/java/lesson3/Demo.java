package lesson3;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product(10, "testUp", "test description update", 99);
        //productDAO.save(product);
        //System.out.println(productDAO.getProducts());
        //productDAO.update(product);
        productDAO.delete(10);
    }
}
