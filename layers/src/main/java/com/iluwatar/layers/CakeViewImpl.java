package com.iluwatar.layers;

/**
 * 
 * View implementation for displaying cakes
 *
 */
public class CakeViewImpl implements View {

	private CakeBakingService cakeBakingService;

	public CakeViewImpl(CakeBakingService cakeBakingService) {
		this.cakeBakingService = cakeBakingService;
	}
	
	public void render() {
		cakeBakingService.getAllCakes().stream().forEach((cake) -> System.out.println(cake));
	}
}
