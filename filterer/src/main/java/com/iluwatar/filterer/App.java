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

package com.iluwatar.filterer;

import com.iluwatar.filterer.threat.ProbableThreat;
import com.iluwatar.filterer.threat.SimpleProbabilisticThreatAwareSystem;
import com.iluwatar.filterer.threat.SimpleProbableThreat;
import com.iluwatar.filterer.threat.SimpleThreat;
import com.iluwatar.filterer.threat.SimpleThreatAwareSystem;
import com.iluwatar.filterer.threat.Threat;
import com.iluwatar.filterer.threat.ThreatAwareSystem;
import com.iluwatar.filterer.threat.ThreatType;
import java.util.List;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;

/**
 * This demo class represent how {@link com.iluwatar.filterer.domain.Filterer} pattern is used to
 * filter container-like objects to return filtered versions of themselves. The container like
 * objects are systems that are aware of threats that they can be vulnerable to. We would like
 * to have a way to create copy of different system objects but with filtered threats.
 * The thing is to keep it simple if we add new subtype of {@link Threat}
 * (for example {@link ProbableThreat}) - we still need to be able to filter by it's properties.
 */
@Slf4j
public class App {

  public static void main(String[] args) {
    filteringSimpleThreats();
    filteringSimpleProbableThreats();
  }

  /**
   * Demonstrates how to filter {@link com.iluwatar.filterer.threat.ProbabilisticThreatAwareSystem}
   * based on probability property. The @{@link com.iluwatar.filterer.domain.Filterer#by(Predicate)}
   * method is able to use {@link com.iluwatar.filterer.threat.ProbableThreat}
   * as predicate argument.
   */
  private static void filteringSimpleProbableThreats() {
    LOGGER.info("### Filtering ProbabilisticThreatAwareSystem by probability ###");

    var trojanArcBomb = new SimpleProbableThreat("Trojan-ArcBomb", 1, ThreatType.TROJAN, 0.99);
    var rootkit = new SimpleProbableThreat("Rootkit-Kernel", 2, ThreatType.ROOTKIT, 0.8);

    List<ProbableThreat> probableThreats = List.of(trojanArcBomb, rootkit);

    var probabilisticThreatAwareSystem =
        new SimpleProbabilisticThreatAwareSystem("Sys-1", probableThreats);

    LOGGER.info("Filtering ProbabilisticThreatAwareSystem. Initial : "
        + probabilisticThreatAwareSystem);

    //Filtering using filterer
    var filteredThreatAwareSystem = probabilisticThreatAwareSystem.filtered()
        .by(probableThreat -> Double.compare(probableThreat.probability(), 0.99) == 0);

    LOGGER.info("Filtered by probability = 0.99 : " + filteredThreatAwareSystem);
  }

  /**
   * Demonstrates how to filter {@link ThreatAwareSystem} based on startingOffset property
   * of {@link SimpleThreat}. The @{@link com.iluwatar.filterer.domain.Filterer#by(Predicate)}
   * method is able to use {@link Threat} as predicate argument.
   */
  private static void filteringSimpleThreats() {
    LOGGER.info("### Filtering ThreatAwareSystem by ThreatType ###");

    var rootkit = new SimpleThreat(ThreatType.ROOTKIT, 1, "Simple-Rootkit");
    var trojan = new SimpleThreat(ThreatType.TROJAN, 2, "Simple-Trojan");
    List<Threat> threats = List.of(rootkit, trojan);

    var threatAwareSystem = new SimpleThreatAwareSystem("Sys-1", threats);

    LOGGER.info("Filtering ThreatAwareSystem. Initial : " + threatAwareSystem);

    //Filtering using Filterer
    var rootkitThreatAwareSystem = threatAwareSystem.filtered()
        .by(threat -> threat.type() == ThreatType.ROOTKIT);

    LOGGER.info("Filtered by threatType = ROOTKIT : " + rootkitThreatAwareSystem);
  }

}
