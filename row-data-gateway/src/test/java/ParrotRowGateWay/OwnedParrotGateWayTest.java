package ParrotRowGateWay;

import ParrotDataModel.OwnedParrot;
import db.DataBaseConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OwnedParrotGateWayTest {

	@Mock
	OwnedParrot ownedParrot;

	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement statement;

	@Mock
	private ResultSet resultSet;

	@Mock
	private DataBaseConnection dataBaseConnection;


	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link OwnedParrotGateWay} all fields
	 */
	@Test
	public void testFields() {
		OwnedParrot ownedParrot = new OwnedParrot();
		ownedParrot.setOwnedParrotId(1);
		ownedParrot.setParrotTypeId(1);
		ownedParrot.setParrotName("Mikey");
		ownedParrot.setParrotAge(10);
		ownedParrot.setColor("Blue and Gold");
		ownedParrot.setTamed(true);
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(ownedParrot);
		assertEquals(Integer.valueOf(1), ownedParrotGateWay.getOwnedParrotId());
		assertEquals(Integer.valueOf(1), ownedParrotGateWay.getParrotTypeId());
		assertEquals("Mikey", ownedParrotGateWay.getParrotName());
		assertEquals(Integer.valueOf(10), ownedParrotGateWay.getParrotAge());
		assertEquals("Blue and Gold", ownedParrotGateWay.getColor());
		assertEquals(true, ownedParrotGateWay.getTamed());
	}


	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link OwnedParrotGateWay} overloaded constructor
	 */
	@Test
	public void testFieldsOverloadedConstructor() {
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(1, 1, "Mikey", 10, "Blue and Gold", false);
		assertEquals(Integer.valueOf(1), ownedParrotGateWay.getOwnedParrotId());
		assertEquals(Integer.valueOf(1), ownedParrotGateWay.getParrotTypeId());
		assertEquals("Mikey", ownedParrotGateWay.getParrotName());
		assertEquals(Integer.valueOf(10), ownedParrotGateWay.getParrotAge());
		assertEquals("Blue and Gold", ownedParrotGateWay.getColor());
		assertEquals(false, ownedParrotGateWay.getTamed());
	}


	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link OwnedParrotGateWay} insert
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testInsert() throws SQLException {
		when(dataBaseConnection.getConnection()).thenReturn(connection);
		when(statement.executeUpdate()).thenReturn(1);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(ownedParrot.getParrotName()).thenReturn("Mikey");
		when(ownedParrot.getParrotAge()).thenReturn(10);
		when(ownedParrot.getColor()).thenReturn("Blue and Gold");
		when(ownedParrot.getTamed()).thenReturn(true);
		when(ownedParrot.getParrotTypeId()).thenReturn(1);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt(anyString())).thenReturn(1);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(dataBaseConnection, ownedParrot);
		ownedParrotGateWay.insert();
		verify(resultSet, times(1)).getInt("OwnedParrotId");
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Negative test {@link OwnedParrotGateWay} insert for SQLException
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test(expected = SQLException.class)
	public void testInsertException() throws SQLException {
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(ownedParrot);
		ownedParrotGateWay.insert();
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Negative test {@link OwnedParrotGateWay} insert for no records
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testInsertZero() throws SQLException {
		when(dataBaseConnection.getConnection()).thenReturn(connection);
		when(statement.executeUpdate()).thenReturn(0);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(ownedParrot.getParrotName()).thenReturn("Mikey");
		when(ownedParrot.getParrotAge()).thenReturn(10);
		when(ownedParrot.getColor()).thenReturn("Blue and Gold");
		when(ownedParrot.getTamed()).thenReturn(true);
		when(ownedParrot.getParrotTypeId()).thenReturn(1);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt(anyString())).thenReturn(1);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(dataBaseConnection, ownedParrot);
		ownedParrotGateWay.insert();
		verify(resultSet, times(1)).getInt("OwnedParrotId");
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link OwnedParrotGateWay} update
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testUpdate() throws SQLException {
		when(dataBaseConnection.getConnection()).thenReturn(connection);
		when(statement.executeUpdate()).thenReturn(1);
		when(ownedParrot.getParrotName()).thenReturn("Mikey");
		when(ownedParrot.getParrotAge()).thenReturn(10);
		when(ownedParrot.getColor()).thenReturn("Blue and Gold");
		when(ownedParrot.getTamed()).thenReturn(true);
		when(ownedParrot.getParrotTypeId()).thenReturn(1);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(dataBaseConnection, ownedParrot);
		ownedParrotGateWay.update();
		verify(statement, times(1)).executeUpdate();
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Negative test {@link OwnedParrotGateWay} update for no records
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testUpdateZero() throws SQLException {
		when(dataBaseConnection.getConnection()).thenReturn(connection);
		when(statement.executeUpdate()).thenReturn(0);
		when(ownedParrot.getParrotName()).thenReturn("Mikey");
		when(ownedParrot.getParrotAge()).thenReturn(10);
		when(ownedParrot.getColor()).thenReturn("Blue and Gold");
		when(ownedParrot.getTamed()).thenReturn(true);
		when(ownedParrot.getParrotTypeId()).thenReturn(1);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(dataBaseConnection, ownedParrot);
		ownedParrotGateWay.update();
		verify(statement, times(1)).executeUpdate();
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Negative test {@link OwnedParrotGateWay} update for SQLException
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test(expected = SQLException.class)
	public void testUpdateException() throws SQLException {
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(ownedParrot);
		ownedParrotGateWay.update();
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link OwnedParrotGateWay} delete
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testDelete() throws SQLException {
		when(dataBaseConnection.getConnection()).thenReturn(connection);
		when(statement.executeUpdate()).thenReturn(1);
		when(ownedParrot.getParrotName()).thenReturn("Mikey");
		when(ownedParrot.getParrotAge()).thenReturn(10);
		when(ownedParrot.getColor()).thenReturn("Blue and Gold");
		when(ownedParrot.getTamed()).thenReturn(true);
		when(ownedParrot.getParrotTypeId()).thenReturn(1);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(dataBaseConnection, ownedParrot);
		ownedParrotGateWay.delete();
		verify(statement, times(1)).executeUpdate();
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Negative test {@link OwnedParrotGateWay} delete for no records
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testDeleteZero() throws SQLException {
		when(dataBaseConnection.getConnection()).thenReturn(connection);
		when(statement.executeUpdate()).thenReturn(0);
		when(ownedParrot.getParrotName()).thenReturn("Mikey");
		when(ownedParrot.getParrotAge()).thenReturn(10);
		when(ownedParrot.getColor()).thenReturn("Blue and Gold");
		when(ownedParrot.getTamed()).thenReturn(true);
		when(ownedParrot.getParrotTypeId()).thenReturn(1);
		when(connection.prepareStatement(any(String.class))).thenReturn(statement);
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(dataBaseConnection, ownedParrot);
		ownedParrotGateWay.delete();
		verify(statement, times(1)).executeUpdate();
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Negative test {@link OwnedParrotGateWay} delete for SQLException
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test(expected = SQLException.class)
	public void testDeleteException() throws SQLException {
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(ownedParrot);
		ownedParrotGateWay.delete();
	}
}