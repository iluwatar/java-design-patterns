/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.iluwatar.plugin.config.modules.ProductionModule;
import com.iluwatar.plugin.config.modules.TestModule;
import com.iluwatar.plugin.service.impl.FacebookChatService;
import com.iluwatar.plugin.service.impl.GoogleChatService;
import com.iluwatar.plugin.service.impl.TestChatService;

/**
 * Plugin Pattern is an Enterprise Integration Pattern for providing different implementations of a
 * particular behavior. Depending on the actual environment of the application, the correct
 * implementation will be used for the behavior. In this example, many chat services are used (
 * {@link FacebookChatService}, {@link GoogleChatService} and {@link TestChatService}. Apart from
 * {@link TestChatService}, the other two services can be used in production environment whereas
 * {@link TestChatService} will be used in testing environment. Due to the difficulties in managing
 * dependencies in different environments, two configurations are provided -
 * {@link ProductionModule} and {@link TestModule} which specify the actual implementations to be
 * used in specific environment. For example, in production environment, the user can use
 * {@link FacebookChatService} or {@link GoogleChatService} and in testing environment,
 * {@link TestChatService} will be used in lieu of the actual services.
 */
public final class App {

  /**
   * Starting Point for the Application
   *
   * @param args command line arguments
   */
  public static void main(final String[] args) {
    final Injector injector = Guice.createInjector(new ProductionModule());

    final ChatApplication application = injector.getInstance(ChatApplication.class);

    System.out.println(application.startChat());
    System.out.println(application.whoIsChatting());
  }

}
