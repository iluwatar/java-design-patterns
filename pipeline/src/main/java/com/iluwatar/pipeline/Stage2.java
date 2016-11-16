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
 * This stage will remove the numbers from the string.
 */

import static com.iluwatar.pipeline.App.buffer2;
import static com.iluwatar.pipeline.App.buffer3;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ayush
 */
public class Stage2 implements Filter, Runnable {
  
  private Thread t;
  private final String threadName;
  
  Stage2(String name) {
    threadName = name;
    //System.out.println("Creating " +  threadName );
  }
    /**
     * @return void
     */
  @Override
  public void removeChar() {
    try {
      if (!buffer2.isEmpty()) {
        Thread.sleep(0);
        String data = (String) buffer2.remove();
        String result = "";
        for (int i = 0;i < data.length();++i) {
          char ch = data.charAt(i);
          boolean cond1 = ch >= 'a' && ch <= 'z';
          boolean cond2 = ch >= 'A' && ch <= 'Z';
          boolean cond = cond1 || cond2 || ch == ' ';
          if (cond) {
            result = result + ch;
          }
        }
        System.out.println("Data filtered through 2nd stage");
        buffer3.add(result);
      }
    } catch (InterruptedException | NoSuchElementException ex) {
      Logger.getLogger(Stage2.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void run() {
    removeChar();
  }
  
  @Override
  public void start() {
    if (t == null) {
      t = new Thread(this, threadName);
      t.start();
    }
  }  
}
