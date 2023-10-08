package crtp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class FightTest {

  /**
   * A fighter has signed a contract with a promotion, and he will face some other fighters. A list of opponents is ready
   * but for some reason not all of them belong to the same weight class. Let's ensure that the fighter will only face
   * opponents in the same weight class.
   */
  @Test
  void testFighterCanFightOnlyAgainstSameWeightOpponents() {
    MmaBantamweightFighter fighter = new MmaBantamweightFighter("Joe", "Johnson", "The Geek", "Muay Thai");
    List<MmaFighter<?>> opponents = getOpponents();
    List<MmaFighter<?>> challenged = new ArrayList<>();

    opponents.forEach(challenger -> {
      try {
        ((MmaBantamweightFighter) challenger).fight(fighter);
        challenged.add(challenger);
      } catch (ClassCastException e) {
          LOGGER.error(e.getMessage());
      }
    });

    assertFalse(challenged.isEmpty());
    assertTrue(challenged.stream().allMatch(c -> c instanceof MmaBantamweightFighter));
  }

  private static List<MmaFighter<?>> getOpponents() {
    return List.of(
          new MmaBantamweightFighter("Ed", "Edwards", "The Problem Solver", "Judo"),
          new MmaLightweightFighter("Evan", "Evans", "Clean Coder", "Sambo"),
          new MmaHeavyweightFighter("Dave", "Davidson", "The Bug Smasher", "Kickboxing"),
          new MmaBantamweightFighter("Ray", "Raymond", "Scrum Master", "Karate"),
          new MmaHeavyweightFighter("Jack", "Jackson", "The Pragmatic", "Brazilian Jiu-Jitsu")
    );
  }


}
