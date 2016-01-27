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

/**
 * 
 * SoulEatingMagicWeapon
 *
 */
public class SoulEatingMagicWeapon extends MagicWeapon {

  public SoulEatingMagicWeapon(SoulEatingMagicWeaponImpl imp) {
    super(imp);
  }

  @Override
  public SoulEatingMagicWeaponImpl getImp() {
    return (SoulEatingMagicWeaponImpl) imp;
  }

  @Override
  public void wield() {
    getImp().wieldImp();
  }

  @Override
  public void swing() {
    getImp().swingImp();
  }

  @Override
  public void unwield() {
    getImp().unwieldImp();
  }

  public void eatSoul() {
    getImp().eatSoulImp();
  }

}
