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

import com.iluwatar.microkernel.core.AbstractLifecyclePlugin;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NeonThemePlugin extends AbstractLifecyclePlugin {

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_GREEN = "\u001B[32m";

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return "Theme";
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return "Neon Theme for Editor";
  }

  /** Applies the terminal color used by the neon theme. */
  @Override
  protected void doStart() {
    System.out.print(ANSI_GREEN);
    LOGGER.info("NeonThemePlugin: Neon theme applied.");
  }

  /** Restores the terminal's default color. */
  @Override
  protected void doStop() {
    System.out.print(ANSI_RESET);
    LOGGER.info("ThemePlugin: Default theme restored.");
  }
}
