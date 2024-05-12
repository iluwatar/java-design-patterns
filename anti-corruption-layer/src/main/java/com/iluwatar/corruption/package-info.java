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
/**
 * Context and problem
 * Most applications rely on other systems for some data or functionality.
 * For example, when a legacy application is migrated to a modern system,
 * it may still need existing legacy resources. New features must be able to call the legacy system.
 * This is especially true of gradual migrations,
 * where different features of a larger application are moved to a modern system over time.
 *
 * <p>Often these legacy systems suffer from quality issues such as convoluted data schemas
 * or obsolete APIs.
 * The features and technologies used in legacy systems can vary widely from more modern systems.
 * To interoperate with the legacy system,
 * the new application may need to support outdated infrastructure, protocols, data models, APIs,
 * or other features that you wouldn't otherwise put into a modern application.
 *
 * <p>Maintaining access between new and legacy systems can force the new system to adhere to
 * at least some of the legacy system's APIs or other semantics.
 * When these legacy features have quality issues, supporting them "corrupts" what might
 * otherwise be a cleanly designed modern application.
 * Similar issues can arise with any external system that your development team doesn't control,
 * not just legacy systems.
 *
 * <p>Solution Isolate the different subsystems by placing an anti-corruption layer between them.
 * This layer translates communications between the two systems,
 * allowing one system to remain unchanged while the other can avoid compromising
 * its design and technological approach.
 */
package com.iluwatar.corruption;
