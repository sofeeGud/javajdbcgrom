package hibernate.lesson1.hw;

public class Demo {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        Product product = new Product();
        product.setId(99);
        product.setName("table");
        product.setDescription("grey & blue & red");
        product.setPrice(70);

        productRepository.save(product);
        productRepository.update(product);
        productRepository.delete(product.getId());
    }
}
