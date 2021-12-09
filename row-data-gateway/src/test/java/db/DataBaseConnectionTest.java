package db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DataBaseConnectionTest {

	@Mock
	private Connection connection;

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link DataBaseConnection} getConnection
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testGetConnection() throws SQLException {
		DataBaseConnection dataBaseConnection = new DataBaseConnection();
		try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
			mockedDriverManager.when(() -> DriverManager.getConnection(anyString())).thenReturn(connection);
			assertNotNull(dataBaseConnection.getConnection());
		}
	}


	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Test {@link DataBaseConnection} closeConnection
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testCloseConnection() throws SQLException {
		DataBaseConnection dataBaseConnection = new DataBaseConnection();
		dataBaseConnection.closeConnection(connection);
		verify(connection, times(1)).close();
	}
}