package iluwater.com.daofactory;
import java.net.InetSocketAddress;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
public class MongoDAOFactory extends DAOFactory {

    public static Object[] create() {
        MongoServer server = new MongoServer(new MemoryBackend());
        InetSocketAddress serverAddress = server.bind();
        MongoClient client = new MongoClient(new ServerAddress(serverAddress));
        Object[] clientAndServer = {client, server};
        return clientAndServer;
    }
    @Override
    public UserDAO getUserDAO() {
        return new MongoUserDAO();
    }
}