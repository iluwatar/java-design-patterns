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
