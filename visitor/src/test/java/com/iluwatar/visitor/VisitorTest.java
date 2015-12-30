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
