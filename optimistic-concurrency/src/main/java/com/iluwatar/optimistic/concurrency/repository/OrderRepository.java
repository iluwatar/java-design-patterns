package com.iluwatar.optimistic.concurrency.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.iluwatar.optimistic.concurrency.model.Order;

public class OrderRepository {
    private EntityManager em;
    public OrderRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public void update(Order order) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // clone
            Order found = em.find(Order.class, order.getId());
            if (found != null) {
                em.merge(order);
            }

            tx.commit();
        } catch (IllegalArgumentException e) {

        } finally {
            em.close();
        }
    }

}
