package com.iluwatar.layers;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Date: 12/15/15 - 8:02 PM
 *
 * @author Jeroen Meulemeester
 */
public class CakeTest {

  @Test
  public void testSetId() {
    final Cake cake = new Cake();
    assertNull(cake.getId());

    final Long expectedId = Long.valueOf(1234L);
    cake.setId(expectedId);
    assertEquals(expectedId, cake.getId());
  }

  @Test
  public void testSetTopping() {
    final Cake cake = new Cake();
    assertNull(cake.getTopping());

    final CakeTopping expectedTopping = new CakeTopping("DummyTopping", 1000);
    cake.setTopping(expectedTopping);
    assertEquals(expectedTopping, cake.getTopping());
  }

  @Test
  public void testSetLayers() {
    final Cake cake = new Cake();
    assertNotNull(cake.getLayers());
    assertTrue(cake.getLayers().isEmpty());

    final Set<CakeLayer> expectedLayers = new HashSet<>();
    expectedLayers.add(new CakeLayer("layer1", 1000));
    expectedLayers.add(new CakeLayer("layer2", 2000));
    expectedLayers.add(new CakeLayer("layer3", 3000));

    cake.setLayers(expectedLayers);
    assertEquals(expectedLayers, cake.getLayers());
  }

  @Test
  public void testAddLayer() {
    final Cake cake = new Cake();
    assertNotNull(cake.getLayers());
    assertTrue(cake.getLayers().isEmpty());

    final Set<CakeLayer> initialLayers = new HashSet<>();
    initialLayers.add(new CakeLayer("layer1", 1000));
    initialLayers.add(new CakeLayer("layer2", 2000));

    cake.setLayers(initialLayers);
    assertEquals(initialLayers, cake.getLayers());

    final CakeLayer newLayer = new CakeLayer("layer3", 3000);
    cake.addLayer(newLayer);

    final Set<CakeLayer> expectedLayers = new HashSet<>();
    expectedLayers.addAll(initialLayers);
    expectedLayers.addAll(initialLayers);
    expectedLayers.add(newLayer);
    assertEquals(expectedLayers, cake.getLayers());
  }

  @Test
  public void testToString() {
    final CakeTopping topping = new CakeTopping("topping", 20);
    topping.setId(2345L);

    final CakeLayer layer = new CakeLayer("layer", 100);
    layer.setId(3456L);

    final Cake cake = new Cake();
    cake.setId(1234L);
    cake.setTopping(topping);
    cake.addLayer(layer);

    final String expected = "id=1234 topping=id=2345 name=topping calories=20 " +
            "layers=[id=3456 name=layer calories=100]";
    assertEquals(expected, cake.toString());

  }

}
