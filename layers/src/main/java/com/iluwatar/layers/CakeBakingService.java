package com.iluwatar.layers;

public interface CakeBakingService {
	
	void bakeNewCake(CakeInfo cakeInfo);

	void saveNewTopping(CakeToppingInfo toppingInfo);
	
	void saveNewLayer(CakeLayerInfo layerInfo);
}
