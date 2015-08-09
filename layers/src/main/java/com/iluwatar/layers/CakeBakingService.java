package com.iluwatar.layers;

public interface CakeBakingService {
	
	void bakeNewCake(String topping, String layer1, String layer2, String layer3);

	void addNewTopping(CakeTopping topping);
	
	void addNewLayer(CakeLayer layer);
}
