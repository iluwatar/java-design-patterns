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
package com.iluwatar.event.queue

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.File
import java.io.IOException
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.UnsupportedAudioFileException
import kotlin.math.max

// ABOUTME: Implements the Event Queue pattern for audio playback.
// ABOUTME: Manages a queue of audio requests and processes them asynchronously.

private val logger = KotlinLogging.logger {}

/**
 * This class implements the Event Queue pattern.
 */
class Audio {

    @Volatile
    private var updateThread: Thread? = null

    private var headIndex: Int = 0

    private var tailIndex: Int = 0

    val pendingAudio: Array<PlayMessage?> = arrayOfNulls(MAX_PENDING)

    /**
     * This method stops the Update Method's thread and waits till service stops.
     */
    @Synchronized
    @Throws(InterruptedException::class)
    fun stopService() {
        updateThread?.interrupt()
        updateThread?.join()
        updateThread = null
    }

    /**
     * This method check the Update Method's thread is started.
     *
     * @return boolean
     */
    @Synchronized
    fun isServiceRunning(): Boolean {
        return updateThread?.isAlive == true
    }

    /**
     * Starts the thread for the Update Method pattern if it was not started previously. Also, when
     * the thread is ready initializes the indexes of the queue.
     */
    fun init() {
        if (updateThread == null) {
            updateThread = Thread {
                while (!Thread.currentThread().isInterrupted) {
                    update()
                }
            }
        }
        startThread()
    }

    /**
     * This is a synchronized thread starter.
     */
    @Synchronized
    private fun startThread() {
        val thread = updateThread ?: return
        if (!thread.isAlive) {
            thread.start()
            headIndex = 0
            tailIndex = 0
        }
    }

    /**
     * This method adds a new audio into the queue.
     *
     * @param stream is the AudioInputStream for the method
     * @param volume is the level of the audio's volume
     */
    fun playSound(stream: AudioInputStream, volume: Float) {
        init()
        // Walk the pending requests.
        var i = headIndex
        while (i != tailIndex) {
            val playMessage = pendingAudio[i]
            if (playMessage?.stream === stream) {
                // Use the larger of the two volumes.
                playMessage.volume = max(volume, playMessage.volume)
                // Don't need to enqueue.
                return
            }
            i = (i + 1) % MAX_PENDING
        }
        pendingAudio[tailIndex] = PlayMessage(stream, volume)
        tailIndex = (tailIndex + 1) % MAX_PENDING
    }

    /**
     * This method uses the Update Method pattern. It takes the audio from the queue and plays it.
     */
    private fun update() {
        // If there are no pending requests, do nothing.
        if (headIndex == tailIndex) {
            return
        }
        try {
            val audioStream = pendingAudio[headIndex]?.stream
            headIndex++
            val clip = AudioSystem.getClip()
            clip.open(audioStream)
            clip.start()
        } catch (e: LineUnavailableException) {
            logger.trace(e) { "Error occurred while loading the audio: The line is unavailable" }
        } catch (e: IOException) {
            logger.trace(e) { "Input/Output error while loading the audio" }
        } catch (e: IllegalArgumentException) {
            logger.trace(e) { "The system doesn't support the sound: ${e.message}" }
        }
    }

    /**
     * Returns the AudioInputStream of a file.
     *
     * @param filePath is the path of the audio file
     * @return AudioInputStream
     * @throws UnsupportedAudioFileException when the audio file is not supported
     * @throws IOException when the file is not readable
     */
    @Throws(UnsupportedAudioFileException::class, IOException::class)
    fun getAudioStream(filePath: String): AudioInputStream {
        return AudioSystem.getAudioInputStream(File(filePath).absoluteFile)
    }

    companion object {
        private const val MAX_PENDING = 16

        val instance: Audio = Audio()
    }
}
