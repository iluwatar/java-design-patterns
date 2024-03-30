/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.iluwatar.health.check.HealthCheck;
import com.iluwatar.health.check.HealthCheckRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests class for {@link HealthCheckRepository}.
 *
 * @author ydoksanbir
 */
@ExtendWith(MockitoExtension.class)
class HealthCheckRepositoryTest {

  /** Mocked EntityManager instance. */
  @Mock private EntityManager entityManager;

  /** `HealthCheckRepository` instance to be tested. */
  @InjectMocks private HealthCheckRepository healthCheckRepository;

  /**
   * Test case for the `performTestTransaction()` method.
   *
   * <p>Asserts that when the `performTestTransaction()` method is called, it successfully executes
   * a test transaction.
   */
  @Test
  void whenCheckHealth_thenReturnsOne() {
    // Arrange
    Query mockedQuery = mock(Query.class);
    when(entityManager.createNativeQuery("SELECT 1")).thenReturn(mockedQuery);
    when(mockedQuery.getSingleResult()).thenReturn(1);

    // Act
    Integer healthCheckResult = healthCheckRepository.checkHealth();

    // Assert
    assertNotNull(healthCheckResult);
    assertEquals(1, healthCheckResult);
  }

  /**
   * Test case for the `performTestTransaction()` method.
   *
   * <p>Asserts that when the `performTestTransaction()` method is called, it successfully executes
   * a test transaction.
   */
  @Test
  void whenPerformTestTransaction_thenSucceeds() {
    // Arrange
    HealthCheck healthCheck = new HealthCheck();
    healthCheck.setStatus("OK");

    // Mocking the necessary EntityManager behaviors
    when(entityManager.find(eq(HealthCheck.class), any())).thenReturn(healthCheck);

    // Act & Assert
    assertDoesNotThrow(() -> healthCheckRepository.performTestTransaction());

    // Verify the interactions
    verify(entityManager).persist(any(HealthCheck.class));
    verify(entityManager).flush();
    verify(entityManager).remove(any(HealthCheck.class));
  }

  /**
   * Test case for the `checkHealth()` method when the database is down.
   *
   * <p>Asserts that when the `checkHealth()` method is called and the database is down, it throws a
   * RuntimeException.
   */
  @Test
  void whenCheckHealth_andDatabaseIsDown_thenThrowsException() {
    // Arrange
    when(entityManager.createNativeQuery("SELECT 1")).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> healthCheckRepository.checkHealth());
  }

  /**
   * Test case for the `performTestTransaction()` method when the persist operation fails.
   *
   * <p>Asserts that when the `performTestTransaction()` method is called and the persist operation
   * fails, it throws a RuntimeException.
   */
  @Test
  void whenPerformTestTransaction_andPersistFails_thenThrowsException() {
    // Arrange
    doThrow(new RuntimeException()).when(entityManager).persist(any(HealthCheck.class));

    // Act & Assert
    assertThrows(RuntimeException.class, () -> healthCheckRepository.performTestTransaction());

    // Verify that remove is not called if persist fails
    verify(entityManager, never()).remove(any(HealthCheck.class));
  }
}
