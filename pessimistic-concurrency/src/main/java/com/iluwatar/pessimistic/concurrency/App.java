package com.iluwatar.pessimistic.concurrency;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdvancedMapping");

        CustomerDao customerDao = new CustomerDao(emf);
        Customer obj1 = new Customer("John");
        Customer obj2 = new Customer("Abby");
        Customer obj3 = new Customer("Bob");
        LockManager lockManager = LockManager.getLockManager("CUSTOMER");
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(100);
                System.out.println("User 1, Obj1: " + lockManager.requestLock("user1", obj1));

            if (lockManager.requestLock("user1", obj1)) {
                obj1.lock("user1");
                Customer tmp = new Customer("Ben");
                customerDao.update(tmp, new String[obj1.getId()]);
                System.out.println(obj1.isLocked());
            }
            Thread.sleep(100);

            System.out.println("Release Obj1 " + lockManager.releaseLock(obj1));
            System.out.println("User 1, Obj3: " + lockManager.requestLock("user1", obj3));
            } catch (
                    Exception e) {
                throw new RuntimeException(e);
            }
        });
//        Thread t2 = new Thread(() -> {
//            try {
//                System.out.println("User 2, Obj1: " + lockManager.requestLock("user2", obj1));
//
//            System.out.println(obj1.isLocked());
//
//            try {
//                Thread.sleep(100);
//            } catch (
//                    InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Release Obj1 " + lockManager.releaseLock(obj1));
//            } catch (
//                    LockingException e) {
//                throw new RuntimeException(e);
//            }
//        });
        try {
        t1.start();
//        t2.start();
        t1.join();
//        t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("User 1, Obj1: " + lockManager.requestLock("user1", obj1));
//        System.out.println("User 2, Obj1: " + lockManager.requestLock("user2", obj1));
//        System.out.println("User 2, Obj2: " + lockManager.requestLock("user2", obj2));
//        System.out.println("User 1, Obj3: " + lockManager.requestLock("user1", obj3));
//        System.out.println("Release Obj1 " + lockManager.releaseLock(obj1));
//        System.out.println("User 2, Obj1: " + lockManager.requestLock("user2", obj1));


    }

}
