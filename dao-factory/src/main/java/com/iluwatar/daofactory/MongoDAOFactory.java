package com.iluwatar.daofactory;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

import java.net.InetSocketAddress;

public class MongoDAOFactory extends DAOFactory {

    /**
     * method to create Mongo connections
     *
     * @return a Connection
     */
    public static Object[] create() {
        final MongoServer server = new MongoServer(new MemoryBackend());
        final InetSocketAddress serverAddress = server.bind();
        final MongoClient client = new MongoClient(new ServerAddress(serverAddress));
        final Object[] clientAndServer = {client, server};
        return clientAndServer;
    }

    /**
     * Override getUserDAO method
     *
     * @return MongoUserDAO
     */
    @Override
    public UserDAO getUserDAO() {
        return new MongoUserDAO();
    }
}