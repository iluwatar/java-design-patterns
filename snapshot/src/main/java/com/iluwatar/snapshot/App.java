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
package com.iluwatar.snapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * The Snapshot pattern is a view of an object at a point in time.
 *
 * <p>A Snapshot is simply a view of an object with all the temporal aspects removed.</p>
 *
 * <p>In this example, the {@link Customer} has the temporal property, and
 * {@link CustomerSnapshot} is the view. The snapshot is taken several times at random intervals
 * and is stored, as would be expected on a manual snapshotting system. </p>
 */
public class App {
  /**
   * Main class.
   *
   * @param args Arguments are not considered.
   */
  public static void main(String[] args) {
    Customer billy = new Customer();
    HashMap<SimpleDate, CustomerSnapshot> billySnapshots = new HashMap<>();

    // billy changes addresses a few times, and has snapshots taken occasionally.

    billy.putAddress(new SimpleDate(2004, 2, 4), "88 Worcester St");

    billySnapshots.put(new SimpleDate(2004, 3, 1),
            new CustomerSnapshot(billy, new SimpleDate(2004, 3, 1)));
    billySnapshots.put(new SimpleDate(2005, 5, 3),
            new CustomerSnapshot(billy, new SimpleDate(2005, 5, 3)));

    billy.putAddress(new SimpleDate(2005, 9, 2), "87 Franklin St");

    billySnapshots.put(new SimpleDate(2006, 2, 1),
            new CustomerSnapshot(billy, new SimpleDate(2006, 2, 1)));

    billy.putAddress(new SimpleDate(2007, 1, 2), "18 Circuit St");
    billySnapshots.put(new SimpleDate(2007, 1, 5),
            new CustomerSnapshot(billy, new SimpleDate(2006, 2, 1)));

    System.out.println("Snapshots of billy's address:");
    for (Map.Entry<SimpleDate, CustomerSnapshot> entry : billySnapshots.entrySet()) {
      System.out.println("On " + entry.getKey() + " billy was at " + entry.getValue().getAddress());
    }

  }
}
