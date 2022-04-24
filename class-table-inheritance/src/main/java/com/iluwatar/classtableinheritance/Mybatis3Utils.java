package com.iluwatar.classtableinheritance;

import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * This is a tool class which use mybatis and mysql .
 *
 * @author ZhangXiZhi
 */
public final class Mybatis3Utils {
  /**
   * constructor.
   */
  private Mybatis3Utils() {
  }

  /**
   * this is a factory field.
   */
  private static  SqlSessionFactory sqlSessionFactory;

  /**
   * getter factory.
   *
   * @return sql factory.
   */
  public static SqlSessionFactory getSqlSessionFactory() {
    return sqlSessionFactory;
  }

  /**
   * It can be extended in the future, it can be thread.
   */
  public static final ThreadLocal<SqlSession> SESSION_THREAD_LOCAL
      = new ThreadLocal<>();

  static {
    try {
      Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
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
      sqlSession = sqlSessionFactory.openSession();
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
