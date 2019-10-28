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

package com.iluwatar.mediator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Date: 12/19/15 - 10:13 PM
 *
 * @author Jeroen Meulemeester
 */
public class PartyMemberTest {

  static Collection<Supplier<PartyMember>[]> dataProvider() {
    return List.of(
            new Supplier[]{Hobbit::new},
            new Supplier[]{Hunter::new},
            new Supplier[]{Rogue::new},
            new Supplier[]{Wizard::new}
    );
  }

  private InMemoryAppender appender;

  @BeforeEach
  public void setUp() {
    appender = new InMemoryAppender(PartyMemberBase.class);
  }

  @AfterEach
  public void tearDown() {
    appender.stop();
  }

  /**
   * Verify if a party action triggers the correct output to the std-Out
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  public void testPartyAction(Supplier<PartyMember> memberSupplier) {
    final PartyMember member = memberSupplier.get();

    for (final Action action : Action.values()) {
      member.partyAction(action);
      assertEquals(member.toString() + " " + action.getDescription(), appender.getLastMessage());
    }

    assertEquals(Action.values().length, appender.getLogSize());
  }

  /**
   * Verify if a member action triggers the expected interactions with the party class
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  public void testAct(Supplier<PartyMember> memberSupplier) {
    final PartyMember member = memberSupplier.get();

    member.act(Action.GOLD);
    assertEquals(0, appender.getLogSize());

    final Party party = mock(Party.class);
    member.joinedParty(party);
    assertEquals(member.toString() + " joins the party", appender.getLastMessage());

    for (final Action action : Action.values()) {
      member.act(action);
      assertEquals(member.toString() + " " + action.toString(), appender.getLastMessage());
      verify(party).act(member, action);
    }

    assertEquals(Action.values().length + 1, appender.getLogSize());
  }

  /**
   * Verify if {@link PartyMemberBase#toString()} generate the expected output
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  public void testToString(Supplier<PartyMember> memberSupplier) throws Exception {
    final PartyMember member = memberSupplier.get();
    final Class<? extends PartyMember> memberClass = member.getClass();
    assertEquals(memberClass.getSimpleName(), member.toString());
  }

  private class InMemoryAppender extends AppenderBase<ILoggingEvent> {
    private List<ILoggingEvent> log = new LinkedList<>();

    public InMemoryAppender(Class clazz) {
      ((Logger) LoggerFactory.getLogger(clazz)).addAppender(this);
      start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      log.add(eventObject);
    }

    public int getLogSize() {
      return log.size();
    }

    public String getLastMessage() {
      return log.get(log.size() - 1).getFormattedMessage();
    }
  }


}
