package com.iluwatar.bloc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class BlocTest {
  private Bloc bloc;
  private AtomicInteger stateValue;
  @BeforeEach
  void setUp() {
    bloc = new Bloc();
    stateValue = new AtomicInteger(0);
  }
  @Test
  void initialState() {
    assertTrue(bloc.getListeners().isEmpty(), "No listeners should be present initially.");
  }

  @Test
  void IncrementUpdateState() {
    bloc.addListener(state -> stateValue.set(state.value()));
    bloc.increment();
    assertEquals(1, stateValue.get(), "State should increment to 1");
  }

  @Test
  void DecrementUpdateState() {
    bloc.addListener(state -> stateValue.set(state.value()));
    bloc.decrement();
    assertEquals(-1, stateValue.get(), "State should decrement to -1");
  }

  @Test
  void addingListener() {
    bloc.addListener(state -> {});
    assertEquals(1, bloc.getListeners().size(), "Listener count should be 1.");
  }

  @Test
  void removingListener() {
    StateListener<State> listener = state -> {};
    bloc.addListener(listener);
    bloc.removeListener(listener);
    assertTrue(bloc.getListeners().isEmpty(), "Listener count should be 0 after removal.");
  }
  @Test
  void multipleListeners() {
    AtomicInteger secondValue = new AtomicInteger();
    bloc.addListener(state -> stateValue.set(state.value()));
    bloc.addListener(state -> secondValue.set(state.value()));
    bloc.increment();
    assertEquals(1, stateValue.get(), "First listener should receive state 1.");
    assertEquals(1, secondValue.get(), "Second listener should receive state 1.");
  }
}