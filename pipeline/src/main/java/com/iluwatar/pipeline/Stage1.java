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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iluwatar.pipeline;

/**
 * This stage removes the special characters that are in the string.
 */

import static com.iluwatar.pipeline.App.buffer1;
import static com.iluwatar.pipeline.App.buffer2;

/**
 *
 * @author Ayush
 */
public class Stage1 implements Filter{
  
    /**
     * @return void
     */
  @Override
  public void removeChar() {
    String data = (String) buffer1.remove();
    String result = "";
    for (int i = 0;i < data.length();++i) {
      char ch = data.charAt(i);
      boolean cond1 = ch >= 'a' && ch <= 'z';
      boolean cond2 = ch >= 'A' && ch <= 'Z';
      boolean cond3 = ch >= '0' && ch <= '9';
      boolean cond = cond1 || cond2 || cond3 || ch == ' ';
      if (cond) {
        result = result + ch;
      }
    }
    buffer2.add(result);
  } 
    
}
