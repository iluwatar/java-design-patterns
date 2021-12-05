package com.iluwatar.daofactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class AbstractDAOFactoryTest {

  @Test
  void getDAOFactoryTest() {
    var derbyFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.DERBY);
    assertTrue(derbyFactory instanceof AbstractDAOFactory);
    assertTrue(derbyFactory instanceof DerbyDAOFactory);
  }
}
