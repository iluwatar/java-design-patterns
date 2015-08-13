package com.iluwatar.layers;

public class CakeView {

	private CakeBakingService cakeBakingService;

	public CakeView(CakeBakingService cakeBakingService) {
		this.cakeBakingService = cakeBakingService;
	}
	
	public void render() {
		cakeBakingService.getAllCakes().stream().forEach((cake) -> System.out.println(cake));
	}
}
