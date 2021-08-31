/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.proxy;

/**
 * A proxy, in its most general form, is a class functioning as an interface to something else. The
 * proxy could interface to anything: a network connection, a large object in memory, a file, or
 * some other resource that is expensive or impossible to duplicate. In short, a proxy is a wrapper
 * or agent object that is being called by the client to access the real serving object behind the
 * scenes.
 * 代理，在其最一般的形式中，是作为其他东西接口的类。代理可以连接到任何东西:网络连接、内存中的大对象、文件或其他一些昂贵或无法复制的资源。
 * 简而言之，代理是客户机调用的包装器或代理对象，用于在幕后访问实际的服务对象。
 *
 * <p>The Proxy design pattern allows you to provide an interface to other objects by creating a
 * wrapper class as the proxy. The wrapper class, which is the proxy, can add additional
 * functionality to the object of interest without changing the object's code.
 * 通过创建包装器类作为代理，代理设计模式允许您为其他对象提供接口。包装器类(即代理)可以向感兴趣的对象添加额外的功能，而无需更改对象的代码。
 *
 * <p>In this example the proxy ({@link WizardTowerProxy}) controls access to the actual object (
 * {@link IvoryTower}).
 * 在这个例子中，代理({@link WizardTowerProxy})控制对实际对象({@link IvoryTower})的访问。
 */
public class App {

  /**
   * Program entry point.
   */
  public static void main(String[] args) {

    var proxy = new WizardTowerProxy(new IvoryTower());
    proxy.enter(new Wizard("Red wizard"));
    proxy.enter(new Wizard("White wizard"));
    proxy.enter(new Wizard("Black wizard"));
    proxy.enter(new Wizard("Green wizard"));
    proxy.enter(new Wizard("Brown wizard"));

  }
}
