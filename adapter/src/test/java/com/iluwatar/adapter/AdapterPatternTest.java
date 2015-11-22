package com.iluwatar.adapter;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * An adapter helps two incompatible interfaces to work together. This is the real world definition
 * for an adapter. Interfaces may be incompatible but the inner functionality should suit the need.
 * The Adapter design pattern allows otherwise incompatible classes to work together by converting
 * the interface of one class into an interface expected by the clients.
 * 
 * <p>There are two variations of the Adapter pattern: 
 * The class adapter implements the adaptee's
 * interface whereas the object adapter uses composition to contain the adaptee in the adapter
 * object. This example uses the object adapter approach.
 * 
 * <p>The Adapter ({@link GnomeEngineer}) converts the interface 
 * of the target class ({@link GoblinGlider}) into a suitable one expected by 
 * the client ({@link GnomeEngineeringManager}
 * ).
 */
public class AdapterPatternTest {

  private Map<String, Object> beans;

  private static final String ENGINEER_BEAN = "engineer";

  private static final String MANAGER_BEAN = "manager";

  /**
   * This method runs before the test execution and sets the bean objects in the beans Map.
   */
  @Before
  public void setup() {
    beans = new HashMap<>();

    GnomeEngineer gnomeEngineer = spy(new GnomeEngineer());
    beans.put(ENGINEER_BEAN, gnomeEngineer);

    GnomeEngineeringManager manager = new GnomeEngineeringManager();
    manager.setEngineer((GnomeEngineer) beans.get(ENGINEER_BEAN));
    beans.put(MANAGER_BEAN, manager);
  }

  /**
   * This test asserts that when we call operateDevice() method on a manager bean, it is internally
   * calling operateDevice method on the engineer object. The Adapter ({@link GnomeEngineer})
   * converts the interface of the target class ( {@link GoblinGlider}) into a suitable one expected
   * by the client ({@link GnomeEngineeringManager} ).
   */
  @Test
  public void testAdapter() {
    Engineer manager = (Engineer) beans.get(MANAGER_BEAN);

    // when manager is asked to operate device
    manager.operateDevice();

    // Manager internally calls the engineer object to operateDevice
    Engineer engineer = (Engineer) beans.get(ENGINEER_BEAN);
    verify(engineer).operateDevice();
  }
}
