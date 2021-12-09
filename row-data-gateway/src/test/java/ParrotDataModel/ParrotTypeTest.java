package ParrotDataModel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParrotTypeTest {

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests getter of {@link ParrotType}
	 */
	@Test
	public void testParrotTypeFields() {
		ParrotType parrotType = new ParrotType(1, "Blue-Throated");
		assertEquals(Integer.valueOf(1), parrotType.getParrotTypeId());
		assertEquals("Blue-Throated", parrotType.getSpecies());
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests setters of {@link ParrotType}
	 */
	@Test
	public void testParrotTypeFieldSetters() {
		ParrotType parrotType = new ParrotType(1, "Blue-Throated");
		parrotType.setParrotTypeId(2);
		parrotType.setSpecies("Hyacinth");
		assertEquals(Integer.valueOf(2), parrotType.getParrotTypeId());
		assertEquals("Hyacinth", parrotType.getSpecies());
	}
}