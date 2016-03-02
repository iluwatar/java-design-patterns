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
package com.amitinside.plugin_pattern.modules;

import com.amitinside.plugin_pattern.annotations.Facebook;
import com.amitinside.plugin_pattern.annotations.Google;
import com.amitinside.plugin_pattern.service.IChatService;
import com.amitinside.plugin_pattern.service.impl.FacebookChatService;
import com.amitinside.plugin_pattern.service.impl.GoogleChatService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

/**
 * Configuration Module to be used in Production Environment
 */
public final class ProductionModule extends AbstractModule {

  /** {@inheritDoc}} */
  @Override
  protected void configure() {

    this.bind(IChatService.class).annotatedWith(Facebook.class).to(FacebookChatService.class)
        .in(Singleton.class);
    this.bind(IChatService.class).annotatedWith(Google.class).to(GoogleChatService.class)
        .in(Singleton.class);

    this.bindConstant().annotatedWith(Names.named("user")).to("AMIT KUMAR MONDAL");

  }
}
