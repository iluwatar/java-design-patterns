/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2023 Ilkka Seppälä
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

import dto.CakeInfo;
import dto.CakeLayerInfo;
import dto.CakeToppingInfo;
import exception.CakeBakingException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import service.CakeBakingService;
import view.CakeViewImpl;

@Component
@Slf4j
public class Runner implements CommandLineRunner {
  public static final String STRAWBERRY = "strawberry";
  private final CakeBakingService cakeBakingService;

  @Autowired
  public Runner(CakeBakingService cakeBakingService) {
    this.cakeBakingService = cakeBakingService;
  }

  @Override
  public void run(String... args) {
    // initialize sample data
    initializeData();
    // create view and render it
    var cakeView = new CakeViewImpl(cakeBakingService);
    cakeView.render();
  }

  /** Initializes the example data. */
  private void initializeData() {
    cakeBakingService.saveNewLayer(new CakeLayerInfo("chocolate", 1200));
    cakeBakingService.saveNewLayer(new CakeLayerInfo("banana", 900));
    cakeBakingService.saveNewLayer(new CakeLayerInfo(STRAWBERRY, 950));
    cakeBakingService.saveNewLayer(new CakeLayerInfo("lemon", 950));
    cakeBakingService.saveNewLayer(new CakeLayerInfo("vanilla", 950));
    cakeBakingService.saveNewLayer(new CakeLayerInfo(STRAWBERRY, 950));

    cakeBakingService.saveNewTopping(new CakeToppingInfo("candies", 350));
    cakeBakingService.saveNewTopping(new CakeToppingInfo("cherry", 350));

    var cake1 =
        new CakeInfo(
            new CakeToppingInfo("candies", 0),
            List.of(
                new CakeLayerInfo("chocolate", 0),
                new CakeLayerInfo("banana", 0),
                new CakeLayerInfo(STRAWBERRY, 0)));
    try {
      cakeBakingService.bakeNewCake(cake1);
    } catch (CakeBakingException e) {
      LOGGER.error("Cake baking exception", e);
    }
    var cake2 =
        new CakeInfo(
            new CakeToppingInfo("cherry", 0),
            List.of(
                new CakeLayerInfo("vanilla", 0),
                new CakeLayerInfo("lemon", 0),
                new CakeLayerInfo(STRAWBERRY, 0)));
    try {
      cakeBakingService.bakeNewCake(cake2);
    } catch (CakeBakingException e) {
      LOGGER.error("Cake baking exception", e);
    }
  }
}
