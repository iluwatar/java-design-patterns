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
package com.iluwatar.event.queue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Testing the Audio service of the Queue
 *
 */
class AudioTest {

  private Audio audio;

  @BeforeEach
  void createAudioInstance() {
    audio = new Audio();
  }

  /**
   * Test here that the playSound method works correctly
   * @throws UnsupportedAudioFileException when the audio file is not supported 
   * @throws IOException when the file is not readable
   * @throws InterruptedException when the test is interrupted externally
   */
  @Test
  void testPlaySound() throws UnsupportedAudioFileException, IOException, InterruptedException {
    audio.playSound(audio.getAudioStream("./etc/Bass-Drum-1.wav"), -10.0f);
    // test that service is started
    assertTrue(audio.isServiceRunning());
    // adding a small pause to be sure that the sound is ended
    Thread.sleep(5000);

    audio.stopService();
    // test that service is finished
    assertFalse(audio.isServiceRunning());
  }

  /**
   * Test here that the Queue
   * @throws UnsupportedAudioFileException when the audio file is not supported 
   * @throws IOException when the file is not readable
   * @throws InterruptedException when the test is interrupted externally
   */
  @Test
  void testQueue() throws UnsupportedAudioFileException, IOException, InterruptedException {
    audio.playSound(audio.getAudioStream("./etc/Bass-Drum-1.aif"), -10.0f);
    audio.playSound(audio.getAudioStream("./etc/Bass-Drum-1.aif"), -10.0f);
    audio.playSound(audio.getAudioStream("./etc/Bass-Drum-1.aif"), -10.0f);
    assertTrue(audio.getPendingAudio().length > 0);
    // test that service is started
    assertTrue(audio.isServiceRunning());
    // adding a small pause to be sure that the sound is ended
    Thread.sleep(10000);

    audio.stopService();
    // test that service is finished
    assertFalse(audio.isServiceRunning());
  }

}
