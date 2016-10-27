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
package com.iluwatar.pipeline;

/**
 * The Pipeline pattern uses parallel tasks and concurrent queues to process a
 * sequence of input values. Each task implements a stage of the pipeline, and 
 * the queues act as buffers that allow the stages of the pipeline to execute 
 * concurrently, even though the values are processed in order. In this program,
 * sentences are decoded in four stages, where, each stage is dedicated to just
 * one single task.
 */

import java.util.Queue;

/**
 *
 * @author Ayush
 */
public class App {
  
  public static Queue buffer1;
  public static Queue buffer2;
  public static Queue buffer3;
  public static Queue buffer4;
    /**
     * Program entry point
     */
  public static void main(String[] args) throws InterruptedException {
    Pipeline pipeline = new Pipeline();
    pipeline.start();
  }  
}
