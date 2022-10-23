package com.iluwatar.ruleengine;


public class Candidate {
    // Add private fields here
    private int lengthOfCurrentResidence;
    private String englishProficiency;
    private boolean isNominatedOccupationOnTheList;
    private boolean isSmallBusinessOwner;
    private int smallBusinessTurnover;

    public int getLengthOfCurrentResidence() {
        return lengthOfCurrentResidence;
    }

    public void setLengthOfCurrentResidence(int lengthOfCurrentResidence) {
        this.lengthOfCurrentResidence = lengthOfCurrentResidence;
    }

    public String getEnglishProficiency() {
        return englishProficiency;
    }

    public void setEnglishProficiency(String englishProficiency) {
        this.englishProficiency = englishProficiency;
    }

    public boolean isNominatedOccupationOnTheList() {
        return isNominatedOccupationOnTheList;
    }

    public void setNominatedOccupationOnTheList(boolean nominatedOccupationOnTheList) {
        isNominatedOccupationOnTheList = nominatedOccupationOnTheList;
    }

    public boolean isSmallBusinessOwner() {
        return isSmallBusinessOwner;
    }

    public void setSmallBusinessOwner(boolean smallBusinessOwner) {
        isSmallBusinessOwner = smallBusinessOwner;
    }

    public int getSmallBusinessTurnover() {
        return smallBusinessTurnover;
    }

    public void setSmallBusinessTurnover(int smallBusinessTurnover) {
        this.smallBusinessTurnover = smallBusinessTurnover;
    }

    // Remember setters and getters
}
