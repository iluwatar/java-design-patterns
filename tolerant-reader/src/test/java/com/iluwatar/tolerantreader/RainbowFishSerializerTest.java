package com.iluwatar.tolerantreader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Date: 12/30/15 - 18:39 PM
 *
 * @author Jeroen Meulemeester
 */
public class RainbowFishSerializerTest {

  /**
   * Create a temporary folder, used to generate files in during this test
   */
  @Rule
  public final TemporaryFolder testFolder = new TemporaryFolder();

  /**
   * Rainbow fish version 1 used during the tests
   */
  private static final RainbowFish V1 = new RainbowFish("version1", 1, 2, 3);

  /**
   * Rainbow fish version 2 used during the tests
   */
  private static final RainbowFishV2 V2 = new RainbowFishV2("version2", 4, 5, 6, true, false, true);

  /**
   * Verify if a fish, written as version 1 can be read back as version 1
   */
  @Test
  public void testWriteV1ReadV1() throws Exception {
    final File outputFile = this.testFolder.newFile();
    RainbowFishSerializer.writeV1(V1, outputFile.getPath());

    final RainbowFish fish = RainbowFishSerializer.readV1(outputFile.getPath());
    assertNotSame(V1, fish);
    assertEquals(V1.getName(), fish.getName());
    assertEquals(V1.getAge(), fish.getAge());
    assertEquals(V1.getLengthMeters(), fish.getLengthMeters());
    assertEquals(V1.getWeightTons(), fish.getWeightTons());

  }

  /**
   * Verify if a fish, written as version 2 can be read back as version 1
   */
  @Test
  public void testWriteV2ReadV1() throws Exception {
    final File outputFile = this.testFolder.newFile();
    RainbowFishSerializer.writeV2(V2, outputFile.getPath());

    final RainbowFish fish = RainbowFishSerializer.readV1(outputFile.getPath());
    assertNotSame(V2, fish);
    assertEquals(V2.getName(), fish.getName());
    assertEquals(V2.getAge(), fish.getAge());
    assertEquals(V2.getLengthMeters(), fish.getLengthMeters());
    assertEquals(V2.getWeightTons(), fish.getWeightTons());
  }

}