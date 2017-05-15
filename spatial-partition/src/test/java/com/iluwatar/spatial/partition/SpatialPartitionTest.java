package com.iluwatar.spatial.partition;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kristof.szoke on 2017-05-08.
 */
public class SpatialPartitionTest {

  private Grid grid;

  private Unit unit;

  private int x;

  private int y;

  @Before
  public void setUp() {
    grid = new Grid();
    unit = new Unit(grid, x, y);
  }

  @Test
  public void initialiseEmpty() {
    grid.clearGrid();
    assertEquals(grid.getCells().length, 10);
  }
}
