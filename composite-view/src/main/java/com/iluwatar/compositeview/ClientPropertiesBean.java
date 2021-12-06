package com.iluwatar.compositeview;

import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * A Java beans class that parses a http request and stores parameters.
 * Java beans used in JSP's to dynamically include elements in view.
 * DEFAULT_NAME = a constant, default name to be used for the default constructor
 * worldNewsInterest = whether current request has world news interest
 * sportsInterest = whether current request has a sportsInterest
 * businessInterest = whether current request has a businessInterest
 * scienceNewsInterest = whether current request has a scienceNewsInterest
 */
public class ClientPropertiesBean implements Serializable {

  private static final String WORLD_PARAM = "world";
  private static final String SCIENCE_PARAM = "sci";
  private static final String SPORTS_PARAM = "sport";
  private static final String BUSINESS_PARAM = "bus";
  private static final String NAME_PARAM = "name";

  private static final  String DEFAULT_NAME = "DEFAULT_NAME";
  private boolean worldNewsInterest;
  private boolean sportsInterest;
  private boolean businessInterest;
  private boolean scienceNewsInterest;
  private String name;

  /**
   * Default constructor that takes no arguments.
   * all booleans set to true, and name is set to DEFAULT_NAME
   */

  public ClientPropertiesBean() {
    worldNewsInterest = true;
    sportsInterest = true;
    businessInterest = true;
    scienceNewsInterest = true;
    name = DEFAULT_NAME;

  }

  /**
   * Constructor that parses an HttpServletRequest and stores all the request parameters.
   *
   * @param req the HttpServletRequest object that is passed in
   */
  public ClientPropertiesBean(HttpServletRequest req) {
    worldNewsInterest = Boolean.parseBoolean(req.getParameter(WORLD_PARAM));
    sportsInterest = Boolean.parseBoolean(req.getParameter(SPORTS_PARAM));
    businessInterest = Boolean.parseBoolean(req.getParameter(BUSINESS_PARAM));
    scienceNewsInterest = Boolean.parseBoolean(req.getParameter(SCIENCE_PARAM));
    String tempName = req.getParameter(NAME_PARAM);
    if (tempName == null || tempName.equals("")) {
      tempName = DEFAULT_NAME;
    }
    name = tempName;
  }

  /**
   * Getter for name.
   *
   * @return the name string
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for name.
   *
   * @param toName the string to set as the name
   */

  public void setName(String toName) {
    this.name = toName;
  }

  /**
   * Getter for businessInterest.
   *
   * @return boolean for businessInterest
   */

  public boolean isBusinessInterest() {
    return businessInterest;
  }

  /**
   * Setter for businessInterest.
   *
   * @param toBusinessInterest boolean to set businessInterest to
   */

  public void setBusinessInterest(boolean toBusinessInterest) {
    this.businessInterest = toBusinessInterest;
  }

  /**
   * Getter for worldNewsInterest.
   *
   * @return the boolean for worldNewsInterest
   */

  public boolean isWorldNewsInterest() {
    return worldNewsInterest;
  }

  /**
   * Setter for worldNewsInterest.
   *
   * @param toWorldNewsInterest boolean to set worldNewsInterest to
   */

  public void setWorldNewsInterest(boolean toWorldNewsInterest) {
    this.worldNewsInterest = toWorldNewsInterest;
  }

  /**
   * Getter for sportsInterest.
   *
   * @return the boolean for sportsInterest
   */

  public boolean isSportsInterest() {
    return sportsInterest;
  }

  /**
   * Setter for sportsInterest.
   *
   * @param toSportsInterest boolean to set sportsInterest to
   */

  public void setSportsInterest(boolean toSportsInterest) {
    this.sportsInterest = toSportsInterest;
  }

  /**
   * Getter for scienceNewsInterest.
   *
   * @return the boolean for scienceNewsInterest
   */

  public boolean isScienceNewsInterest() {
    return scienceNewsInterest;
  }

  /**
   * Setter for scienceNewsInterest.
   *
   * @param toScienceNewsInterest the boolean to set scienceNewsInterest to
   */

  public void setScienceNewsInterest(boolean toScienceNewsInterest) {
    this.scienceNewsInterest = toScienceNewsInterest;
  }
}
