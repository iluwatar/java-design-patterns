/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Gopinath Langote
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

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VideoResourceTest {
  private VideoResource resource;

  @Before
  public void setUp() {
    Map<Integer, Video> videos = new HashMap<>();
    videos.put(1, new Video(1, "Avatar", 178, "epic science fiction film", "James Cameron", "English"));
    videos.put(2, new Video(2, "Godzilla Resurgence", 120, "Action & drama movie|", "Hideaki Anno", "Japanese"));
    videos.put(3, new Video(3, "Interstellar", 169, "Adventure & Sci-Fi", "Christopher Nolan", "English"));
    resource = new VideoResource(videos);
  }

  @Test
  public void shouldGiveVideoDetailsById() {
    String details = resource.getDetails(1);

    String expectedDetails = "{\"id\": \"1\",\"title\": \"Avatar\",\"length\": 178,\"description\": \"epic science fiction film\",\"director\": \"James Cameron\",\"language\": \"English\",}";
    assertEquals(expectedDetails, details);
  }
}