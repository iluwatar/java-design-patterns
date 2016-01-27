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
package com.iluwatar.mediator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

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
   * The mocked standard out {@link PrintStream}, required since some actions on a {@link
   * PartyMember} have any influence on any other accessible objects, except for writing to std-out
   * using {@link System#out}
   */
  private final PrintStream stdOutMock = mock(PrintStream.class);

  /**
   * Keep the original std-out so it can be restored after the test
   */
  private final PrintStream stdOutOrig = System.out;

  /**
   * Inject the mocked std-out {@link PrintStream} into the {@link System} class before each test
   */
  @Before
  public void setUp() {
    System.setOut(this.stdOutMock);
  }

  /**
   * Removed the mocked std-out {@link PrintStream} again from the {@link System} class
   */
  @After
  public void tearDown() {
    System.setOut(this.stdOutOrig);
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

  /**
   * Verify if a party action triggers the correct output to the std-Out
   */
  @Test
  public void testPartyAction() {
    final PartyMember member = this.memberSupplier.get();

    for (final Action action : Action.values()) {
      member.partyAction(action);
      verify(this.stdOutMock).println(member.toString() + " " + action.getDescription());
    }

    verifyNoMoreInteractions(this.stdOutMock);
  }

  /**
   * Verify if a member action triggers the expected interactions with the party class
   */
  @Test
  public void testAct() {
    final PartyMember member = this.memberSupplier.get();

    member.act(Action.GOLD);
    verifyZeroInteractions(this.stdOutMock);

    final Party party = mock(Party.class);
    member.joinedParty(party);
    verify(this.stdOutMock).println(member.toString() + " joins the party");

    for (final Action action : Action.values()) {
      member.act(action);
      verify(this.stdOutMock).println(member.toString() + " " + action.toString());
      verify(party).act(member, action);
    }

    verifyNoMoreInteractions(party, this.stdOutMock);
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

}
