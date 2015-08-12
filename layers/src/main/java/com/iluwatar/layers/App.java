package com.iluwatar.layers;

import java.util.Arrays;

public class App {
	
	public static void main(String[] args) {

		CakeBakingService service = new CakeBakingServiceImpl();
		service.saveNewLayer(new CakeLayerInfo("chocolate", 1200));
		service.saveNewLayer(new CakeLayerInfo("banana", 900));
		service.saveNewLayer(new CakeLayerInfo("strawberry", 950));
		service.getAllLayers().stream().forEach((layer) -> System.out.println(layer));
		
		service.saveNewTopping(new CakeToppingInfo("candies", 350));
		service.getAllToppings().stream().forEach((topping) -> System.out.println(topping));

		CakeInfo cakeInfo = new CakeInfo(new CakeToppingInfo("candies", 0),
				Arrays.asList(new CakeLayerInfo("chocolate", 0), new CakeLayerInfo("chocolate", 0),
						new CakeLayerInfo("chocolate", 0)));
		try {
			service.bakeNewCake(cakeInfo);
		} catch (CakeBakingException e) {
			e.printStackTrace();
		}
	}
}
