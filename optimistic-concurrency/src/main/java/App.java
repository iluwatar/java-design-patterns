import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.iluwatar.optimistic.concurrency.model.Order;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdvancedMapping");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Order order = new Order(0, 1, "coconut", "");
        em.persist(order);

        transaction.commit();
        em.close();
        emf.close();
    }
}
