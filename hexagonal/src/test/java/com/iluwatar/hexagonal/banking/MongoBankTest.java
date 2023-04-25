package com.iluwatar.hexagonal.banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests for Mongo banking adapter
 */
class MongoBankTest {

  private static final String TEST_DB = "lotteryDBTest";
  private static final String TEST_ACCOUNTS_COLLECTION = "testAccounts";
  private static final String LOCALHOST = "localhost";
  private static final int MONGO_TEST_PORT = 27017;

  private static MongodExecutable mongodExe;
  private static MongodProcess mongod;
  private static MongoClient mongoClient;
  private static MongoDatabase mongoDatabase;

  private MongoBank mongoBank;

  @BeforeAll
  static void setUp() throws Exception {
    MongodStarter starter = MongodStarter.getDefaultInstance();
    MongodConfig mongodConfig = MongodConfig.builder()
            .version(Version.Main.PRODUCTION)
            .net(new de.flapdoodle.embed.mongo.config.Net(LOCALHOST, MONGO_TEST_PORT, true))
            .build();
    mongodExe = starter.prepare(mongodConfig);
    mongod = mongodExe.start();
    mongoClient = MongoClients.create(
            MongoClientSettings.builder()
                    .applyToClusterSettings(builder -> builder.hosts(List.of(new ServerAddress(LOCALHOST, MONGO_TEST_PORT))))
                    .build());
    mongoDatabase = mongoClient.getDatabase(TEST_DB);
  }

  @BeforeEach
  void init() {
    System.setProperty("mongo-host", LOCALHOST);
    System.setProperty("mongo-port", String.valueOf(MONGO_TEST_PORT));
    mongoDatabase.drop();
    mongoBank = new MongoBank(mongoDatabase.getName(), TEST_ACCOUNTS_COLLECTION);
  }

  @AfterAll
  static void tearDown() {
    mongoClient.close();
    mongod.stop();
    mongodExe.stop();
  }

  @Test
  void testSetup() {
    assertEquals(0, mongoBank.getAccountsCollection().countDocuments());
  }

  @Test
  void testFundTransfers() {
    assertEquals(0, mongoBank.getFunds("000-000"));
    mongoBank.setFunds("000-000", 10);
    assertEquals(10, mongoBank.getFunds("000-000"));
    assertEquals(0, mongoBank.getFunds("111-111"));
    mongoBank.transferFunds(9, "000-000", "111-111");
    assertEquals(1, mongoBank.getFunds("000-000"));
    assertEquals(9, mongoBank.getFunds("111-111"));
  }
}
