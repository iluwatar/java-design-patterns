package iluwater.com.daofactory;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoDAOFactoryTest {
    @Test
    void getUserDAOTest() {
        DAOFactory mongoFactory = DAOFactory.getDAOFactory(DAOFactory.MONGO);
        UserDAO userDAO = mongoFactory.getUserDAO();
        assertTrue(userDAO instanceof MongoUserDAO);
    }
}