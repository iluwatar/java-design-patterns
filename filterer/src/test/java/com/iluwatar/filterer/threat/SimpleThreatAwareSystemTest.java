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

package com.iluwatar.filterer.threat;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleThreatAwareSystemTest {
  @Test
  void shouldFilterByThreatType() {
    //given
    var rootkit = new SimpleThreat(ThreatType.ROOTKIT, 1, "Simple-Rootkit");
    var trojan = new SimpleThreat(ThreatType.TROJAN, 2, "Simple-Trojan");
    List<Threat> threats = List.of(rootkit, trojan);

    var threatAwareSystem = new SimpleThreatAwareSystem("System-1", threats);

    //when
    var rootkitThreatAwareSystem = threatAwareSystem.filtered()
        .by(threat -> threat.type() == ThreatType.ROOTKIT);

    //then
    assertEquals(rootkitThreatAwareSystem.threats().size(), 1);
    assertEquals(rootkitThreatAwareSystem.threats().get(0), rootkit);
  }
}