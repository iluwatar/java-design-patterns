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
package com.iluwatar.adapter;

/**
 * An adapter helps two incompatible interfaces to work together. This is the real world definition
 * for an adapter. Interfaces may be incompatible but the inner functionality should suit the need.
 * The Adapter design pattern allows otherwise incompatible classes to work together by converting
 * the interface of one class into an interface expected by the clients.
 *
 * <p>
 * There are two variations of the Adapter pattern: The class adapter implements the adaptee's
 * interface whereas the object adapter uses composition to contain the adapter in the adapter
 * object. This example uses the object adapter approach.
 *
 * <p>
 * The Adapter ({@link FishingBoatAdapter}) converts the interface of the adaptee class (
 * {@link FishingBoat}) into a suitable one expected by the client ( {@link RowingBoat} ).
 *
 * <p>
 * The story of this implementation is this. <br>
 * Pirates are coming! we need a {@link RowingBoat} to flee! We have a {@link FishingBoat} and our
 * captain. We have no time to make up a new ship! we need to reuse this {@link FishingBoat}. The
 * captain needs a rowing boat which he can operate. The spec is in {@link RowingBoat}. We will
 * use the Adapter pattern to reuse {@link FishingBoat}.
 *
 * 适配器模式：
 * 1、适配器用于 组装不兼容的类型， 类似与装饰器模式，但是适配器用于不兼容的类型
 * 2、FishingBoatAdapter 适配器实现了 RowingBoat 接口，但是实现方法中使用的时 适配对象来执行方法。
 *  有 类适配器和对象适配器 两种，例子为 对象适配器，适配器初始化了对象，作为对象来使用
 *
 *
 *
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    // The captain can only operate rowing boats but with adapter he is able to use fishing boats as well
    Captain captain = new Captain(new FishingBoatAdapter());
    captain.row();
  }
}
