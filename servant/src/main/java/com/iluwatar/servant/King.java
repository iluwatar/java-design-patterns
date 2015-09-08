package com.iluwatar.servant;

/**
 * 
 * King
 *
 */
public class King implements Royalty {
	
    private boolean isDrunk;
    private boolean isHungry = true;
    private boolean isHappy;
    private boolean complimentReceived;

    @Override
    public void getFed() {
        isHungry = false;
    }

    @Override
    public void getDrink() {
        isDrunk = true;
    }

    public void receiveCompliments() {
        complimentReceived = true;
    }

    @Override
    public void changeMood() {
        if (!isHungry && isDrunk) isHappy = true;
        if (complimentReceived) isHappy = false;
    }

    @Override
    public boolean getMood() {
        return isHappy;
    }
}
