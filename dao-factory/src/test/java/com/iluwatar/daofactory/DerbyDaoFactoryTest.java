package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DerbyDaoFactoryTest {

  @Test
  void getUserDAOTest() {
    AbstractDaoFactory derbyFactory = AbstractDaoFactory.getDAOFactory(AbstractDaoFactory.DERBY);
    UserDao userDAO = derbyFactory.getUserDAO();
    assertTrue(userDAO instanceof DerbyUserDao);
  }
}
