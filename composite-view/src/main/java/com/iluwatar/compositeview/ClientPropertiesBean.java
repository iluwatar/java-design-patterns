/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
