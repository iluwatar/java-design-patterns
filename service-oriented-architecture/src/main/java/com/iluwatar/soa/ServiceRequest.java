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
package com.iluwatar.soa;

import java.util.Map;

/**
 * A coarse-grained, self-describing message sent to a service through the {@link ServiceBus}.
 *
 * <p>The request names the target service and the operation to invoke, carries a flat, immutable
 * payload and optionally the caller's credential. Because the request payload is plain data rather
 * than typed Java objects, the same contract could be transported as SOAP, JSON or any other
 * interoperable format. Responses in this demo carry typed Java objects for brevity; a deployed
 * system would describe them as plain data too.
 *
 * @param service the name of the target service
 * @param operation the operation the target service should perform
 * @param payload the parameters of the operation
 * @param credential the caller's credential, {@code null} for an anonymous caller
 */
public record ServiceRequest(
    String service, String operation, Map<String, Object> payload, String credential) {

  public ServiceRequest {
    payload = Map.copyOf(payload);
  }

  /** Creates an anonymous request. */
  public ServiceRequest(String service, String operation, Map<String, Object> payload) {
    this(service, operation, payload, null);
  }

  /**
   * Reads a typed parameter from the payload.
   *
   * @param key the parameter name
   * @param type the expected type
   * @param <T> the expected type
   * @return the parameter value
   * @throws IllegalArgumentException when the parameter is missing or has another type
   */
  public <T> T param(String key, Class<T> type) {
    var value = payload.get(key);
    if (!type.isInstance(value)) {
      throw new IllegalArgumentException(
          "Missing or invalid parameter '" + key + "' for operation '" + operation + "'");
    }
    return type.cast(value);
  }
}
