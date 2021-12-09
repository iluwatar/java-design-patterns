package ParrotRowGateWay;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParrotRegistryTest {

	@Mock
	private OwnedParrotGateWay ownedParrotGateWay;

	@Mock
	private HashMap<Integer, OwnedParrotGateWay> parrotRegistry;

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link ParrotRegistry} getParrotRegistrySize
	 */
	@Test
	public void getParrotRegistrySize() {
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(1, 1, "Name", 2, "Red", false);
		OwnedParrotGateWay ownedParrotGateWay2 = new OwnedParrotGateWay(2, 2, "Name2", 5, "Blue", true);
		ParrotRegistry.addOwnedParrot(ownedParrotGateWay);
		ParrotRegistry.addOwnedParrot(ownedParrotGateWay2);
		assertEquals(2, ParrotRegistry.getParrotRegistrySize());
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link ParrotRegistry} getAllOwnedParrotsInRegistry
	 */
	@Test
	public void getAllOwnedParrotsInRegistry() {
		OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(1, 1, "Name", 2, "Red", false);
		ParrotRegistry.addOwnedParrot(ownedParrotGateWay);
		assertEquals(1, ParrotRegistry.getAllOwnedParrotsInRegistry().size());
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link ParrotRegistry} addOwnedParrot
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testAddOwnedParrot() throws Exception {
		setFinalStatic(ParrotRegistry.class.getDeclaredField("parrotRegistry"), parrotRegistry);
		ParrotRegistry.addOwnedParrot(ownedParrotGateWay);
		verify(parrotRegistry, times(1)).put(ownedParrotGateWay.getOwnedParrotId(), ownedParrotGateWay);
		verify(ownedParrotGateWay, times(3)).getOwnedParrotId();
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link ParrotRegistry} removeOwnedParrot
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testRemoveOwnedParrot() throws Exception {
		setFinalStatic(ParrotRegistry.class.getDeclaredField("parrotRegistry"), parrotRegistry);
		ParrotRegistry.removeOwnedParrot(ownedParrotGateWay);
		verify(parrotRegistry, times(1)).remove(ownedParrotGateWay.getOwnedParrotId());
		verify(ownedParrotGateWay, times(3)).getOwnedParrotId();
	}


	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link ParrotRegistry} updateOwnedParrot
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testUpdateOwnedParrot() throws Exception {
		setFinalStatic(ParrotRegistry.class.getDeclaredField("parrotRegistry"), parrotRegistry);
		when(parrotRegistry.containsKey(anyInt())).thenReturn(Boolean.TRUE);
		ParrotRegistry.updateOwnedParrot(ownedParrotGateWay);
		verify(parrotRegistry, times(1)).put(ownedParrotGateWay.getOwnedParrotId(), ownedParrotGateWay);
		verify(ownedParrotGateWay, times(3)).getOwnedParrotId();
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link ParrotRegistry} getOwnedParrot by ID
	 *
	 * @throws SQLException If any error occurs
	 */
	@Test
	public void testGetOwnedParrot() throws Exception {
		setFinalStatic(ParrotRegistry.class.getDeclaredField("parrotRegistry"), parrotRegistry);
		when(parrotRegistry.containsKey(anyInt())).thenReturn(Boolean.TRUE);
		when(parrotRegistry.get(anyInt())).thenReturn(ownedParrotGateWay);
		OwnedParrotGateWay ownedParrot = ParrotRegistry.getOwnedParrot(1);
		assertNotNull(ownedParrot);
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Tests {@link ParrotRegistry} getParrotRegistrySize with empty
	 */
	@Test
	public void testGetParrotRegistryEmptySize() {
		assertEquals(0, ParrotRegistry.getParrotRegistrySize());
	}

	/**
	 * CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1312
	 * Helper method for setting final statics
	 *
	 * @throws Exception If any error occurs
	 */
	public static void setFinalStatic(Field field, Object newValue) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, newValue);
	}
}