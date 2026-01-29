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

package com.iluwatar.servicestub

// ABOUTME: Real implementation of SentimentAnalysisServer with simulated processing delay.
// ABOUTME: Uses a supplier function to produce random sentiment classifications.

import java.util.function.Supplier

/**
 * Real implementation of SentimentAnalysisServer. Simulates random sentiment classification with
 * processing delay.
 *
 * A real sentiment analysis implementation would analyze the input string using, e.g., NLP and
 * determine whether the sentiment is positive, negative or neutral. Here we simply choose a
 * random number to simulate this. The "model" may take some time to process the input and we
 * simulate this by delaying the execution 5 seconds.
 */
class RealSentimentAnalysisServer(
    private val sentimentSupplier: Supplier<Int> = Supplier { (0..2).random() }
) : SentimentAnalysisServer {

    override fun analyzeSentiment(text: String): String {
        val sentiment = sentimentSupplier.get()
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        return when (sentiment) {
            0 -> "Positive"
            1 -> "Negative"
            else -> "Neutral"
        }
    }
}
