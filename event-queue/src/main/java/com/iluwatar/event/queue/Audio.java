/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

package com.iluwatar.event.queue;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the Event Queue pattern.
 * 
 * @author mkuprivecz
 *
 */
public class Audio {

  private static final Logger LOGGER = LoggerFactory.getLogger(Audio.class);

  private static final int MAX_PENDING = 16;

  private static int headIndex;

  private static int tailIndex;

  private static Thread updateThread = null;

  private static PlayMessage[] pendingAudio = new PlayMessage[MAX_PENDING];

  /**
   * This method stops the Update Method's thread.
   */
  public static synchronized void stopService() {
    if (updateThread != null) {
      updateThread.interrupt();
    }
  }

  /**
   * This method stops the Update Method's thread.
   * 
   * @return boolean
   */
  public static synchronized boolean isServiceRunning() {
    return updateThread != null && updateThread.isAlive();
  }

  /**
   * Starts the thread for the Update Method pattern if it was not started previously. Also when the thread is is ready
   * initializes the indexes of the queue
   */
  public static void init() {
    if (updateThread == null) {
      updateThread = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
          Audio.update();
          try {
            Thread.sleep(1000); // Sleep a second before processing next in queue. If not in this example all messages
                                // in the queue played in parallel.
          } catch (InterruptedException e) {
            LOGGER.warn("Interrupted!");
            // Restore interrupted state as per sonarqube recommendation
            Thread.currentThread().interrupt();
          }
        }
      });
    }
    startThread();
  }

  /**
   * This is a synchronized thread starter
   */
  public static synchronized void startThread() {
    if (!updateThread.isAlive()) {
      updateThread.start();
      headIndex = 0;
      tailIndex = 0;
    }
  }

  /**
   * This method adds a new audio into the queue.
   * 
   * @param stream
   *          is the AudioInputStream for the method
   * @param volume
   *          is the level of the audio's volume
   */
  public static void playSound(AudioInputStream stream, float volume) {
    init();
    // Walk the pending requests.
    for (int i = headIndex; i != tailIndex; i = (i + 1) % MAX_PENDING) {
      if (getPendingAudio()[i].getStream() == stream) {
        // Use the larger of the two volumes.
        getPendingAudio()[i].setVolume(Math.max(volume, getPendingAudio()[i].getVolume()));

        // Don't need to enqueue.
        return;
      }
    }
    getPendingAudio()[tailIndex] = new PlayMessage(stream, volume);
    LOGGER.info("Added audio stream at queue index {}", tailIndex);
    tailIndex = (tailIndex + 1) % MAX_PENDING;
  }

  /**
   * This method uses the Update Method pattern. It takes the audio from the queue and plays it
   */
  public static void update() {
    // If there are no pending requests, do nothing.
    if (headIndex == tailIndex) {
      return;
    }
    Clip clip = null;
    AudioInputStream audioStream = getPendingAudio()[headIndex].getStream();
    try {
      clip = AudioSystem.getClip();
      clip.open(audioStream);
      clip.start();
      LOGGER.info("Started playing stream from index {}", headIndex);
      headIndex++;
    } catch (LineUnavailableException e) {
      LOGGER.error("Error occoured while loading the audio: The line is unavailable", e);
    } catch (IOException e) {
      LOGGER.error("Input/Output error while loading the audio", e);
    } catch (IllegalArgumentException e) {
      LOGGER.error("The system doesn't support the sound: " + e.getMessage());
    } finally {
      if (clip != null) {
        while (clip.isActive()) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            LOGGER.warn("Interrupted!", e);
            // Restore interrupted state as per sonarqube recommendation
            Thread.currentThread().interrupt();
          }
        }
        clip.close();
      }
    }

  }

  /**
   * Returns the AudioInputStream of a file
   * 
   * @param filePath
   *          is the path of the audio file
   * @return AudioInputStream
   * @throws UnsupportedAudioFileException
   *           when the audio file is not supported
   * @throws IOException
   *           when the file is not readable
   */
  public static AudioInputStream getAudioStream(String filePath) throws UnsupportedAudioFileException, IOException {
    return AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
  }

  /**
   * Returns with the message array of the queue
   * 
   * @return PlayMessage[]
   */
  public static PlayMessage[] getPendingAudio() {
    return pendingAudio;
  }

}
