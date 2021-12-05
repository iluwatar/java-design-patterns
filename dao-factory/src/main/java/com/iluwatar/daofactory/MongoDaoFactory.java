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
   * @return a Connection
   */
  public static Object[] create() {
    final MongoServer server = new MongoServer(new MemoryBackend());
    final InetSocketAddress serverAddress = server.bind();
    final MongoClient client = new MongoClient(new ServerAddress(serverAddress));
    return new Object[]{client, server};
  }

  /**
   * Override getUserDAO method.
   *
   * @return MongoUserDAO
   */
  @Override
  public UserDao getUserDAO() {
    return new MongoUserDao();
  }
}