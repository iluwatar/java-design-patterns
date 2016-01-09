package com.iluwatar.model.view.controller;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

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
  public void testSetHealth() {
    final GiantModel model = mock(GiantModel.class);
    final GiantView view = mock(GiantView.class);
    final GiantController controller = new GiantController(model, view);

    verifyZeroInteractions(model, view);

    for (final Health health : Health.values()) {
      controller.setHealth(health);
      verify(model).setHealth(health);
      verifyZeroInteractions(view);
    }

    controller.getHealth();
    verify(model).getHealth();

    verifyNoMoreInteractions(model, view);
  }

  /**
   * Verify if the controller passes the fatigue level through to the model and vice versa
   */
  @Test
  public void testSetFatigue() {
    final GiantModel model = mock(GiantModel.class);
    final GiantView view = mock(GiantView.class);
    final GiantController controller = new GiantController(model, view);

    verifyZeroInteractions(model, view);

    for (final Fatigue fatigue : Fatigue.values()) {
      controller.setFatigue(fatigue);
      verify(model).setFatigue(fatigue);
      verifyZeroInteractions(view);
    }

    controller.getFatigue();
    verify(model).getFatigue();

    verifyNoMoreInteractions(model, view);
  }

  /**
   * Verify if the controller passes the nourishment level through to the model and vice versa
   */
  @Test
  public void testSetNourishment() {
    final GiantModel model = mock(GiantModel.class);
    final GiantView view = mock(GiantView.class);
    final GiantController controller = new GiantController(model, view);

    verifyZeroInteractions(model, view);

    for (final Nourishment nourishment : Nourishment.values()) {
      controller.setNourishment(nourishment);
      verify(model).setNourishment(nourishment);
      verifyZeroInteractions(view);
    }

    controller.getNourishment();
    verify(model).getNourishment();

    verifyNoMoreInteractions(model, view);
  }

  @Test
  public void testUpdateView() {
    final GiantModel model = mock(GiantModel.class);
    final GiantView view = mock(GiantView.class);
    final GiantController controller = new GiantController(model, view);

    verifyZeroInteractions(model, view);

    controller.updateView();
    verify(view).displayGiant(model);

    verifyNoMoreInteractions(model, view);
  }

}