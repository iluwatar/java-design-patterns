package com.iluwatar;

public class King implements Royalty{
	private boolean isDrunk = false, isHungry = true, isHappy = false;
	private boolean complimentReceived = false;
	
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
		if(!isHungry && isDrunk) isHappy = true;
		if( complimentReceived ) isHappy = false;
	}

	@Override
	public boolean getMood() {
		return isHappy;
	}
}
