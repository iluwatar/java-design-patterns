/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Gopinath Langote
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.iluwatar.partialresponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The Partial response pattern is a design pattern in which client specifies fields to fetch to serve.
 * Here {@link App} is playing as client for {@link VideoResource} server.
 * Client ask for specific fields information in video to server.
 * <p>
 * <p>
 * {@link VideoResource} act as server to serve video information.
 */

public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Method as act client and request to server for video details.
   *
   * @param args program argument.
   */
  public static void main(String[] args) throws Exception {
    Map<Integer, Video> videos = new HashMap<>();
    videos.put(1, new Video(1, "Avatar", 178, "epic science fiction film", "James Cameron", "English"));
    videos.put(2, new Video(2, "Godzilla Resurgence", 120, "Action & drama movie|", "Hideaki Anno", "Japanese"));
    videos.put(3, new Video(3, "Interstellar", 169, "Adventure & Sci-Fi", "Christopher Nolan", "English"));
    VideoResource videoResource = new VideoResource(new FieldJsonMapper(), videos);


    LOGGER.info("Retrieving full response from server:-");
    LOGGER.info("Get all video information:");
    String videoDetails = videoResource.getDetails(1);
    LOGGER.info(videoDetails);

    LOGGER.info("----------------------------------------------------------");

    LOGGER.info("Retrieving partial response from server:-");
    LOGGER.info("Get video @id, @title, @director:");
    String specificFieldsDetails = videoResource.getDetails(3, "id", "title", "director");
    LOGGER.info(specificFieldsDetails);

    LOGGER.info("Get video @id, @length:");
    String videoLength = videoResource.getDetails(3, "id", "length");
    LOGGER.info(videoLength);
  }
}
