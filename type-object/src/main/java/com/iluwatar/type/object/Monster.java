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
 * Monster
 * @author JAPO
 *
 */
public abstract class Monster implements Damage {
  protected int health;
  protected String attack;
  protected String name;
  
  public Monster(String name, int health) {
    this.health = health;
    this.name = name;
  }
  
  public int getHealth() {
    return health;
  }
  
  public int setHealth(int health) {
    return this.health = health;
  }
  
  public String getAttack(String attack) {
    return attack;
  }
  
  public String setAttack(String attack) {
    return this.attack = attack;
  }
  
  public String getName() {
    return name;
  }
  
  public String setName(String name) {
    return this.name = name;
  }
  
  /**
   *
   *Implements Damage.
   */
  @Override
  public void damage(int a) {
    health -= a;
    if (health <= 0) {
      System.out.println(name + " died.");
    } else {
      System.out.println(name + " damaged. " + "Health: " +  health);
    }
  }
    
}