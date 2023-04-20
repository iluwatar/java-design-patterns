/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Singleton pattern ensures that the class can have only one existing instance per Java
 * classloader instance and provides global access to it.</p>
 * 单例模式确保每个Java类加载器实例只能有一个现有实例，并提供对它的全局访问。
 *
 * <p>One of the risks of this pattern is that bugs resulting from setting a singleton up in a
 * distributed environment can be tricky to debug since it will work fine if you debug with a
 * single classloader. Additionally, these problems can crop up a while after the implementation of
 * a singleton, since they may start synchronous and only become async with time, so it may
 * not be clear why you are seeing certain changes in behavior.</p>
 * 这种模式的风险之一是，在分布式环境中设置单例会导致错误，调试时可能会很棘手，因为如果使用单个类加载器进行调试，它会工作得很好。
 * 此外，这些问题可能会在实现单例后一段时间突然出现，因为它们可能会开始同步，并随着时间的推移而变成异步，所以您可能不清楚为什么会看到行为中的某些变化
 *
 * <p>There are many ways to implement the Singleton. The first one is the eagerly initialized
 * instance in {@link IvoryTower}. Eager initialization implies that the implementation is thread
 * safe. If you can afford to give up control of the instantiation moment, then this implementation
 * will suit you fine.</p>
 * 实现Singleton有很多方法。第一个是在{@link IvoryTower}中急切地初始化的实例。紧急初始化意味着实现是线程安全的。
 * 如果您能够放弃对实例化时刻的控制，那么这个实现将非常适合您
 *
 * <p>The other option to implement eagerly initialized Singleton is enum-based Singleton. The
 * example is found in {@link EnumIvoryTower}. At first glance, the code looks short and simple.
 * However, you should be aware of the downsides including committing to implementation strategy,
 * extending the enum class, serializability, and restrictions to coding. These are extensively
 * discussed in Stack Overflow: http://programmers.stackexchange.com/questions/179386/what-are-the-downsides-of-implementing
 * -a-singleton-with-javas-enum</p>
 * 实现急切地初始化单例的另一个选项是基于枚举的单例。这个例子可以在{@link EnumIvoryTower}中找到。乍一看，代码看起来简短而简单。
 * 但是，您应该意识到其缺点，包括承诺实现策略、扩展枚举类、可序列化性和编码限制。
 * 这些在Stack Overflow: http://programmers.stackexchange.com/questions/179386/what-are-the-downsides-of-implementing-a-singleton-with-javas-enum中有广泛的讨论
 *
 * <p>{@link ThreadSafeLazyLoadedIvoryTower} is a Singleton implementation that is initialized on
 * demand. The downside is that it is very slow to access since the whole access method is
 * synchronized.</p>
 * {@link ThreadSafeLazyLoadedIvoryTower}是一个按需初始化的单例实现。缺点是访问非常慢，因为整个访问方法是同步的
 *
 * <p>Another Singleton implementation that is initialized on demand is found in
 * {@link ThreadSafeDoubleCheckLocking}. It is somewhat faster than {@link
 * ThreadSafeLazyLoadedIvoryTower} since it doesn't synchronize the whole access method but only the
 * method internals on specific conditions.</p>
 * 另一个按需初始化的单例实现可以在{@link ThreadSafeDoubleCheckLocking}中找到。
 * 它比{@link ThreadSafeLazyLoadedIvoryTower}快一些，因为它不同步整个访问方法，而只在特定条件下同步方法内部。
 *
 * <p>Yet another way to implement thread-safe lazily initialized Singleton can be found in
 * {@link InitializingOnDemandHolderIdiom}. However, this implementation requires at least Java 8
 * API level to work.</p>
 * 还有一种实现线程安全的延迟初始化单例的方法可以在{@link InitializingOnDemandHolderIdiom}中找到。然而，该实现至少需要Java 8 API级别才能工作
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    // eagerly initialized singleton
    var ivoryTower1 = IvoryTower.getInstance();
    var ivoryTower2 = IvoryTower.getInstance();
    LOGGER.info("ivoryTower1={}", ivoryTower1);
    LOGGER.info("ivoryTower2={}", ivoryTower2);

    // lazily initialized singleton
    var threadSafeIvoryTower1 = ThreadSafeLazyLoadedIvoryTower.getInstance();
    var threadSafeIvoryTower2 = ThreadSafeLazyLoadedIvoryTower.getInstance();
    LOGGER.info("threadSafeIvoryTower1={}", threadSafeIvoryTower1);
    LOGGER.info("threadSafeIvoryTower2={}", threadSafeIvoryTower2);

    // enum singleton
    var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
    var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
    LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
    LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);

    // double checked locking
    var dcl1 = ThreadSafeDoubleCheckLocking.getInstance();
    LOGGER.info(dcl1.toString());
    var dcl2 = ThreadSafeDoubleCheckLocking.getInstance();
    LOGGER.info(dcl2.toString());

    // initialize on demand holder idiom
    var demandHolderIdiom = InitializingOnDemandHolderIdiom.getInstance();
    LOGGER.info(demandHolderIdiom.toString());
    var demandHolderIdiom2 = InitializingOnDemandHolderIdiom.getInstance();
    LOGGER.info(demandHolderIdiom2.toString());
  }
}
