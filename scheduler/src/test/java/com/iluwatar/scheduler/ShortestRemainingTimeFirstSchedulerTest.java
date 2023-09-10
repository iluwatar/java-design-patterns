package com.iluwatar.scheduler;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShortestRemainingTimeFirstSchedulerTest {
  @Test
  void testExecuteTasksFromBeginning() {
    Map<Integer, List<Task>> tasks = new HashMap<>();
    tasks.put(0, List.of(new Task(1, 3), new Task(2, 2), new Task(3, 4)));

    Simulator simulator = new Simulator(new ShortestRemainingTimeFirstScheduler(), tasks, 1, 10);

    LinkedHashMap<Integer, Integer> taskCompletedOrder = simulator.simulate();
    assertEquals(3, taskCompletedOrder.size());
    assertIterableEquals(List.of(2, 1, 3), taskCompletedOrder.keySet());
  }

  @Test
  void testExecuteTasks() {
    Map<Integer, List<Task>> tasks = new HashMap<>();
    tasks.put(0, List.of(new Task(1, 3)));
    tasks.put(1, List.of(new Task(2, 2)));
    tasks.put(2, List.of(new Task(3, 4)));
    tasks.put(7, List.of(new Task(4, 2)));
    tasks.put(8, List.of(new Task(5, 1)));

    Simulator simulator = new Simulator(new ShortestRemainingTimeFirstScheduler(), tasks, 1, 100);

    LinkedHashMap<Integer, Integer> taskCompletedOrder = simulator.simulate();

    assertEquals(5, taskCompletedOrder.size());
    assertIterableEquals(List.of(1, 2, 3, 5, 4), taskCompletedOrder.keySet());
    assertEquals(3, taskCompletedOrder.get(1));
    assertEquals(5, taskCompletedOrder.get(2));
    assertEquals(9, taskCompletedOrder.get(3));
    assertEquals(10, taskCompletedOrder.get(5));
    assertEquals(12, taskCompletedOrder.get(4));
  }
}
