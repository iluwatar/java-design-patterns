package com.iluwatar.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    Map<Integer, List<Task>> tasks = new HashMap<>();
    tasks.put(0, List.of(new Task(1, 3), new Task(2, 2), new Task(3, 4)));

    TaskScheduler scheduler = new FirstComeFirstServedScheduler();
    Simulator simulator = new Simulator(scheduler, tasks, 1, 100);
    simulator.simulate();
  }
}
