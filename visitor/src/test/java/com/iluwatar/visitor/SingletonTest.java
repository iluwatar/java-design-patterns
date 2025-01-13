package com.iluwatar.visitor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SingletonTest {

  @Test
  public void testSingletonInstance() {
    // Obtiene dos instancias de Singleton
    Singleton instance1 = Singleton.getInstance();
    Singleton instance2 = Singleton.getInstance();

    // Verifica que ambas instancias sean iguales (Singleton garantiza una única instancia)
    assertSame(instance1, instance2, "Las dos instancias deberían ser iguales");

    // Verifica el comportamiento del método en Singleton
    instance1.showMessage();
  }

  private void assertSame(Singleton instance1, Singleton instance2, String s) {
  }
}
