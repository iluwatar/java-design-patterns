package com.iluwatar.metamapping.service;

import com.iluwatar.metamapping.model.User;
import com.iluwatar.metamapping.utils.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class UserService{
    private static final SessionFactory factory = HibernateUtil.getSessionFactory();
    
    public List<User> listUser(){
        LOGGER.info("list all users.");
        Session session = factory.openSession();
        Transaction tx = null;
        List<User> users = new ArrayList<>();
        try{
            tx = session.beginTransaction();
            List userIter = session.createQuery("FROM User").list();
            for (Iterator iterator = userIter.iterator(); iterator.hasNext();){
                users.add((User) iterator.next());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return users;
    }
    
    public int createUser(User user){
        LOGGER.info("create user: "+user.getUsername());
        Session session = factory.openSession();
        Transaction tx = null;
        Integer id = null;
        try{
            tx = session.beginTransaction();
            id = (Integer) session.save(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        LOGGER.info("create user "+user.getUsername()+" at "+id);
        return id;
    }
    
    public void updateUser(Integer id, User user){
        LOGGER.info("update user at "+id);
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User _user = (User) session.get(User.class, id);
            _user.setUsername(user.getUsername());
            _user.setPassword(user.getPassword());
            session.update(_user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    
    public void deleteUser(Integer id){
        LOGGER.info("delete user at: "+id);
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    
    public User getUser(Integer id){
        LOGGER.info("get user at: "+id);
        Session session = factory.openSession();
        Transaction tx = null;
        User user = null;
        try{
            tx = session.beginTransaction();
            user = (User) session.get(User.class, id);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return user;
    }

    
    public void close() {
        HibernateUtil.shutdown();
    }
}
