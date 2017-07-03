/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.featuretoggle.pattern;

import com.iluwatar.featuretoggle.user.User;

/**
 * Simple interfaces to allow the calling of the method to generate the welcome message for a given user. While there is
 * a helper method to gather the the status of the feature toggle. In some cases there is no need for the
 * {@link Service#isEnhanced()} in {@link com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion}
 * where the toggle is determined by the actual {@link User}.
 *
 * @see com.iluwatar.featuretoggle.pattern.propertiesversion.PropertiesFeatureToggleVersion
 * @see com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion
 * @see User
 */
public interface Service {

  /**
   * Generates a welcome message for the passed user.
   *
   * @param user the {@link User} to be used if the message is to be personalised.
   * @return Generated {@link String} welcome message
   */
  String getWelcomeMessage(User user);

  /**
   * Returns if the welcome message to be displayed will be the enhanced version.
   *
   * @return Boolean {@value true} if enhanced.
   */
  boolean isEnhanced();

}
