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

// ABOUTME: Model component in MVP pattern responsible for file loading operations.
// ABOUTME: Handles reading file contents and checking file existence.
package com.iluwatar.model.view.presenter

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.Serializable

private val logger = KotlinLogging.logger {}

/**
 * Every instance of this class represents the Model component in the Model-View-Presenter
 * architectural pattern.
 *
 * It is responsible for reading and loading the contents of a given file.
 */
class FileLoader : Serializable {

    /** Indicates if the file is loaded or not. */
    var isLoaded: Boolean = false
        private set

    /** The name of the file that we want to load. */
    var fileName: String? = null

    /** Loads the data of the file specified. */
    fun loadData(): String? {
        val dataFileName = this.fileName
        try {
            BufferedReader(FileReader(dataFileName)).use { br ->
                val result = br.readLines().joinToString("\n")
                this.isLoaded = true
                return result
            }
        } catch (e: Exception) {
            logger.error { "File $dataFileName does not exist" }
        }
        return null
    }

    /**
     * Returns true if the given file exists.
     *
     * @return True, if the file given exists, false otherwise.
     */
    fun fileExists(): Boolean {
        return File(this.fileName.orEmpty()).exists()
    }

    companion object {
        private const val serialVersionUID = -4745803872902019069L
    }
}
