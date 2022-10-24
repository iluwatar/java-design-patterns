package com.iluwatar.ruleengine;


public class Candidate {
    // Add private fields here
    private int lengthOfCurrentResidence;
    private String englishProficiency;
    private boolean isNominatedOccupationOnTheList;
    private boolean isSmallBusinessOwner;
    private int smallBusinessTurnover;
    private int Length_of_ACT_employment;

    public int getYears_of_Study() {
        return Years_of_Study;
    }

    public void setYears_of_Study(int years_of_Study) {
        Years_of_Study = years_of_Study;
    }

    private int  Years_of_Study;

    private String Tertiary_Qualification;
    private int Assets;
    private String Close_Family_Ties;

    public int getLength_of_ACT_employment() {
        return Length_of_ACT_employment;
    }

    public void setLength_of_ACT_employment(int length_of_ACT_employment) {
        Length_of_ACT_employment = length_of_ACT_employment;
    }

    public String getTertiary_Qualification() {
        return Tertiary_Qualification;
    }

    public void setTertiary_Qualification(String  tertiary_Qualification) {
        Tertiary_Qualification = tertiary_Qualification;
    }

    public int getAssets() {
        return Assets;
    }

    public void setAssets(int assets) {
        Assets = assets;
    }

    public String getClose_Family_Ties() {
        return Close_Family_Ties;
    }

    public void setClose_Family_Ties(String close_Family_Ties) {
        Close_Family_Ties = close_Family_Ties;
    }


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
