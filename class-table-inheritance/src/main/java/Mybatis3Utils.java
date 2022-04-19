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
public abstract class Mybatis3Utils {

  public static final SqlSessionFactory sqlSessionFactory;
  public static final ThreadLocal<SqlSession> sessionThread = new ThreadLocal<>();

  static {
    try {
      Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get related session.
   *
   * @return sqlsession can deal with some requests.
   */
  public static SqlSession getCurrentSqlSession() {
    SqlSession sqlSession = sessionThread.get();
    if (Objects.isNull(sqlSession)) {
      sqlSession = sqlSessionFactory.openSession();
      sessionThread.set(sqlSession);
    }
    return sqlSession;
  }

  /**
   * Close the session.
   */
  public static void closeCurrentSession() {
    SqlSession sqlSession = sessionThread.get();
    if (Objects.nonNull(sqlSession)) {
      sqlSession.close();
    }
    sessionThread.set(null);
  }
}
