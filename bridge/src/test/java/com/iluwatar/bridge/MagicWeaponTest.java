/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.bridge;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Date: 12/6/15 - 11:28 PM
 *
 * @author Jeroen Meulemeester
 */
public abstract class MagicWeaponTest {

  /**
   * Invoke the basic actions of the given weapon, and test if the underlying weapon implementation
   * is invoked
   *
   * @param weaponImpl The spied weapon implementation where actions are bridged to
   * @param weapon               The weapon, handled by the app
   */
  protected final void testBasicWeaponActions(final MagicWeapon weapon,
                                              final MagicWeaponImpl weaponImpl) {
    assertNotNull(weapon);
    assertNotNull(weaponImpl);
    assertNotNull(weapon.getImp());

    weapon.swing();
    verify(weaponImpl, times(1)).swingImp();
    verifyNoMoreInteractions(weaponImpl);

    weapon.wield();
    verify(weaponImpl, times(1)).wieldImp();
    verifyNoMoreInteractions(weaponImpl);

    weapon.unwield();
    verify(weaponImpl, times(1)).unwieldImp();
    verifyNoMoreInteractions(weaponImpl);

  }

}
