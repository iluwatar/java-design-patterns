package com.iluwatar.layers;

import java.util.Arrays;

public class App {
	
	public static void main(String[] args) {
		CakeBakingService cakeBakingService = new CakeBakingServiceImpl();
		initializeData(cakeBakingService);
		CakeView cakeView = new CakeView(cakeBakingService);
		cakeView.render();
	}
	
	private static void initializeData(CakeBakingService cakeBakingService) {
		cakeBakingService.saveNewLayer(new CakeLayerInfo("chocolate", 1200));
		cakeBakingService.saveNewLayer(new CakeLayerInfo("banana", 900));
		cakeBakingService.saveNewLayer(new CakeLayerInfo("strawberry", 950));
		cakeBakingService.getAllLayers().stream().forEach((layer) -> System.out.println(layer));
		
		cakeBakingService.saveNewTopping(new CakeToppingInfo("candies", 350));
		cakeBakingService.getAllToppings().stream().forEach((topping) -> System.out.println(topping));

		CakeInfo cakeInfo = new CakeInfo(new CakeToppingInfo("candies", 0),
				Arrays.asList(new CakeLayerInfo("chocolate", 0), new CakeLayerInfo("banana", 0),
						new CakeLayerInfo("strawberry", 0)));
		try {
			cakeBakingService.bakeNewCake(cakeInfo);
		} catch (CakeBakingException e) {
			e.printStackTrace();
		}
	}
}
