/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.model.view.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.jupiter.api.Test;

/**
 * Date: 12/20/15 - 2:19 PM
 *
 * @author Jeroen Meulemeester
 */
public class GiantControllerTest {

  /**
   * Verify if the controller passes the health level through to the model and vice versa
   */
  @Test
  void testSetHealth() {
    final var model = mock(GiantModel.class);
    final var view = mock(GiantView.class);
    final var controller = new GiantController(model, view);

    verifyZeroInteractions(model, view);

    for (final var health : Health.values()) {
      controller.setHealth(health);
      verify(model).setHealth(health);
      verifyZeroInteractions(view);
    }

    controller.getHealth();
    //noinspection ResultOfMethodCallIgnored
    verify(model).getHealth();

    verifyNoMoreInteractions(model, view);
  }

  /**
   * Verify if the controller passes the fatigue level through to the model and vice versa
   */
  @Test
  void testSetFatigue() {
    final var model = mock(GiantModel.class);
    final var view = mock(GiantView.class);
    final var controller = new GiantController(model, view);

    verifyZeroInteractions(model, view);

    for (final var fatigue : Fatigue.values()) {
      controller.setFatigue(fatigue);
      verify(model).setFatigue(fatigue);
      verifyZeroInteractions(view);
    }

    controller.getFatigue();
    //noinspection ResultOfMethodCallIgnored
    verify(model).getFatigue();

    verifyNoMoreInteractions(model, view);
  }

  /**
   * Verify if the controller passes the nourishment level through to the model and vice versa
   */
  @Test
  void testSetNourishment() {
    final var model = mock(GiantModel.class);
    final var view = mock(GiantView.class);
    final var controller = new GiantController(model, view);

    verifyZeroInteractions(model, view);

    for (final var nourishment : Nourishment.values()) {
      controller.setNourishment(nourishment);
      verify(model).setNourishment(nourishment);
      verifyZeroInteractions(view);
    }

    controller.getNourishment();
    //noinspection ResultOfMethodCallIgnored
    verify(model).getNourishment();

    verifyNoMoreInteractions(model, view);
  }

  @Test
  void testUpdateView() {
    final var model = mock(GiantModel.class);
    final var view = mock(GiantView.class);
    final var controller = new GiantController(model, view);

    verifyZeroInteractions(model, view);

    controller.updateView();
    verify(view).displayGiant(model);

    verifyNoMoreInteractions(model, view);
  }

}