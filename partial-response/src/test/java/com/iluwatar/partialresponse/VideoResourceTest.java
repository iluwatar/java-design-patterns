/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * tests {@link VideoResource}.
 */
@RunWith(MockitoJUnitRunner.class)
public class VideoResourceTest {
  @Mock
  private FieldJsonMapper fieldJsonMapper;

  private VideoResource resource;

  @Before
  public void setUp() {
    var videos = Map.of(
        1, new Video(1, "Avatar", 178, "epic science fiction film",
            "James Cameron", "English"),
        2, new Video(2, "Godzilla Resurgence", 120, "Action & drama movie|",
            "Hideaki Anno", "Japanese"),
        3, new Video(3, "Interstellar", 169, "Adventure & Sci-Fi",
            "Christopher Nolan", "English"));
    resource = new VideoResource(fieldJsonMapper, videos);
  }

  @Test
  public void shouldGiveVideoDetailsById() throws Exception {
    var actualDetails = resource.getDetails(1);

    var expectedDetails = "{\"id\": 1,\"title\": \"Avatar\",\"length\": 178,\"description\": "
        + "\"epic science fiction film\",\"director\": \"James Cameron\",\"language\": \"English\",}";
    assertEquals(expectedDetails, actualDetails);
  }

  @Test
  public void shouldGiveSpecifiedFieldsInformationOfVideo() throws Exception {
    var fields = new String[]{"id", "title", "length"};

    var expectedDetails = "{\"id\": 1,\"title\": \"Avatar\",\"length\": 178}";
    when(fieldJsonMapper.toJson(any(Video.class), eq(fields))).thenReturn(expectedDetails);

    var actualFieldsDetails = resource.getDetails(2, fields);

    assertEquals(expectedDetails, actualFieldsDetails);
  }
}