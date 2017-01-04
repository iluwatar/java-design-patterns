package com.Iluwatar.combinator;

import java.util.List;

public class Warrior {
	private List<Crown> listOfCrowns;
	private List<ButterflyLamp> listOfButterflyLamps;
	private List<Beerstein> listOfBeersteins;
	private List<ElegantMask> listOfElegantMasks;
	private List<GoldenLynx> listOfGoldenLynxes;
	
	public Warrior(List<Crown> listOfCrowns, List<ButterflyLamp> listOfButterflyLamps,
			List<Beerstein> listOfBeersteins, List<ElegantMask> listOfElegantMasks,
			List<GoldenLynx> listOfGoldenLynxes){
		this.listOfCrowns = listOfCrowns;
		this.listOfBeersteins = listOfBeersteins;
		this.listOfButterflyLamps = listOfButterflyLamps;
		this.listOfElegantMasks = listOfElegantMasks;
		this.listOfGoldenLynxes = listOfGoldenLynxes;
	}
	
	public List<Crown> getListOfCrowns(){
		return listOfCrowns;
	}
	
	public List<ButterflyLamp> getListOfButterflyLamps(){
		return listOfButterflyLamps;
	}
	
	public List<Beerstein> getListOfBeersteins(){
		return listOfBeersteins;
	}
	
	public List<ElegantMask> getListOfElegantMasks(){
		return listOfElegantMasks;
	}
	
	public List<GoldenLynx> getListOfGoldenLynxes(){
		return listOfGoldenLynxes;
	}
}
