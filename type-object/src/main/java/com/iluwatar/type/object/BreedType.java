/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.type.object;

/**
 * 
 * Breed type enumeration
 * 
 * @author Imp92
 */
public enum BreedType {
  TROLL("Troll"),
  TROLL_ARCHER("Troll Archer"),
  TROLL_WIZARD("Troll Wizard"),
  DRAGON("Dragon"),
  DRAGON_WARRIOR("Dragon Warrior"),
  DRAGON_KNIGHT("Dragon Knight"),
  GIANT("Giant"),
  GIANT_CYCLOPS("Cyclops"),
  GIANT_CHIMERA("Chimera"),
  BEAST("Beast"),
  BEAST_HUNTER("Beast Hunter");
  
  private String title;
  
  BreedType(String title) {
    this.title = title;
  }
  
  @Override
  public String toString() {
    return title;
  }
}
