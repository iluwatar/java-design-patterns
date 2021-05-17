/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.business.delegate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for the {@link BusinessDelegate}
 */
class BusinessDelegateTest {

  private NetflixService netflixService;

  private YouTubeService youTubeService;

  private BusinessDelegate businessDelegate;

  /**
   * This method sets up the instance variables of this test class. It is executed before the
   * execution of every test.
   */
  @BeforeEach
  public void setup() {
    netflixService = spy(new NetflixService());
    youTubeService = spy(new YouTubeService());

    BusinessLookup businessLookup = spy(new BusinessLookup());
    businessLookup.setNetflixService(netflixService);
    businessLookup.setYouTubeService(youTubeService);

    businessDelegate = spy(new BusinessDelegate());
    businessDelegate.setLookupService(businessLookup);
  }

  /**
   * In this example the client ({@link MobileClient}) utilizes a business delegate (
   * {@link BusinessDelegate}) to execute a task. The Business Delegate then selects the appropriate
   * service and makes the service call.
   */
  @Test
  void testBusinessDelegate() {

    // setup a client object
    var client = new MobileClient(businessDelegate);

    // action
    client.playbackMovie("Die hard");

    // verifying that the businessDelegate was used by client during playbackMovie() method.
    verify(businessDelegate).playbackMovie(anyString());
    verify(netflixService).doProcessing();

    // action
    client.playbackMovie("Maradona");

    // verifying that the businessDelegate was used by client during doTask() method.
    verify(businessDelegate, times(2)).playbackMovie(anyString());
    verify(youTubeService).doProcessing();
  }
}
