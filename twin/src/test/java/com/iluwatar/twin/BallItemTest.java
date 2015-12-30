package com.iluwatar.twin;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/30/15 - 18:44 PM
 *
 * @author Jeroen Meulemeester
 */
public class BallItemTest extends StdOutTest {

  @Test
  public void testClick() {
    final BallThread ballThread = mock(BallThread.class);
    final BallItem ballItem = new BallItem();
    ballItem.setTwin(ballThread);

    final InOrder inOrder = inOrder(ballThread);

    for (int i = 0; i < 10; i++) {
      ballItem.click();
      inOrder.verify(ballThread).suspendMe();

      ballItem.click();
      inOrder.verify(ballThread).resumeMe();
    }

    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void testDoDraw() {
    final BallItem ballItem = new BallItem();
    final BallThread ballThread = mock(BallThread.class);
    ballItem.setTwin(ballThread);

    ballItem.draw();
    verify(getStdOutMock()).println("draw");
    verify(getStdOutMock()).println("doDraw");

    verifyNoMoreInteractions(ballThread, getStdOutMock());
  }

  @Test
  public void testMove() {
    final BallItem ballItem = new BallItem();
    final BallThread ballThread = mock(BallThread.class);
    ballItem.setTwin(ballThread);

    ballItem.move();
    verify(getStdOutMock()).println("move");

    verifyNoMoreInteractions(ballThread, getStdOutMock());
  }

}