/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.layers;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Date: 12/15/15 - 9:55 PM
 *
 * @author Jeroen Meulemeester
 */
public class CakeBakingServiceImplTest {

  @Test
  public void testLayers() throws CakeBakingException {
    final CakeBakingServiceImpl service = new CakeBakingServiceImpl();

    final List<CakeLayerInfo> initialLayers = service.getAvailableLayers();
    assertNotNull(initialLayers);
    assertTrue(initialLayers.isEmpty());

    service.saveNewLayer(new CakeLayerInfo("Layer1", 1000));
    service.saveNewLayer(new CakeLayerInfo("Layer2", 2000));

    final List<CakeLayerInfo> availableLayers = service.getAvailableLayers();
    assertNotNull(availableLayers);
    assertEquals(2, availableLayers.size());
    for (final CakeLayerInfo layer : availableLayers) {
      assertNotNull(layer.id);
      assertNotNull(layer.name);
      assertNotNull(layer.toString());
      assertTrue(layer.calories > 0);
    }

  }

  @Test
  public void testToppings() throws CakeBakingException {
    final CakeBakingServiceImpl service = new CakeBakingServiceImpl();

    final List<CakeToppingInfo> initialToppings = service.getAvailableToppings();
    assertNotNull(initialToppings);
    assertTrue(initialToppings.isEmpty());

    service.saveNewTopping(new CakeToppingInfo("Topping1", 1000));
    service.saveNewTopping(new CakeToppingInfo("Topping2", 2000));

    final List<CakeToppingInfo> availableToppings = service.getAvailableToppings();
    assertNotNull(availableToppings);
    assertEquals(2, availableToppings.size());
    for (final CakeToppingInfo topping : availableToppings) {
      assertNotNull(topping.id);
      assertNotNull(topping.name);
      assertNotNull(topping.toString());
      assertTrue(topping.calories > 0);
    }

  }

  @Test
  public void testBakeCakes() throws CakeBakingException {
    final CakeBakingServiceImpl service = new CakeBakingServiceImpl();

    final List<CakeInfo> initialCakes = service.getAllCakes();
    assertNotNull(initialCakes);
    assertTrue(initialCakes.isEmpty());

    final CakeToppingInfo topping1 = new CakeToppingInfo("Topping1", 1000);
    final CakeToppingInfo topping2 = new CakeToppingInfo("Topping2", 2000);
    service.saveNewTopping(topping1);
    service.saveNewTopping(topping2);

    final CakeLayerInfo layer1 = new CakeLayerInfo("Layer1", 1000);
    final CakeLayerInfo layer2 = new CakeLayerInfo("Layer2", 2000);
    final CakeLayerInfo layer3 = new CakeLayerInfo("Layer3", 2000);
    service.saveNewLayer(layer1);
    service.saveNewLayer(layer2);
    service.saveNewLayer(layer3);

    service.bakeNewCake(new CakeInfo(topping1, Arrays.asList(layer1, layer2)));
    service.bakeNewCake(new CakeInfo(topping2, Collections.singletonList(layer3)));

    final List<CakeInfo> allCakes = service.getAllCakes();
    assertNotNull(allCakes);
    assertEquals(2, allCakes.size());
    for (final CakeInfo cakeInfo : allCakes) {
      assertNotNull(cakeInfo.id);
      assertNotNull(cakeInfo.cakeToppingInfo);
      assertNotNull(cakeInfo.cakeLayerInfos);
      assertNotNull(cakeInfo.toString());
      assertFalse(cakeInfo.cakeLayerInfos.isEmpty());
      assertTrue(cakeInfo.calculateTotalCalories() > 0);
    }

  }

  @Test(expected = CakeBakingException.class)
  public void testBakeCakeMissingTopping() throws CakeBakingException {
    final CakeBakingServiceImpl service = new CakeBakingServiceImpl();

    final CakeLayerInfo layer1 = new CakeLayerInfo("Layer1", 1000);
    final CakeLayerInfo layer2 = new CakeLayerInfo("Layer2", 2000);
    service.saveNewLayer(layer1);
    service.saveNewLayer(layer2);

    final CakeToppingInfo missingTopping = new CakeToppingInfo("Topping1", 1000);
    service.bakeNewCake(new CakeInfo(missingTopping, Arrays.asList(layer1, layer2)));
  }

  @Test(expected = CakeBakingException.class)
  public void testBakeCakeMissingLayer() throws CakeBakingException {
    final CakeBakingServiceImpl service = new CakeBakingServiceImpl();

    final List<CakeInfo> initialCakes = service.getAllCakes();
    assertNotNull(initialCakes);
    assertTrue(initialCakes.isEmpty());

    final CakeToppingInfo topping1 = new CakeToppingInfo("Topping1", 1000);
    service.saveNewTopping(topping1);

    final CakeLayerInfo layer1 = new CakeLayerInfo("Layer1", 1000);
    service.saveNewLayer(layer1);

    final CakeLayerInfo missingLayer = new CakeLayerInfo("Layer2", 2000);
    service.bakeNewCake(new CakeInfo(topping1, Arrays.asList(layer1, missingLayer)));

  }

  @Test(expected = CakeBakingException.class)
  public void testBakeCakesUsedLayer() throws CakeBakingException {
    final CakeBakingServiceImpl service = new CakeBakingServiceImpl();

    final List<CakeInfo> initialCakes = service.getAllCakes();
    assertNotNull(initialCakes);
    assertTrue(initialCakes.isEmpty());

    final CakeToppingInfo topping1 = new CakeToppingInfo("Topping1", 1000);
    final CakeToppingInfo topping2 = new CakeToppingInfo("Topping2", 2000);
    service.saveNewTopping(topping1);
    service.saveNewTopping(topping2);

    final CakeLayerInfo layer1 = new CakeLayerInfo("Layer1", 1000);
    final CakeLayerInfo layer2 = new CakeLayerInfo("Layer2", 2000);
    service.saveNewLayer(layer1);
    service.saveNewLayer(layer2);

    service.bakeNewCake(new CakeInfo(topping1, Arrays.asList(layer1, layer2)));
    service.bakeNewCake(new CakeInfo(topping2, Collections.singletonList(layer2)));

  }

}
