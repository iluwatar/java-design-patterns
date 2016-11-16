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

import static com.iluwatar.pipeline.App.buffer1;
import static com.iluwatar.pipeline.App.buffer2;
import static com.iluwatar.pipeline.App.buffer3;
import static com.iluwatar.pipeline.App.buffer4;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Ayush
 */
public class Pipeline {
    
  private Filter stage1;
  private Filter stage2;
  private Process stage3;
  private Print stage4;
  private Queue data;
    
    /**
     * @return void
     */
  public void start() throws InterruptedException {
    buffersInitialize();
    processData();
  }

  /**
   * Function entry 
   */
  public void buffersInitialize() {
    Data getData = new Data();
    data = getData.data();
    System.out.println("Data received");
    
    
    
    buffer1 = new LinkedList();
    buffer2 = new LinkedList();
    buffer3 = new LinkedList();
    buffer4 = new LinkedList();
    System.out.println("Buffers initialised");
  }

    /**
     * Function entry
     */
  public void processData() throws InterruptedException {
    int time = 1;
    
    /**
     * Each iteration of the loop specify a single instance where each process
     * is carried out simultaneously.
     */ 

    while (!(data.isEmpty())) {
      createObject();  
      System.out.println("\nTime " + time + " : ");
      String newData = (String) data.remove();
      buffer1.add(newData);     
      stage1.start();
      stage2.start(); 
      stage3.start(); 
      stage4.start();
      time++;
      Thread.sleep(1000);
    }
    
    while (!buffer4.isEmpty()) {
      createObject();
      System.out.println("\nTime " + time + " : ");
      stage1.start();
      stage2.start();
      stage3.start();
      stage4.start();
      time++;
      Thread.sleep(1000);
    }
  }
  
    /**
     * Function entry
     */
  public void createObject() {
    stage1 = new Stage1("Stage 1");
    stage2 = new Stage2("Stage 2");
    stage3 = new Stage3("Stage 3");
    stage4 = new Stage4("Stage 4");  
  }
}
