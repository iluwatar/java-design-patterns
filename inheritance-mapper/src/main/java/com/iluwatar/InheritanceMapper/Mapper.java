package com.iluwatar.InheritanceMapper;

import com.iluwatar.InheritanceMapper.ClassObject.Player;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


/**
 * an abstract mapper to be inherited.
 */
public abstract class Mapper {
    /**Find Player from database.
     *  @param id    Player id
     * @return Player
     */
    public Player abstractFind(final int id) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdvancedMapping");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        return em.find(Player.class, id);

    }
    /**Save Player to database.
     * @param p     Player class object to save
     * @param update weather to save as an update or insert
     * @return Player
     */
    public Player save(final Player p, final boolean update) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdvancedMapping");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if (update) {
            em.merge(p);
        } else {
            em.persist(p);
        }

        transaction.commit();
        em.close();
        emf.close();
        return p;
    }
    /**load Player that was found.
     * @param rows a Player
     * @return Player
     */
    public Player load(final Player rows) {
        return rows;
    }

    /**Delete Player from database.
     * @param p Player to delete
     */
    void delete(final Player p) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdvancedMapping");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.contains(p) ? p : em.merge(p));
        transaction.commit();
        em.close();
        emf.close();
    }
}
