package ParrotDataModel;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParrotTypeTest {

	@Test
	public void testParrotTypeFields(){
		ParrotType parrotType = new ParrotType(1, "Blue-Throated");
		assertEquals(Integer.valueOf(1), parrotType.getParrotTypeId());
		assertEquals("Blue-Throated", parrotType.getSpecies());
	}

	@Test
	public void testParrotTypeFieldSetters(){
		ParrotType parrotType = new ParrotType(1, "Blue-Throated");
		parrotType.setParrotTypeId(2);
		parrotType.setSpecies("Hyacinth");
		assertEquals(Integer.valueOf(2), parrotType.getParrotTypeId());
		assertEquals("Hyacinth", parrotType.getSpecies());
	}
}