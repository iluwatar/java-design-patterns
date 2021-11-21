package ParrotDataModel;

import ParrotRowGateWay.ParrotTypeRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OwnedParrotTest {

	@InjectMocks
	private OwnedParrot injectOwnedParrot;

	@Test
	public void testFields() {

		OwnedParrot myNewParrot = new OwnedParrot();
		myNewParrot.setOwnedParrotId(1);
		myNewParrot.setParrotTypeId(1);
		myNewParrot.setParrotName("Mikey");
		myNewParrot.setParrotAge(10);
		myNewParrot.setColor("Blue and Gold");
		myNewParrot.setTamed(true);

		assertEquals(Integer.valueOf(1), myNewParrot.getOwnedParrotId());
		assertEquals(Integer.valueOf(1), myNewParrot.getParrotTypeId());
		assertEquals("Mikey", myNewParrot.getParrotName());
		assertEquals(Integer.valueOf(10), myNewParrot.getParrotAge());
		assertEquals("Blue and Gold", myNewParrot.getColor());
		assertEquals(true, myNewParrot.getTamed());
	}

	@Test
	public void testPrintParrotInformation() throws SQLException {
		try (MockedStatic<ParrotTypeRegistry> mockedParrotTypeRegistry = Mockito.mockStatic(ParrotTypeRegistry.class)) {
			ParrotType parrotType = new ParrotType(1, "macaw");
			mockedParrotTypeRegistry.when(() -> ParrotTypeRegistry.getParrotTypeById(1)).thenReturn(parrotType);
			injectOwnedParrot.printParrotInformation();
			assertEquals(parrotType, ParrotTypeRegistry.getParrotTypeById(1));
		}
	}

	@Test(expected = SQLException.class)
	public void testPrintParrotInformationWithException() throws SQLException {
		try (MockedStatic<ParrotTypeRegistry> mockedParrotTypeRegistry = Mockito.mockStatic(ParrotTypeRegistry.class)) {
			mockedParrotTypeRegistry.when(() -> ParrotTypeRegistry.getParrotTypeById(1)).thenThrow(new SQLException());
			injectOwnedParrot.printParrotInformation();
			ParrotTypeRegistry.getParrotTypeById(1);
		}
	}
}