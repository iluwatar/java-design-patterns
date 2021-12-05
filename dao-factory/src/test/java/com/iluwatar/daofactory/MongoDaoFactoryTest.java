package com.iluwatar.daofactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoDaoFactoryTest {
  @Test
  void getUserDAOTest() {
    AbstractDaoFactory mongoFactory = AbstractDaoFactory.getDAOFactory(AbstractDaoFactory.MONGO);
    UserDao userDAO = mongoFactory.getUserDAO();
    assertTrue(userDAO instanceof MongoUserDao);
  }
}