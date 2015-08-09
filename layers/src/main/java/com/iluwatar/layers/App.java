package com.iluwatar.layers;

public class App {
	
	public static void main(String[] args) {

		CakeBakingService service = new CakeBakingServiceImpl();
		service.saveNewLayer(new CakeLayerInfo("foo", 1));
		service.saveNewLayer(new CakeLayerInfo("bar", 2));
		service.getAllLayers().stream().forEach((layer) -> System.out.println(layer));
		
		service.saveNewTopping(new CakeToppingInfo("hoi", 11));
		service.getAllToppings().stream().forEach((topping) -> System.out.println(topping));

	}
}
