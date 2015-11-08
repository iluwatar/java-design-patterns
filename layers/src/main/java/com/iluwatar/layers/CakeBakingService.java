package com.iluwatar.layers;

import java.util.List;

/**
 * 
 * Service for cake baking operations
 *
 */
public interface CakeBakingService {
	
	/**
	 * Bakes new cake according to parameters
	 * @param cakeInfo
	 * @throws CakeBakingException
	 */
	void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException;
	
	/**
	 * Get all cakes
	 * @return
	 */
	List<CakeInfo> getAllCakes();

	/**
	 * Store new cake topping
	 * @param toppingInfo
	 */
	void saveNewTopping(CakeToppingInfo toppingInfo);

	/**
	 * Get available cake toppings
	 * @return
	 */
	List<CakeToppingInfo> getAvailableToppings();
	
	/**
	 * Add new cake layer
	 * @param layerInfo
	 */
	void saveNewLayer(CakeLayerInfo layerInfo);
	
	/**
	 * Get available cake layers
	 * @return
	 */
	List<CakeLayerInfo> getAvailableLayers();
}
