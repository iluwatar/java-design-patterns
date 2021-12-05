package com.iluwatar.daofactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoDaoFactoryTest {
  @Test
  void getUserDAOTest() {
    AbstractDaoFactory mongoFactory = AbstractDaoFactory.getDaoFactory(AbstractDaoFactory.MONGO);
    UserDao userDAO = mongoFactory.getUserDao();
    assertTrue(userDAO instanceof MongoUserDao);
  }
}