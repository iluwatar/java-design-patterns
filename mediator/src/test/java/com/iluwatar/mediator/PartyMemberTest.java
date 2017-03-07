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
package com.iluwatar.mediator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Date: 12/19/15 - 10:13 PM
 *
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class PartyMemberTest {

  @Parameterized.Parameters
  public static Collection<Supplier<PartyMember>[]> data() {
    return Arrays.asList(
            new Supplier[]{Hobbit::new},
            new Supplier[]{Hunter::new},
            new Supplier[]{Rogue::new},
            new Supplier[]{Wizard::new}
    );
  }

  /**
   * The factory, used to create a new instance of the tested party member
   */
  private final Supplier<PartyMember> memberSupplier;

  /**
   * Create a new test instance, using the given {@link PartyMember} factory
   *
   * @param memberSupplier The party member factory
   */
  public PartyMemberTest(final Supplier<PartyMember> memberSupplier) {
    this.memberSupplier = memberSupplier;
  }

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender(PartyMemberBase.class);
  }

  @After
  public void tearDown() {
    appender.stop();
  }

  /**
   * Verify if a party action triggers the correct output to the std-Out
   */
  @Test
  public void testPartyAction() {
    final PartyMember member = this.memberSupplier.get();

    for (final Action action : Action.values()) {
      member.partyAction(action);
      assertEquals(member.toString() + " " + action.getDescription(), appender.getLastMessage());
    }

    assertEquals(Action.values().length, appender.getLogSize());
  }

  /**
   * Verify if a member action triggers the expected interactions with the party class
   */
  @Test
  public void testAct() {
    final PartyMember member = this.memberSupplier.get();

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
   * Verify if {@link PartyMember#toString()} generate the expected output
   */
  @Test
  public void testToString() throws Exception {
    final PartyMember member = this.memberSupplier.get();
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
