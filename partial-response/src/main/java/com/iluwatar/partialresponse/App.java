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
package com.iluwatar.partialresponse;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * The Partial response pattern is a design pattern in which client specifies fields to fetch to
 * serve. Here {@link App} is playing as client for {@link VideoResource} server. Client ask for
 * specific fields information in video to server.
 *
 * <p>{@link VideoResource} act as server to serve video information.
 */

@Slf4j
public class App {

  /**
   * Method as act client and request to server for video details.
   *
   * @param args program argument.
   */
  public static void main(String[] args) throws Exception {
    var videos = Map.of(
        1, new Video(1, "Avatar", 178, "epic science fiction film",
            "James Cameron", "English"),
        2, new Video(2, "Godzilla Resurgence", 120, "Action & drama movie|",
            "Hideaki Anno", "Japanese"),
        3, new Video(3, "Interstellar", 169, "Adventure & Sci-Fi",
            "Christopher Nolan", "English")
    );
    var videoResource = new VideoResource(new FieldJsonMapper(), videos);


    LOGGER.info("Retrieving full response from server:-");
    LOGGER.info("Get all video information:");
    var videoDetails = videoResource.getDetails(1);
    LOGGER.info(videoDetails);

    LOGGER.info("----------------------------------------------------------");

    LOGGER.info("Retrieving partial response from server:-");
    LOGGER.info("Get video @id, @title, @director:");
    var specificFieldsDetails = videoResource.getDetails(3, "id", "title", "director");
    LOGGER.info(specificFieldsDetails);

    LOGGER.info("Get video @id, @length:");
    var videoLength = videoResource.getDetails(3, "id", "length");
    LOGGER.info(videoLength);
  }
}
