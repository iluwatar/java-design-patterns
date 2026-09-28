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
package com.iluwatar.microkernel.plugins;

import com.iluwatar.microkernel.core.AbstractOnDemandPlugin;
import com.iluwatar.microkernel.ipc.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UppercasePlugin extends AbstractOnDemandPlugin {

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return "Uppercase";
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return "Converts Texts to UPPERCASE";
  }

  /** Converts the payload to uppercase for a transform action. */
  @Override
  public String handleMessage(Message message) {
    if ("TRANSFORM".equalsIgnoreCase(message.action())) {
      LOGGER.info("UppercasePlugin: Transforming document.");
      return message.payload().toUpperCase();
    }
    return "ERROR: Action not supported.";
  }
}
