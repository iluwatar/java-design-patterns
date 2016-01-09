package com.iluwatar.servicelayer.app;

import com.iluwatar.servicelayer.hibernate.HibernateUtil;

import org.junit.After;
import org.junit.Test;

/**
 * 
 * Application test
 *
 */
public class AppTest {

  @Test
  public void test() {
    String[] args = {};
    App.main(args);
  }

  @After
  public void tearDown() throws Exception {
    HibernateUtil.dropSession();
  }

}
