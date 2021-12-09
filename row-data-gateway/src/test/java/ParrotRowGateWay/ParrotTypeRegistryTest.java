package ParrotRowGateWay;

import org.junit.Test;

import java.sql.SQLException;

public class ParrotTypeRegistryTest {

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Negative test {@link ParrotTypeRegistry} getParrotTypeById for SQLException
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test(expected = SQLException.class)
	public void getParrotTypeById() throws SQLException {
		ParrotTypeRegistry.getParrotTypeById(1);

	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link ParrotTypeRegistry} printParrotTypeInformation
	 */
	@Test
	public void printParrotTypeInformation() {
		ParrotTypeRegistry.printParrotTypeInformation();
	}
}