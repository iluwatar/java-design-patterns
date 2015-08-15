package com.iluwatar.layers;

import java.util.List;

public interface CakeBakingService {
	
	void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException;
	
	List<CakeInfo> getAllCakes();

	void saveNewTopping(CakeToppingInfo toppingInfo);

	List<CakeToppingInfo> getAvailableToppings();
	
	void saveNewLayer(CakeLayerInfo layerInfo);
	
	List<CakeLayerInfo> getAvailableLayers();
}
