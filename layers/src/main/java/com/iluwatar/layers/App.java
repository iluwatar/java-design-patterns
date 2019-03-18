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

import java.util.Arrays;

/**
 * 
 * Layers is an architectural style where software responsibilities are divided among the different layers of the
 * application.
 * <p>
 * This example demonstrates a traditional 3-layer architecture consisting of data access layer, business layer and
 * presentation layer.
 * <p>
 * The data access layer is formed of Spring Data repositories <code>CakeDao</code>, <code>CakeToppingDao</code> and
 * <code>CakeLayerDao</code>. The repositories can be used for CRUD operations on cakes, cake toppings and cake layers
 * respectively.
 * <p>
 * The business layer is built on top of the data access layer. <code>CakeBakingService</code> offers methods to
 * retrieve available cake toppings and cake layers and baked cakes. Also the service is used to create new cakes out of
 * cake toppings and cake layers.
 * <p>
 * The presentation layer is built on the business layer and in this example it simply lists the cakes that have been
 * baked.
 * <p>
 * We have applied so called strict layering which means that the layers can only access the classes directly beneath
 * them. This leads the solution to create an additional set of DTOs ( <code>CakeInfo</code>,
 * <code>CakeToppingInfo</code>, <code>CakeLayerInfo</code>) to translate data between layers. In other words,
 * <code>CakeBakingService</code> cannot return entities ( <code>Cake</code>, <code>CakeTopping</code>,
 * <code>CakeLayer</code>) directly since these reside on data access layer but instead translates these into business
 * layer DTOs (<code>CakeInfo</code>, <code>CakeToppingInfo</code>, <code>CakeLayerInfo</code>) and returns them
 * instead. This way the presentation layer does not have any knowledge of other layers than the business layer and thus
 * is not affected by changes to them.
 *
 * @see Cake
 * @see CakeTopping
 * @see CakeLayer
 * @see CakeDao
 * @see CakeToppingDao
 * @see CakeLayerDao
 * @see CakeBakingService
 * @see CakeInfo
 * @see CakeToppingInfo
 * @see CakeLayerInfo
 *
 */
public class App {

  private static CakeBakingService cakeBakingService = new CakeBakingServiceImpl();

  /**
   * Application entry point
   * 
   * @param args Command line parameters
   */
  public static void main(String[] args) {

    // initialize example data
    initializeData(cakeBakingService);

    // create view and render it
    CakeViewImpl cakeView = new CakeViewImpl(cakeBakingService);
    cakeView.render();
  }

  /**
   * Initializes the example data
   */
  private static void initializeData(CakeBakingService cakeBakingService) {
    cakeBakingService.saveNewLayer(new CakeLayerInfo("chocolate", 1200));
    cakeBakingService.saveNewLayer(new CakeLayerInfo("banana", 900));
    cakeBakingService.saveNewLayer(new CakeLayerInfo("strawberry", 950));
    cakeBakingService.saveNewLayer(new CakeLayerInfo("lemon", 950));
    cakeBakingService.saveNewLayer(new CakeLayerInfo("vanilla", 950));
    cakeBakingService.saveNewLayer(new CakeLayerInfo("strawberry", 950));

    cakeBakingService.saveNewTopping(new CakeToppingInfo("candies", 350));
    cakeBakingService.saveNewTopping(new CakeToppingInfo("cherry", 350));

    CakeInfo cake1 =
        new CakeInfo(new CakeToppingInfo("candies", 0), Arrays.asList(new CakeLayerInfo(
            "chocolate", 0), new CakeLayerInfo("banana", 0), new CakeLayerInfo("strawberry", 0)));
    try {
      cakeBakingService.bakeNewCake(cake1);
    } catch (CakeBakingException e) {
      e.printStackTrace();
    }
    CakeInfo cake2 =
        new CakeInfo(new CakeToppingInfo("cherry", 0), Arrays.asList(
            new CakeLayerInfo("vanilla", 0), new CakeLayerInfo("lemon", 0), new CakeLayerInfo(
                "strawberry", 0)));
    try {
      cakeBakingService.bakeNewCake(cake2);
    } catch (CakeBakingException e) {
      e.printStackTrace();
    }
  }
}
