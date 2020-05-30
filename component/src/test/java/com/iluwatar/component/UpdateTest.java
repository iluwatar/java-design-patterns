/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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
package com.iluwatar.component;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests that Component example runs without errors.
 */

public class UpdateTest {
  /**
   * test for the update for the input component of the object
   */
  @Test
  public void inputUpdateTest() {
    var arrayList = new ArrayList<Component>();
    arrayList.add(new BjornInputComponent());
    var gameObject = new GameObject(arrayList);
    gameObject.setPositionOFy(12);
    gameObject.setPositionOFx(13);
    gameObject.setVelocity(1);
    gameObject.update();
    assertEquals(14, gameObject.getPositionOFx());
    assertEquals(13,gameObject.getPositionOFy());
  }
  /**
   * test for the update for the Physics component of the object
   */
  @Test
  public void physicsUpdateTest() {
    var arrayList = new ArrayList<Component>();
    arrayList.add(new BjornInputComponent());
    arrayList.add(new BjornPhysicsComponent());
    arrayList.add(new BjornGraphicsComponent());
    var gameObject = new GameObject(arrayList);
    gameObject.setPositionOFy(13);
    gameObject.setPositionOFx(12);
    gameObject.setVelocity(1);
    gameObject.update();
    assertEquals(13, gameObject.getPositionOFx());
    assertEquals(14,gameObject.getPositionOFy());
  }
  /**
   * test for the update for the Graphics component of the object
   */
  @Test
  public void graphicsUpdateTest() {
    var arrayList = new ArrayList<Component>();
    arrayList.add(new BjornInputComponent());
    arrayList.add(new BjornPhysicsComponent());
    arrayList.add(new BjornGraphicsComponent());
    var gameObject = new GameObject(arrayList);
    gameObject.setPositionOFy(1);
    gameObject.setPositionOFx(1);
    gameObject.setVelocity(1);
    gameObject.update();
    assertEquals(2, gameObject.getPositionOFx());
    assertEquals(2,gameObject.getPositionOFy());
  }
  /**
   * test for the setPositionOFx
   */
  @Test
  public void setPositionOFxTest(){
    var arrayList = new ArrayList<Component>();
    arrayList.add(new BjornInputComponent());
    arrayList.add(new BjornPhysicsComponent());
    arrayList.add(new BjornGraphicsComponent());
    var gameObject = new GameObject(arrayList);
    gameObject.setPositionOFx(1);
    assertEquals(1,gameObject.getPositionOFx());
  }
  /**
   * test for the getPositionOFx
   */
  @Test
  public void getPositionOFxTest(){
    var arrayList = new ArrayList<Component>();
    arrayList.add(new BjornInputComponent());
    arrayList.add(new BjornPhysicsComponent());
    arrayList.add(new BjornGraphicsComponent());
    var gameObject = new GameObject(arrayList);
    gameObject.setPositionOFx(1);
    assertEquals(1,gameObject.getPositionOFx());
  }
  /**
   * test for the setPositionOFy
   */
  @Test
  public void setPositionOFyTest(){
    var arrayList = new ArrayList<Component>();
    arrayList.add(new BjornInputComponent());
    arrayList.add(new BjornPhysicsComponent());
    arrayList.add(new BjornGraphicsComponent());
    var gameObject = new GameObject(arrayList);
    gameObject.setPositionOFy(1);
    assertEquals(1,gameObject.getPositionOFy());
  }
  /**
   * test for the getPositionOFy
   */
  @Test
  public void getPositionOFyTest(){
    var arrayList = new ArrayList<Component>();
    arrayList.add(new BjornInputComponent());
    arrayList.add(new BjornPhysicsComponent());
    arrayList.add(new BjornGraphicsComponent());
    var gameObject = new GameObject(arrayList);
    gameObject.setPositionOFy(1);
    assertEquals(1,gameObject.getPositionOFy());
  }
  /**
   * test for the setVelocity
   */
  @Test
  public void setVelocityTest(){
    var arrayList = new ArrayList<Component>();
    arrayList.add(new BjornInputComponent());
    arrayList.add(new BjornPhysicsComponent());
    arrayList.add(new BjornGraphicsComponent());
    var gameObject = new GameObject(arrayList);
    gameObject.setVelocity(1);
    assertEquals(1,gameObject.getVelocity());
  }
  /**
   * test for the getVelocity
   */
  @Test
  public void getVelocityTest(){
    var arrayList = new ArrayList<Component>();
    arrayList.add(new BjornInputComponent());
    arrayList.add(new BjornPhysicsComponent());
    arrayList.add(new BjornGraphicsComponent());
    var gameObject = new GameObject(arrayList);
    gameObject.setVelocity(1);
    assertEquals(1,gameObject.getVelocity());
  }
}
