package com.iluwatar.daofactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class AbstractDaoFactoryTest {

  @Test
  void getDAOFactoryTest() {
    var derbyFactory = AbstractDaoFactory.getDAOFactory(AbstractDaoFactory.DERBY);
    assertTrue(derbyFactory instanceof AbstractDaoFactory);
    assertTrue(derbyFactory instanceof DerbyDaoFactory);
  }
}
