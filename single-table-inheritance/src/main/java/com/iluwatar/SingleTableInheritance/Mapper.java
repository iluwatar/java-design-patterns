package com.iluwatar.SingleTableInheritance;

import com.iluwatar.SingleTableInheritance.ClassObject.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * an abstract vehicle mapper to be inherited.
 */
public abstract class Mapper {

    /**
     * query rows from database.
     * @param id Vehicle id
     * @return list of rows
     */
    public List<Vehicle> abstractFind(final int id) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdvancedMapping");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Query query = em.createNativeQuery(
                "select * from vehicle where IDVEHICLE = ?;", Vehicle.class);
        query.setParameter(1, id);
        return query.getResultList();
    }

    /**
     * return a vehicle in the row.
     * @param rows list of Vehicle
     * @return Vehicle
     */
    public Vehicle load(final List<Vehicle> rows) {
        if (rows.size() != 1) {
            return null;
        }
        return rows.get(0);
    }

    /**
     * Save Vehicle to database.
     *  @param v      Vehicle class object to save
     * @param update whelther to save as an update or insert
     * @return Vehicle
     */
    public Vehicle save(final Vehicle v, final boolean update) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdvancedMapping");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if (update) {
            em.merge(v);
        } else {
            em.persist(v);
        }

        transaction.commit();
        em.close();
        emf.close();
        return v;
    }

    /**
     * Delete Vehicle from database.
     * @param v Vehicle to delete
     */
    void delete(final Vehicle v) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdvancedMapping");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Query query = em.createNativeQuery(
                "delete from vehicle where IDVEHICLE = ?;", Vehicle.class);
        query.setParameter(1, v.getIdVehicle());
        query.executeUpdate();

        transaction.commit();
        em.close();
        emf.close();
    }
}
