package com.iluwatar.layers;

import java.util.Arrays;

public class App {

	private static CakeBakingService cakeBakingService = new CakeBakingServiceImpl();
	
	public static void main(String[] args) {
		
		// initialize example data
		initializeData(cakeBakingService);
		
		// create view and render it
		CakeViewImpl cakeView = new CakeViewImpl(cakeBakingService);
		cakeView.render();
	}
	
	private static void initializeData(CakeBakingService cakeBakingService) {
		cakeBakingService.saveNewLayer(new CakeLayerInfo("chocolate", 1200));
		cakeBakingService.saveNewLayer(new CakeLayerInfo("banana", 900));
		cakeBakingService.saveNewLayer(new CakeLayerInfo("strawberry", 950));
		cakeBakingService.saveNewLayer(new CakeLayerInfo("lemon", 950));
		cakeBakingService.saveNewLayer(new CakeLayerInfo("vanilla", 950));
		cakeBakingService.saveNewLayer(new CakeLayerInfo("strawberry", 950));
		
		cakeBakingService.saveNewTopping(new CakeToppingInfo("candies", 350));
		cakeBakingService.saveNewTopping(new CakeToppingInfo("cherry", 350));

		CakeInfo cake1 = new CakeInfo(new CakeToppingInfo("candies", 0),
				Arrays.asList(new CakeLayerInfo("chocolate", 0), new CakeLayerInfo("banana", 0),
						new CakeLayerInfo("strawberry", 0)));
		try {
			cakeBakingService.bakeNewCake(cake1);
		} catch (CakeBakingException e) {
			e.printStackTrace();
		}
		CakeInfo cake2 = new CakeInfo(new CakeToppingInfo("cherry", 0),
				Arrays.asList(new CakeLayerInfo("vanilla", 0), new CakeLayerInfo("lemon", 0),
						new CakeLayerInfo("strawberry", 0)));
		try {
			cakeBakingService.bakeNewCake(cake2);
		} catch (CakeBakingException e) {
			e.printStackTrace();
		}
	}
}
