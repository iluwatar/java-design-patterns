/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.prototype;

/**
 * ElfWarlord.
 */
public class ElfWarlord extends Warlord {

  private String helpType;

  public ElfWarlord(String helpType) {
    this.helpType = helpType;
  }

  public ElfWarlord(ElfWarlord elfWarlord) {
    super(elfWarlord);
    this.helpType = elfWarlord.helpType;
  }

  @Override
  public ElfWarlord copy() {
    return new ElfWarlord(this);
  }

  @Override
  public String toString() {
    return "Elven warlord helps in " + helpType;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ElfWarlord other = (ElfWarlord) obj;
    if (helpType == null) {
      if (other.helpType != null) {
        return false;
      }
    } else if (!helpType.equals(other.helpType)) {
      return false;
    }
    return true;
  }
}