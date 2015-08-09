package com.iluwatar.layers;

import java.util.List;

public interface CakeBakingService {
	
	void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException;

	void saveNewTopping(CakeToppingInfo toppingInfo);

	List<CakeToppingInfo> getAllToppings();
	
	void saveNewLayer(CakeLayerInfo layerInfo);
	
	List<CakeLayerInfo> getAllLayers();
}
