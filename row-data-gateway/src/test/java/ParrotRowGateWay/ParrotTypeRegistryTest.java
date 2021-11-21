package ParrotRowGateWay;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ParrotTypeRegistryTest {

	@Test(expected = SQLException.class)
	public void getParrotTypeById() throws SQLException {
		ParrotTypeRegistry.getParrotTypeById(1);

	}

	@Test
	public void printParrotTypeInformation() {
		ParrotTypeRegistry.printParrotTypeInformation();
	}
}