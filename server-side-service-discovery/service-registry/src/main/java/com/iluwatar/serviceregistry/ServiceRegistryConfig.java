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

package com.iluwatar.serviceregistry;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the Service Registry.
 * Defines the properties for Eureka server configuration.
 */
@Configuration
@ConfigurationProperties(prefix = "eureka")
public class ServiceRegistryConfig {
  
  /**
   * Configuration for Eureka client settings.
   */
  public static class Client {
    private boolean registerWithEureka = false;
    private boolean fetchRegistry = false;
    
    public boolean isRegisterWithEureka() {
      return registerWithEureka;
    }
    
    public void setRegisterWithEureka(boolean registerWithEureka) {
      this.registerWithEureka = registerWithEureka;
    }
    
    public boolean isFetchRegistry() {
      return fetchRegistry;
    }
    
    public void setFetchRegistry(boolean fetchRegistry) {
      this.fetchRegistry = fetchRegistry;
    }
  }
  
  /**
   * Configuration for Eureka server settings.
   */
  public static class Server {
    private boolean enableSelfPreservation = false;
    private int evictionIntervalTimerInMs = 15000;
    
    public boolean isEnableSelfPreservation() {
      return enableSelfPreservation;
    }
    
    public void setEnableSelfPreservation(boolean enableSelfPreservation) {
      this.enableSelfPreservation = enableSelfPreservation;
    }
    
    public int getEvictionIntervalTimerInMs() {
      return evictionIntervalTimerInMs;
    }
    
    public void setEvictionIntervalTimerInMs(int evictionIntervalTimerInMs) {
      this.evictionIntervalTimerInMs = evictionIntervalTimerInMs;
    }
  }
}
