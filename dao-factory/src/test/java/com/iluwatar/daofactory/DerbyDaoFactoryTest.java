package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DerbyDaoFactoryTest {

  @Test
  void getUserDAOTest() {
    AbstractDaoFactory derbyFactory = AbstractDaoFactory.getDaoFactory(AbstractDaoFactory.DERBY);
    UserDao userDAO = derbyFactory.getUserDao();
    assertTrue(userDAO instanceof DerbyUserDao);
  }
}
