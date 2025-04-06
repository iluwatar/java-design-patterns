/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.filterer.threat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class SimpleProbabilisticThreatAwareSystemTest {

  @Test
  void shouldFilterByProbability() {
    // given
    var trojan = new SimpleProbableThreat("Troyan-ArcBomb", 1, ThreatType.TROJAN, 0.99);
    var rootkit = new SimpleProbableThreat("Rootkit-System", 2, ThreatType.ROOTKIT, 0.8);
    List<ProbableThreat> probableThreats = List.of(trojan, rootkit);

    var simpleProbabilisticThreatAwareSystem =
        new SimpleProbabilisticThreatAwareSystem("System-1", probableThreats);

    // when
    var filtered =
        simpleProbabilisticThreatAwareSystem
            .filtered()
            .by(probableThreat -> Double.compare(probableThreat.probability(), 0.99) == 0);

    // then
    assertEquals(filtered.threats().size(), 1);
    assertEquals(filtered.threats().get(0), trojan);
  }
}
