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
package com.iluwatar.dynamicproxy;

import com.iluwatar.dynamicproxy.tinyrestclient.TinyRestClient;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.http.HttpClient;
import lombok.extern.slf4j.Slf4j;

/**
 * Class whose method 'invoke' will be called every time that an interface's method is called.
 * That interface is linked to this class by the Proxy class.
 */
@Slf4j
public class AlbumInvocationHandler implements InvocationHandler {

  private TinyRestClient restClient;

  /**
   * Class constructor. It instantiates a TinyRestClient object.
   *
   * @param baseUrl    Root url for endpoints.
   * @param httpClient Handle the http communication.
   */
  public AlbumInvocationHandler(String baseUrl, HttpClient httpClient) {
    this.restClient = new TinyRestClient(baseUrl, httpClient);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    LOGGER.info("===== Calling the method {}.{}()",
        method.getDeclaringClass().getSimpleName(), method.getName());

    return restClient.send(method, args);
  }

}
