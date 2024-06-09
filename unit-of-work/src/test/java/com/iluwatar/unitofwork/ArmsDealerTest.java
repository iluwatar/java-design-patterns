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
package com.iluwatar.unitofwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * tests {@link ArmsDealer}
 */

class ArmsDealerTest {
  private final Weapon weapon1 = new Weapon(1, "battle ram");
  private final Weapon weapon2 = new Weapon(1, "wooden lance");

  private final Map<String, List<Weapon>> context = new HashMap<>();
  private final WeaponDatabase weaponDatabase = mock(WeaponDatabase.class);
  private final ArmsDealer armsDealer = new ArmsDealer(context, weaponDatabase);

  @Test
  void shouldSaveNewStudentWithoutWritingToDb() {
    armsDealer.registerNew(weapon1);
    armsDealer.registerNew(weapon2);

    assertEquals(2, context.get(UnitActions.INSERT.getActionValue()).size());
    verifyNoMoreInteractions(weaponDatabase);
  }

  @Test
  void shouldSaveDeletedStudentWithoutWritingToDb() {
    armsDealer.registerDeleted(weapon1);
    armsDealer.registerDeleted(weapon2);

    assertEquals(2, context.get(UnitActions.DELETE.getActionValue()).size());
    verifyNoMoreInteractions(weaponDatabase);
  }

  @Test
  void shouldSaveModifiedStudentWithoutWritingToDb() {
    armsDealer.registerModified(weapon1);
    armsDealer.registerModified(weapon2);

    assertEquals(2, context.get(UnitActions.MODIFY.getActionValue()).size());
    verifyNoMoreInteractions(weaponDatabase);
  }

  @Test
  void shouldSaveAllLocalChangesToDb() {
    context.put(UnitActions.INSERT.getActionValue(), List.of(weapon1));
    context.put(UnitActions.MODIFY.getActionValue(), List.of(weapon1));
    context.put(UnitActions.DELETE.getActionValue(), List.of(weapon1));

    armsDealer.commit();

    verify(weaponDatabase, times(1)).insert(weapon1);
    verify(weaponDatabase, times(1)).modify(weapon1);
    verify(weaponDatabase, times(1)).delete(weapon1);
  }

  @Test
  void shouldNotWriteToDbIfContextIsNull() {
    var weaponRepository = new ArmsDealer(null, weaponDatabase);

    weaponRepository.commit();

    verifyNoMoreInteractions(weaponDatabase);
  }

  @Test
  void shouldNotWriteToDbIfNothingToCommit() {
    var weaponRepository = new ArmsDealer(new HashMap<>(), weaponDatabase);

    weaponRepository.commit();

    verifyNoMoreInteractions(weaponDatabase);
  }

  @Test
  void shouldNotInsertToDbIfNoRegisteredStudentsToBeCommitted() {
    context.put(UnitActions.MODIFY.getActionValue(), List.of(weapon1));
    context.put(UnitActions.DELETE.getActionValue(), List.of(weapon1));

    armsDealer.commit();

    verify(weaponDatabase, never()).insert(weapon1);
  }

  @Test
  void shouldNotModifyToDbIfNotRegisteredStudentsToBeCommitted() {
    context.put(UnitActions.INSERT.getActionValue(), List.of(weapon1));
    context.put(UnitActions.DELETE.getActionValue(), List.of(weapon1));

    armsDealer.commit();

    verify(weaponDatabase, never()).modify(weapon1);
  }

  @Test
  void shouldNotDeleteFromDbIfNotRegisteredStudentsToBeCommitted() {
    context.put(UnitActions.INSERT.getActionValue(), List.of(weapon1));
    context.put(UnitActions.MODIFY.getActionValue(), List.of(weapon1));

    armsDealer.commit();

    verify(weaponDatabase, never()).delete(weapon1);
  }
}
