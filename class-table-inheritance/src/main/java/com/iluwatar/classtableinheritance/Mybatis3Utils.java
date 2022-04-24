package com.iluwatar.classtableinheritance;

import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * This is tool class.
 *
 * @author ZhangXiZhi
 */
public  class Mybatis3Utils {

  /**
   * this is a factory.
   */
  public static  SqlSessionFactory SQL_SESSION_FACTORY;
  /**
   * It can be extended in the future.
   */
  public static final ThreadLocal<SqlSession> SESSION_THREAD_LOCAL
      = new ThreadLocal<>();

  static {
    try {
      Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
      SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get related session.
   *
   * @return sqlsession can deal with some requests.
   */
  public static SqlSession getCurrentSqlSession() {
    SqlSession sqlSession = SESSION_THREAD_LOCAL.get();
    if (Objects.isNull(sqlSession)) {
      sqlSession = SQL_SESSION_FACTORY.openSession();
      SESSION_THREAD_LOCAL.set(sqlSession);
    }
    return sqlSession;
  }

  /**
   * Close the session.
   */
  public static void closeCurrentSession() {
    SqlSession sqlSession = SESSION_THREAD_LOCAL.get();
    if (Objects.nonNull(sqlSession)) {
      sqlSession.close();
    }
    SESSION_THREAD_LOCAL.set(null);
  }
}
