package com.iluwater.coarsegrainedlock.utils;

import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


@Slf4j
public final class MybatisUtils {

  public static final ThreadLocal<SqlSession> LOCAL = new ThreadLocal<>();

  private static  SqlSessionFactory sqlSessionFactory;

  public static SqlSessionFactory getSqlSessionFactory() {
    return sqlSessionFactory;
  }

  static {
    try {
      Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    } catch (IOException e) {
      LOGGER.info("mybatis import error");
    }
  }


  public static SqlSession getCurrentSqlSession() {
    SqlSession sqlSession = LOCAL.get();
    if (Objects.isNull(sqlSession)) {
      sqlSession = sqlSessionFactory.openSession();
      LOCAL.set(sqlSession);
    }
    return sqlSession;
  }


  public static void closeCurrentSession() {
    SqlSession sqlSession = LOCAL.get();
    if (Objects.nonNull(sqlSession)) {
      sqlSession.close();
    }
    LOCAL.set(null);
  }
}