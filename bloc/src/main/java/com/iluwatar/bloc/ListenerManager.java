package com.iluwatar.bloc;
import java.util.List;

/**
 * Interface for managing listeners for state changes.
 *
 * @param <T> The type of state to be handled by the listeners.
 */

public interface ListenerManager<T> {

  /**
   * Adds a listener that will be notified of state changes.
   *
   * @param listener the listener to be added
   */
  void addListener(StateListener<T> listener);

  /**
   * Removes a listener so that it no longer receives state change notifications.
   *
   * @param listener the listener to be removed
   */
  void removeListener(StateListener<T> listener);

  /**
   * Returns a list of all listeners currently registered for state changes.
   *
   * @return a list of registered listeners
   */
  List<StateListener<T>> getListeners();
}
