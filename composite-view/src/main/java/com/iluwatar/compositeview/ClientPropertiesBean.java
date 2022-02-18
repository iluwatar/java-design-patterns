package com.iluwatar.compositeview;

import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * A Java beans class that parses a http request and stores parameters.
 * Java beans used in JSP's to dynamically include elements in view.
 * DEFAULT_NAME = a constant, default name to be used for the default constructor
 * worldNewsInterest = whether current request has world news interest
 * sportsInterest = whether current request has a sportsInterest
 * businessInterest = whether current request has a businessInterest
 * scienceNewsInterest = whether current request has a scienceNewsInterest
 */
@Getter
@Setter
@NoArgsConstructor
public class ClientPropertiesBean implements Serializable {

  private static final String WORLD_PARAM = "world";
  private static final String SCIENCE_PARAM = "sci";
  private static final String SPORTS_PARAM = "sport";
  private static final String BUSINESS_PARAM = "bus";
  private static final String NAME_PARAM = "name";

  private static final  String DEFAULT_NAME = "DEFAULT_NAME";
  private boolean worldNewsInterest = true;
  private boolean sportsInterest = true;
  private boolean businessInterest = true;
  private boolean scienceNewsInterest = true;
  private String name = DEFAULT_NAME;

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
}
