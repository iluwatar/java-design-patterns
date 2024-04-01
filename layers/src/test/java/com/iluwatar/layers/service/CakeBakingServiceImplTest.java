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

package com.iluwatar.layers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.layers.app.LayersApp;
import dto.CakeInfo;
import dto.CakeLayerInfo;
import dto.CakeToppingInfo;
import exception.CakeBakingException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import service.CakeBakingServiceImpl;

/**
 * Constructs a new instance of CakeBakingServiceImplTest.
 *
 * @param cakeBakingService the service for cake baking operations
 */
@SpringBootTest(classes = LayersApp.class)
class CakeBakingServiceImplTest {

  private final CakeBakingServiceImpl cakeBakingService;

  @Autowired
  CakeBakingServiceImplTest(CakeBakingServiceImpl cakeBakingService) {
    this.cakeBakingService = cakeBakingService;
  }

  @BeforeEach
  void setUp() {
    cakeBakingService.deleteAllCakes();
    cakeBakingService.deleteAllLayers();
    cakeBakingService.deleteAllToppings();
  }


  @Test
  void testLayers() {
    final var initialLayers = cakeBakingService.getAvailableLayers();
    assertNotNull(initialLayers);
    assertTrue(initialLayers.isEmpty());

    cakeBakingService.saveNewLayer(new CakeLayerInfo("Layer1", 1000));
    cakeBakingService.saveNewLayer(new CakeLayerInfo("Layer2", 2000));

    final var availableLayers = cakeBakingService.getAvailableLayers();
    assertNotNull(availableLayers);
    assertEquals(2, availableLayers.size());
    for (final var layer : availableLayers) {
      assertNotNull(layer.id);
      assertNotNull(layer.name);
      assertNotNull(layer.toString());
      assertTrue(layer.calories > 0);
    }

  }

  @Test
  void testToppings() {
    final var initialToppings = cakeBakingService.getAvailableToppings();
    assertNotNull(initialToppings);
    assertTrue(initialToppings.isEmpty());

    cakeBakingService.saveNewTopping(new CakeToppingInfo("Topping1", 1000));
    cakeBakingService.saveNewTopping(new CakeToppingInfo("Topping2", 2000));

    final var availableToppings = cakeBakingService.getAvailableToppings();
    assertNotNull(availableToppings);
    assertEquals(2, availableToppings.size());
    for (final var topping : availableToppings) {
      assertNotNull(topping.id);
      assertNotNull(topping.name);
      assertNotNull(topping.toString());
      assertTrue(topping.calories > 0);
    }

  }

  @Test
  void testBakeCakes() throws CakeBakingException {

    final var initialCakes = cakeBakingService.getAllCakes();
    assertNotNull(initialCakes);
    assertTrue(initialCakes.isEmpty());

    final var topping1 = new CakeToppingInfo("Topping1", 1000);
    final var topping2 = new CakeToppingInfo("Topping2", 2000);
    cakeBakingService.saveNewTopping(topping1);
    cakeBakingService.saveNewTopping(topping2);

    final var layer1 = new CakeLayerInfo("Layer1", 1000);
    final var layer2 = new CakeLayerInfo("Layer2", 2000);
    final var layer3 = new CakeLayerInfo("Layer3", 2000);
    cakeBakingService.saveNewLayer(layer1);
    cakeBakingService.saveNewLayer(layer2);
    cakeBakingService.saveNewLayer(layer3);

    cakeBakingService.bakeNewCake(new CakeInfo(topping1, List.of(layer1, layer2)));
    cakeBakingService.bakeNewCake(new CakeInfo(topping2, Collections.singletonList(layer3)));

    final var allCakes = cakeBakingService.getAllCakes();
    assertNotNull(allCakes);
    assertEquals(2, allCakes.size());
    for (final var cakeInfo : allCakes) {
      assertNotNull(cakeInfo.id);
      assertNotNull(cakeInfo.cakeToppingInfo);
      assertNotNull(cakeInfo.cakeLayerInfos);
      assertNotNull(cakeInfo.toString());
      assertFalse(cakeInfo.cakeLayerInfos.isEmpty());
      assertTrue(cakeInfo.calculateTotalCalories() > 0);
    }

  }

  @Test
  void testBakeCakeMissingTopping() {
    final var layer1 = new CakeLayerInfo("Layer1", 1000);
    final var layer2 = new CakeLayerInfo("Layer2", 2000);
    cakeBakingService.saveNewLayer(layer1);
    cakeBakingService.saveNewLayer(layer2);

    final var missingTopping = new CakeToppingInfo("Topping1", 1000);
    assertThrows(CakeBakingException.class,
        () -> cakeBakingService.bakeNewCake(new CakeInfo(missingTopping, List.of(layer1, layer2))));
  }

  @Test
  void testBakeCakeMissingLayer() {
    final var initialCakes = cakeBakingService.getAllCakes();
    assertNotNull(initialCakes);
    assertTrue(initialCakes.isEmpty());

    final var topping1 = new CakeToppingInfo("Topping1", 1000);
    cakeBakingService.saveNewTopping(topping1);

    final var layer1 = new CakeLayerInfo("Layer1", 1000);
    cakeBakingService.saveNewLayer(layer1);

    final var missingLayer = new CakeLayerInfo("Layer2", 2000);
    assertThrows(CakeBakingException.class,
        () -> cakeBakingService.bakeNewCake(new CakeInfo(topping1, List.of(layer1, missingLayer))));
  }

  @Test
  void testBakeCakesUsedLayer() throws CakeBakingException {
    final var initialCakes = cakeBakingService.getAllCakes();
    assertNotNull(initialCakes);
    assertTrue(initialCakes.isEmpty());

    final var topping1 = new CakeToppingInfo("Topping1", 1000);
    final var topping2 = new CakeToppingInfo("Topping2", 2000);
    cakeBakingService.saveNewTopping(topping1);
    cakeBakingService.saveNewTopping(topping2);

    final var layer1 = new CakeLayerInfo("Layer1", 1000);
    final var layer2 = new CakeLayerInfo("Layer2", 2000);
    cakeBakingService.saveNewLayer(layer1);
    cakeBakingService.saveNewLayer(layer2);

    cakeBakingService.bakeNewCake(new CakeInfo(topping1, List.of(layer1, layer2)));
    assertThrows(CakeBakingException.class, () -> cakeBakingService.bakeNewCake(
        new CakeInfo(topping2, Collections.singletonList(layer2))));
  }

}
