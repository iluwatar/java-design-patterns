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

package com.iluwatar.builder;

import com.iluwatar.builder.Hero.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * The intention of the Builder pattern is to find a solution to the telescoping constructor
 * anti-pattern. The telescoping constructor anti-pattern occurs when the increase of object
 * constructor parameter combination leads to an exponential list of constructors. Instead of using
 * numerous constructors, the builder pattern uses another object, a builder, that receives each
 * initialization parameter step by step and then returns the resulting constructed object at once.
 * Builder模式的目的是找到伸缩构造函数反模式的解决方案。当对象构造函数参数组合的增加导致构造函数的指数列表时，就会出现伸缩构造函数反模式。
 * 构建器模式不是使用大量构造器，而是使用另一个对象，即一个构建器，该对象逐步接收每个初始化参数，然后立即返回生成的构造对象。
 *
 * <p>The Builder pattern has another benefit. It can be used for objects that contain flat data
 * (html code, SQL query, X.509 certificate...), that is to say, data that can't be easily edited.
 * This type of data cannot be edited step by step and must be edited at once. The best way to
 * construct such an object is to use a builder class.
 * Builder模式还有另一个好处。它可以用于包含平面数据的对象(html代码、SQL查询、X.509证书…)，也就是说，不能轻松编辑的数据。
 * 这种类型的数据不能一步一步地编辑，必须立即编辑。构造此类对象的最佳方法是使用构建器类。
 *
 * <p>In this example we have the Builder pattern variation as described by Joshua Bloch in
 * Effective Java 2nd Edition.
 * 在这个例子中，我们有Joshua Bloch在Effective Java 2nd Edition中描述的Builder模式变体。
 *
 * <p>We want to build {@link Hero} objects, but its construction is complex because of the many
 * parameters needed. To aid the user we introduce {@link Builder} class. {@link Hero.Builder} takes
 * the minimum parameters to build {@link Hero} object in its constructor. After that additional
 * configuration for the {@link Hero} object can be done using the fluent {@link Builder} interface.
 * When configuration is ready the build method is called to receive the final {@link Hero} object.
 * 我们想要构建{@link Hero}对象，但它的构造很复杂，因为需要许多参数。为了帮助用户，我们引入了{@link Builder}类。
 * {@link Hero.Builder}在其构造函数中使用最小的参数来构建{@link Hero}对象。在{@link Hero}对象的附加配置之后，可以使用流畅的{@link Builder}接口来完成。
 * 当配置准备好后，build方法会被调用来接收最终的{@link Hero}对象。
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    var mage = new Hero.Builder(Profession.MAGE, "Riobard")
        .withHairColor(HairColor.BLACK)
        .withWeapon(Weapon.DAGGER)
        .build();
    LOGGER.info(mage.toString());

    var warrior = new Hero.Builder(Profession.WARRIOR, "Amberjill")
        .withHairColor(HairColor.BLOND)
        .withHairType(HairType.LONG_CURLY).withArmor(Armor.CHAIN_MAIL).withWeapon(Weapon.SWORD)
        .build();
    LOGGER.info(warrior.toString());

    var thief = new Hero.Builder(Profession.THIEF, "Desmond")
        .withHairType(HairType.BALD)
        .withWeapon(Weapon.BOW)
        .build();
    LOGGER.info(thief.toString());
  }
}
