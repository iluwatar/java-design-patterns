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

// ABOUTME: Utility functions for file download, character frequency analysis, and line counting.
// ABOUTME: Provides helper operations used by the Promise pattern demonstration.
package com.iluwatar.promise

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

private val logger = KotlinLogging.logger {}

/** Utility to perform various operations. */
object Utility {

    /**
     * Calculates character frequency of the file provided.
     *
     * @param fileLocation location of the file.
     * @return a map of character to its frequency, an empty map if file does not exist.
     */
    @JvmStatic
    fun characterFrequency(fileLocation: String): Map<Char, Long> {
        try {
            BufferedReader(FileReader(fileLocation)).use { bufferedReader ->
                return bufferedReader
                    .lines()
                    .flatMapToInt { it.chars() }
                    .mapToObj { it.toChar() }
                    .collect(
                        java.util.stream.Collectors.groupingBy(
                            java.util.function.Function.identity<Char>(),
                            java.util.stream.Collectors.counting(),
                        ),
                    )
            }
        } catch (ex: IOException) {
            logger.error(ex) { "An error occurred" }
        }
        return emptyMap()
    }

    /**
     * Return the character with the lowest frequency, if exists.
     *
     * @return the character.
     */
    @JvmStatic
    fun lowestFrequencyChar(charFrequency: Map<Char, Long>): Char =
        charFrequency.entries
            .minByOrNull { it.value }
            ?.key
            ?: throw NoSuchElementException("No value present")

    /**
     * Count the number of lines in a file.
     *
     * @return number of lines, 0 if file does not exist.
     */
    @JvmStatic
    fun countLines(fileLocation: String): Int {
        try {
            BufferedReader(FileReader(fileLocation)).use { bufferedReader ->
                return bufferedReader.lines().count().toInt()
            }
        } catch (ex: IOException) {
            logger.error(ex) { "An error occurred" }
        }
        return 0
    }

    /**
     * Downloads the contents from the given urlString, and stores it in a temporary directory.
     *
     * @return the absolute path of the file downloaded.
     */
    @JvmStatic
    @Throws(IOException::class)
    fun downloadFile(urlString: String): String {
        logger.info { "Downloading contents from url: $urlString" }
        val url = URL(urlString)
        val file = File.createTempFile("promise_pattern", null)
        BufferedReader(InputStreamReader(url.openStream())).use { bufferedReader ->
            FileWriter(file).use { writer ->
                var line: String? = bufferedReader.readLine()
                while (line != null) {
                    writer.write(line)
                    writer.write("\n")
                    line = bufferedReader.readLine()
                }
                logger.info { "File downloaded at: ${file.absolutePath}" }
                return file.absolutePath
            }
        }
    }
}
