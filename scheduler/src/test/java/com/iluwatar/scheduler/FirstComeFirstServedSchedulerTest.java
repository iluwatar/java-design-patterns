package com.iluwatar.scheduler;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FirstComeFirstServedSchedulerTest {
  @Test
  void testExecuteTasksFromBeginning() {
    Map<Integer, List<Task>> tasks = new HashMap<>();
    tasks.put(0, List.of(new Task(1, 3), new Task(2, 2), new Task(3, 4)));

    Simulator simulator = new Simulator(new FirstComeFirstServedScheduler(), tasks, 1, 100);

    Map<Integer, Integer> taskCompletedOrder = simulator.simulate();

    assertEquals(3, taskCompletedOrder.size());
    assertIterableEquals(List.of(1, 2, 3), taskCompletedOrder.keySet());
    assertEquals(3, taskCompletedOrder.get(1));
    assertEquals(5, taskCompletedOrder.get(2));
    assertEquals(9, taskCompletedOrder.get(3));
  }

  @Test
  void testExecuteTasks() {
    Map<Integer, List<Task>> tasks = new HashMap<>();
    tasks.put(0, List.of(new Task(1, 3)));
    tasks.put(1, List.of(new Task(2, 2)));
    tasks.put(6, List.of(new Task(3, 4)));
    tasks.put(7, List.of(new Task(4, 2)));
    tasks.put(8, List.of(new Task(5, 1)));

    Simulator simulator = new Simulator(new FirstComeFirstServedScheduler(), tasks, 1, 100);

    Map<Integer, Integer> taskCompletedOrder = simulator.simulate();

    assertEquals(5, taskCompletedOrder.size());
    assertIterableEquals(List.of(1, 2, 3, 4, 5), taskCompletedOrder.keySet());
    assertEquals(3, taskCompletedOrder.get(1));
    assertEquals(5, taskCompletedOrder.get(2));
    assertEquals(10, taskCompletedOrder.get(3));
    assertEquals(12, taskCompletedOrder.get(4));
    assertEquals(13, taskCompletedOrder.get(5));
  }
}
