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
package com.iluwatar.visitor;

import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/30/15 - 18:59 PM
 *
 * @author Jeroen Meulemeester
 */
public abstract class VisitorTest<V extends UnitVisitor> extends StdOutTest {

  /**
   * The tested visitor instance
   */
  private final V visitor;

  /**
   * The optional expected response when being visited by a commander
   */
  private final Optional<String> commanderResponse;

  /**
   * The optional expected response when being visited by a sergeant
   */
  private final Optional<String> sergeantResponse;

  /**
   * The optional expected response when being visited by a soldier
   */
  private final Optional<String> soldierResponse;

  /**
   * Create a new test instance for the given visitor
   *
   * @param commanderResponse The optional expected response when being visited by a commander
   * @param sergeantResponse  The optional expected response when being visited by a sergeant
   * @param soldierResponse   The optional expected response when being visited by a soldier
   */
  public VisitorTest(final V visitor, final Optional<String> commanderResponse,
                     final Optional<String> sergeantResponse, final Optional<String> soldierResponse) {

    this.visitor = visitor;
    this.commanderResponse = commanderResponse;
    this.sergeantResponse = sergeantResponse;
    this.soldierResponse = soldierResponse;
  }

  @Test
  public void testVisitCommander() {
    this.visitor.visitCommander(new Commander());
    if (this.commanderResponse.isPresent()) {
      verify(getStdOutMock()).println(this.commanderResponse.get());
    }
    verifyNoMoreInteractions(getStdOutMock());
  }

  @Test
  public void testVisitSergeant() {
    this.visitor.visitSergeant(new Sergeant());
    if (this.sergeantResponse.isPresent()) {
      verify(getStdOutMock()).println(this.sergeantResponse.get());
    }
    verifyNoMoreInteractions(getStdOutMock());
  }

  @Test
  public void testVisitSoldier() {
    this.visitor.visitSoldier(new Soldier());
    if (this.soldierResponse.isPresent()) {
      verify(getStdOutMock()).println(this.soldierResponse.get());
    }
    verifyNoMoreInteractions(getStdOutMock());
  }

}
