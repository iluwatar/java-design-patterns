package com.iluwatar.servant;

/**
 * 
 * Queen
 *
 */
public class Queen implements Royalty {
	
    private boolean isDrunk = true;
    private boolean isHungry;
    private boolean isHappy;
    private boolean isFlirty = true;
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
        if (complimentReceived && isFlirty && isDrunk) isHappy = true;
    }

    @Override
    public boolean getMood() {
        return isHappy;
    }

    public void setFlirtiness(boolean f) {
        this.isFlirty = f;
    }

}
