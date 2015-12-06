package com.iluwatar.bridge;

import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Date: 12/6/15 - 11:26 PM
 *
 * @author Jeroen Meulemeester
 */
public class FlyingMagicWeaponTest extends MagicWeaponTest {

  /**
   * Invoke all possible actions on the weapon and check if the actions are executed on the actual
   * underlying weapon implementation.
   */
  @Test
  public void testMjollnir() throws Exception {
    final Mjollnir mjollnir = spy(new Mjollnir());
    final FlyingMagicWeapon flyingMagicWeapon = new FlyingMagicWeapon(mjollnir);

    testBasicWeaponActions(flyingMagicWeapon, mjollnir);

    flyingMagicWeapon.fly();
    verify(mjollnir, times(1)).flyImp();
    verifyNoMoreInteractions(mjollnir);
  }

}