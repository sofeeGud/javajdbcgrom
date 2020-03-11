package lesson5.hw;
import org.hibernate.Session;

public class ProductRepository {

    public static void main(String[] args) {
        Session session = new HibernateUtils().createSessionFactory().openSession();
        session.getTransaction().begin();

        Product product = new Product();
        product.setId(99);
        product.setName("table_test");
        product.setDescription("grey & blue");
        product.setPrice(70);

        session.save(product);
        session.update(product);
        session.delete(product);

        session.getTransaction().commit();

        System.out.println("Done");

        session.close();
    }
}
