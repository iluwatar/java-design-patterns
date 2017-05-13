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
 * Breed class
 * 
 * @author Imp92
 */
public class Breed {
  
  private BreedType type;
  
  private int health;
  
  private String attack;
  
  private Breed parent = null;
  
  /**
   * 
   * constructor
   */
  public Breed(BreedType type, int health, String attack) {
    setHealth(health);
    setAttack(attack);
    setType(type);
  }
  
  /**
   * 
   * constructor
   * to allow a breed to have a parent breed
   */
  public Breed(BreedType type, Breed parent, int health, String attack) {
    setParent(parent);
    setHealth(health);
    setAttack(attack);
    setType(type);
  }
  
  /**
   * 
   * get health
   */
  public int getHealth() {
    if ( getParent() != null && health == 0 ) {
    //Inherit when the health's value is 0 but the parent exist
      return parent.getHealth();
    }
    return health;
  }
  
  /**
   * 
   * set health
   */
  private void setHealth(int health) {
    this.health = health;
  }
  
  /**
   * 
   * get attack
   */
  public String getAttack() {
    if ( getParent() != null && attack == null ) {
    //Inherit when the attack's value is null character but the parent exist
      return parent.getAttack();
    }
    return attack;
  }

  private void setAttack(String attack) {
    this.attack = attack;
  }
  
  public Monster newMonster() {
    return new Monster(this);
  }

  public Breed getParent() {
    return parent;
  }

  private void setParent(Breed parent) {
    this.parent = parent;
  }

  public BreedType getType() {
    return type;
  }

  private void setType(BreedType type) {
    this.type = type;
  }
  
}
