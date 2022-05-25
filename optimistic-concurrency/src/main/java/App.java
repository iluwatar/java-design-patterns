import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iluwatar.optimistic.concurrency.Product;
import com.iluwatar.optimistic.concurrency.ProductDAO;
import com.iluwatar.optimistic.concurrency.ProductService;

public class App {
    public static void main(String[] args) {
//        EntityManagerFactory emf =
//                Persistence.createEntityManagerFactory("AdvancedMapping");
//        EntityManager em = emf.createEntityManager();
//        ProductDAO productDAO = new ProductDAO(em);
//        // create some products in database
//        Product apple = new Product("apple", "The apple is very delicious!", 1.5, 10);
//        Product banana = new Product("banana", "The banana is fresh!", 2.1, 20);
//
//        productDAO.save(apple);
//        productDAO.save(banana);
//
//        em.close();
//        emf.close();
        // in parralel
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                EntityManagerFactory emf =
//                        Persistence.createEntityManagerFactory("AdvancedMapping");
//                EntityManager em = emf.createEntityManager();
//                ProductService productService = new ProductService(em);
//
//                productService.buy(2, 2, false);
//
//                em.close();
//                emf.close();
//            }
//        });
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                EntityManagerFactory emf =
//                        Persistence.createEntityManagerFactory("AdvancedMapping");
//                EntityManager em = emf.createEntityManager();
//                ProductService productService = new ProductService(em);
//
//                productService.buy(2, 2, true);
//
//                em.close();
//                emf.close();
//            }
//        });
//
//        t1.start();
//        t2.start();

        EntityManagerFactory emf =
                        Persistence.createEntityManagerFactory("AdvancedMapping");
        EntityManager em = emf.createEntityManager();
        ProductService productService = new ProductService(em);

        // in sequence
        productService.buy(2, 2, false);
        productService.buy(2, 2, true);

        em.close();
        emf.close();

    }
}
