package com.iluwatar.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 * This case is Just for test the Annotation Based configuration
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AppConfigTest {

  @Autowired
  DataSource dataSource;

  /**
   * Test for bean instance
   */
  @Test
  public void testDataSource() {
    assertNotNull(dataSource);
  }

  /**
   * Test for correct query execution
   */
  @Test
  @Transactional
  public void testQuery() throws SQLException {
    ResultSet resultSet = dataSource.getConnection().createStatement().executeQuery("SELECT 1");
    String result = null;
    String expected = "1";
    while (resultSet.next()) {
      result = resultSet.getString(1);

    }
    assertTrue(result.equals(expected));
  }

}
