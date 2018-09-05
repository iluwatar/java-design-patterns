/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.acyclicvisitor;

/**
 * The Acyclic Visitor pattern allows new functions to be added to existing class 
 * hierarchies without affecting those hierarchies, and without creating the dependency 
 * cycles that are inherent to the GoF Visitor pattern, by making the Visitor base class 
 * degenerate
 * <p>
 * In this example the visitor base class is {@link ModemVisitor}. The base class of the 
 * visited hierarchy is {@link Modem} and has two children {@link Hayes} and {@link Zoom} 
 * each one having its own visitor interface {@link HayesVisitor} and {@link ZoomVisitor} 
 * respectively. {@link ConfigureForUnixVisitor} and {@link ConfigureForDosVisitor} 
 * implement each derivative's visit method only if it is required
 *
 * 非循环访问者模式：
 *
 * 1、所有访问者的基类 ModemVisitor
 * 2、existing class hierarchies 的抽象类 Modem， 定义了接受访问者的方法 accept
 * 3、existing class hierarchies 的实现类 Hayes/Zoom 继承Modem,实现 accept,执行 访问者的visit(this) （× 传入自己，访问自己，实现了访问者模式的核心目的，
 * 在不改变existing class hierarchies 的情况下，增强）
 *
 * 在 Acyclic Visitor 中，Element 只接受自己匹配的 visitor
 *
 * 4、通过接口多继承，AllModemVisitor接口汇总所有的访问者方法，ConfigureForDosVisitor ,具体访问者只实现需要的访问方法
 *
 *
 *
 *
 */
public class App {
  
  /**
   * Program's entry point
   */
  
  public static void main(String[] args) {  
    ConfigureForUnixVisitor conUnix = new ConfigureForUnixVisitor();
    ConfigureForDosVisitor conDos = new ConfigureForDosVisitor();
    
    Zoom zoom = new Zoom();
    Hayes hayes = new Hayes();
    
    hayes.accept(conDos); // Hayes modem with Unix configurator
    zoom.accept(conDos); // Zoom modem with Dos configurator
    hayes.accept(conUnix); // Hayes modem with Unix configurator
    zoom.accept(conUnix); // Zoom modem with Unix configurator   
  }
}
