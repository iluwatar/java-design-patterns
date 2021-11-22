package com.iluwatar.compositeview;

import javax.swing.table.TableRowSorter;
import java.io.Serializable;

/**
 *A Java beans class that parses a request to the server to dynamically include certain elements in the view.
 */
public class ClientPropertiesBean implements Serializable {
    private boolean worldNewsInterest;
    private boolean sportsInterest;
    private boolean businessInterest;
    private boolean scienceNewsInterest;
    private String name;

    public ClientPropertiesBean(){
        worldNewsInterest = true;
        sportsInterest = true;
        businessInterest = true;
        scienceNewsInterest = true;
        name = "";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBusinessInterest() {
        return businessInterest;
    }

    public void setBusinessInterest(boolean businessInterest) {
        this.businessInterest = businessInterest;
    }

    public boolean isWorldNewsInterest() {
        return worldNewsInterest;
    }

    public void setWorldNewsInterest(boolean worldNewsInterest) {
        this.worldNewsInterest = worldNewsInterest;
    }

    public boolean isSportsInterest() {
        return sportsInterest;
    }

    public void setSportsInterest(boolean sportsInterest) {
        this.sportsInterest = sportsInterest;
    }

    public boolean isScienceNewsInterest() {
        return scienceNewsInterest;
    }

    public void setScienceNewsInterest(boolean scienceNewsInterest) {
        this.scienceNewsInterest = scienceNewsInterest;
    }
}
