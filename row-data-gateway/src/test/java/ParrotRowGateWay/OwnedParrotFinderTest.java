package ParrotRowGateWay;

import db.DataBaseConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OwnedParrotFinderTest {

	@Mock
	private DataBaseConnection dataBaseConnection;

	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement statement;

	@Mock
	private ResultSet resultSet;

	private OwnedParrotFinder ownedParrotFinder;

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Setting up the test requirements.
	 */
	@Before
	public void setUp() {
		ownedParrotFinder = new OwnedParrotFinder();
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Negative test {@link OwnedParrotFinder} findById for SQLException
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test(expected = SQLException.class)
	public void findByIdWithExceptionTest() throws SQLException {
		ownedParrotFinder.findById(2);
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link OwnedParrotFinder} findById for Null object
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void findByIdWithNoExceptionAndReturnNULLTest() throws SQLException {
		ownedParrotFinder = new OwnedParrotFinder(dataBaseConnection);
		when(dataBaseConnection.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		assertNull(ownedParrotFinder.findById(2));
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link OwnedParrotFinder} findById
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void findByIdWithReturnObjectTest() throws SQLException {
		ownedParrotFinder = new OwnedParrotFinder(dataBaseConnection);
		when(dataBaseConnection.getConnection()).thenReturn(connection);
		when(resultSet.getInt(OwnedParrotFinder.OWNED_PARROT_ID)).thenReturn(1);
		when(resultSet.getInt(OwnedParrotFinder.PARROT_TYPE_ID)).thenReturn(1);
		when(resultSet.getString(OwnedParrotFinder.PARROT_NAME)).thenReturn("Mikey");
		when(resultSet.getInt(OwnedParrotFinder.PARROT_AGE)).thenReturn(10);
		when(resultSet.getString(OwnedParrotFinder.COLOR)).thenReturn("Blue and Gold");
		when(resultSet.getBoolean(OwnedParrotFinder.TAMED)).thenReturn(true);
		when(resultSet.next()).thenReturn(true);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		assertNotNull(ownedParrotFinder.findById(2));
		assertEquals(ownedParrotFinder.findById(2).getParrotName(), "Mikey");
	}


	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link OwnedParrotFinder} findAll
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void findAllWithReturnObjectTest() throws SQLException {
		ownedParrotFinder = new OwnedParrotFinder(dataBaseConnection);
		when(dataBaseConnection.getConnection()).thenReturn(connection);
		when(resultSet.getInt("count")).thenReturn(1);
		when(resultSet.getInt(OwnedParrotFinder.OWNED_PARROT_ID)).thenReturn(1);
		when(resultSet.getInt(OwnedParrotFinder.PARROT_TYPE_ID)).thenReturn(1);
		when(resultSet.getString(OwnedParrotFinder.PARROT_NAME)).thenReturn("Mikey");
		when(resultSet.getInt(OwnedParrotFinder.PARROT_AGE)).thenReturn(10);
		when(resultSet.getString(OwnedParrotFinder.COLOR)).thenReturn("Blue and Gold");
		when(resultSet.getBoolean(OwnedParrotFinder.TAMED)).thenReturn(true);
		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		List<OwnedParrotGateWay> allParrots = ownedParrotFinder.findAll();
		assertNotNull(allParrots);
	}

}