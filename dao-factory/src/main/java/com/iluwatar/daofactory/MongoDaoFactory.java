package com.iluwatar.daofactory;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

import java.net.InetSocketAddress;

/**
 * This concrete factory extends DAOFactory.
 */
public class MongoDaoFactory extends AbstractDaoFactory {

  /**
   * Instantiates a MongoDAOFactory.
   */
  public MongoDaoFactory() {
    super();
  }

  /**
   * method to create Mongo connections.
   *
   * @return an Object array containing the MongoServer and the InetSocketAddress it is bound to
   */
  public static Object[] create() {
    final MongoServer server = new MongoServer(new MemoryBackend());
    final InetSocketAddress serverAddress = server.bind();
    return new Object[]{server, serverAddress};
  }

  /**
   * Override getUserDAO method.
   *
   * @return MongoUserDAO
   */
  @Override
  public UserDao getUserDao() {
    return new MongoUserDao();
  }
}