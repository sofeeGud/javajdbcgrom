package lesson5.hw;
import org.hibernate.Session;

public class ProductRepository {
    Session session = new HibernateUtils().createSessionFactory().openSession();
    public void save(Product product){
        try{
            session.getTransaction().begin();
            session.save(product);
            session.getTransaction().commit();
            System.out.println("Done");
            session.close();
        }catch (Exception e){
            session.getTransaction().rollback();
            System.err.println(e.getMessage());
        }
    }

    public void update(Product product){
        try{
            session.getTransaction().begin();
            session.update(product);
            session.getTransaction().commit();
            System.out.println("Done");
            session.close();
        }catch (Exception e) {
            session.getTransaction().rollback();
            System.err.println(e.getMessage());
        }
    }

    public void delete(long id){
        try{
            session.getTransaction().begin();
            Product product = session.load(Product.class, id);
            session.delete(product);
            session.getTransaction().commit();
            System.out.println("Done");
            session.close();
        }catch (Exception e){
            session.getTransaction().rollback();
            System.err.println(e.getMessage());
        }
    }


}
