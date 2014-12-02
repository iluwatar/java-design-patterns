package com.iluwatar;

public class Queen implements Royalty{
	private boolean isDrunk = true, isHungry = false, isHappy = false;
	private boolean isFlirty = true, complimentReceived = false;
	
	@Override
	public void getFed() {
		isHungry = false;
	}

	@Override
	public void getDrink() {
		isDrunk = true;
	}
	
	public void receiveCompliments(){
		complimentReceived = true;
	}

	@Override
	public void changeMood() {
		if( complimentReceived && isFlirty && isDrunk ) isHappy = true;
	}

	@Override
	public boolean getMood() {
		return isHappy;
	}
	
	public void setFlirtiness(boolean f){
		this.isFlirty = f;
	}

}
