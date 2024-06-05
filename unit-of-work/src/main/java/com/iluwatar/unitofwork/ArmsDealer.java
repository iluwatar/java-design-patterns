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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link ArmsDealer} Weapon repository that supports unit of work for weapons.
 */
@Slf4j
@RequiredArgsConstructor
public class ArmsDealer implements UnitOfWork<Weapon> {

  private final Map<String, List<Weapon>> context;
  private final WeaponDatabase weaponDatabase;

  @Override
  public void registerNew(Weapon weapon) {
    LOGGER.info("Registering {} for insert in context.", weapon.getName());
    register(weapon, UnitActions.INSERT.getActionValue());
  }

  @Override
  public void registerModified(Weapon weapon) {
    LOGGER.info("Registering {} for modify in context.", weapon.getName());
    register(weapon, UnitActions.MODIFY.getActionValue());

  }

  @Override
  public void registerDeleted(Weapon weapon) {
    LOGGER.info("Registering {} for delete in context.", weapon.getName());
    register(weapon, UnitActions.DELETE.getActionValue());
  }

  private void register(Weapon weapon, String operation) {
    var weaponsToOperate = context.get(operation);
    if (weaponsToOperate == null) {
      weaponsToOperate = new ArrayList<>();
    }
    weaponsToOperate.add(weapon);
    context.put(operation, weaponsToOperate);
  }

  /**
   * All UnitOfWork operations are batched and executed together on commit only.
   */
  @Override
  public void commit() {
    if (context == null || context.isEmpty()) {
      return;
    }
    LOGGER.info("Commit started");
    if (context.containsKey(UnitActions.INSERT.getActionValue())) {
      commitInsert();
    }

    if (context.containsKey(UnitActions.MODIFY.getActionValue())) {
      commitModify();
    }
    if (context.containsKey(UnitActions.DELETE.getActionValue())) {
      commitDelete();
    }
    LOGGER.info("Commit finished.");
  }

  private void commitInsert() {
    var weaponsToBeInserted = context.get(UnitActions.INSERT.getActionValue());
    for (var weapon : weaponsToBeInserted) {
      LOGGER.info("Inserting a new weapon {} to sales rack.", weapon.getName());
      weaponDatabase.insert(weapon);
    }
  }

  private void commitModify() {
    var modifiedWeapons = context.get(UnitActions.MODIFY.getActionValue());
    for (var weapon : modifiedWeapons) {
      LOGGER.info("Scheduling {} for modification work.", weapon.getName());
      weaponDatabase.modify(weapon);
    }
  }

  private void commitDelete() {
    var deletedWeapons = context.get(UnitActions.DELETE.getActionValue());
    for (var weapon : deletedWeapons) {
      LOGGER.info("Scrapping {}.", weapon.getName());
      weaponDatabase.delete(weapon);
    }
  }
}
