/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.singleton;

import java.io.Serializable;

/**
 * The Initialize-on-demand-holder idiom is a secure way of creating lazy initialized singleton
 * object in Java. refer to "The CERT Oracle Secure Coding Standard for Java" By Dhruv Mohindra,
 * Robert C. Seacord p.378
 * <p/>
 * Singleton objects usually are heavy to create and sometimes need to serialize them. This class
 * also shows how to preserve singleton in serialized version of singleton.
 *
 * @author mortezaadi@gmail.com
 */
public class InitializingOnDemandHolderIdiom implements Serializable {

  private static final long serialVersionUID = 1L;

  private InitializingOnDemandHolderIdiom() {}

  public static InitializingOnDemandHolderIdiom getInstance() {
    return HelperHolder.INSTANCE;
  }

  protected Object readResolve() {
    return getInstance();
  }

  private static class HelperHolder {
    public static final InitializingOnDemandHolderIdiom INSTANCE =
        new InitializingOnDemandHolderIdiom();
  }

}
