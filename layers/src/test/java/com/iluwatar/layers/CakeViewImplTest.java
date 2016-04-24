/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Date: 12/15/15 - 10:04 PM
 *
 * @author Jeroen Meulemeester
 */
public class CakeViewImplTest extends StdOutTest {

  /**
   * Verify if the cake view renders the expected result
   */
  @Test
  public void testRender() {

    final List<CakeLayerInfo> layers = new ArrayList<>();
    layers.add(new CakeLayerInfo("layer1", 1000));
    layers.add(new CakeLayerInfo("layer2", 2000));
    layers.add(new CakeLayerInfo("layer3", 3000));

    final List<CakeInfo> cakes = new ArrayList<>();
    final CakeInfo cake = new CakeInfo(new CakeToppingInfo("topping", 1000), layers);
    cakes.add(cake);

    final CakeBakingService bakingService = mock(CakeBakingService.class);
    when(bakingService.getAllCakes()).thenReturn(cakes);

    final CakeViewImpl cakeView = new CakeViewImpl(bakingService);

    verifyZeroInteractions(getStdOutMock());

    cakeView.render();
    verify(getStdOutMock(), times(1)).println(cake);

  }

}
