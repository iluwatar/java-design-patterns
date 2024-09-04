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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * tests {@link VideoResource}.
 */
@ExtendWith(MockitoExtension.class)
class VideoResourceTest {
  @Mock
  private static FieldJsonMapper fieldJsonMapper;

  private static VideoResource resource;

  @BeforeEach
  void setUp() {
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
  void shouldGiveVideoDetailsById() throws Exception {
    var actualDetails = resource.getDetails(1);

    var expectedDetails = "{\"id\": 1,\"title\": \"Avatar\",\"length\": 178,\"description\": "
        + "\"epic science fiction film\",\"director\": \"James Cameron\",\"language\": \"English\"}";
    Assertions.assertEquals(expectedDetails, actualDetails);
  }

  @Test
  void shouldGiveSpecifiedFieldsInformationOfVideo() throws Exception {
    var fields = new String[]{"id", "title", "length"};

    var expectedDetails = "{\"id\": 1,\"title\": \"Avatar\",\"length\": 178}";
    Mockito.when(fieldJsonMapper.toJson(any(Video.class), eq(fields))).thenReturn(expectedDetails);

    var actualFieldsDetails = resource.getDetails(2, fields);

    Assertions.assertEquals(expectedDetails, actualFieldsDetails);
  }

  @Test
  void shouldAllSpecifiedFieldsInformationOfVideo() throws Exception {
    var fields = new String[]{"id", "title", "length", "description", "director", "language"};

    var expectedDetails = "{\"id\": 1,\"title\": \"Avatar\",\"length\": 178,\"description\": "
        + "\"epic science fiction film\",\"director\": \"James Cameron\",\"language\": \"English\"}";
    Mockito.when(fieldJsonMapper.toJson(any(Video.class), eq(fields))).thenReturn(expectedDetails);

    var actualFieldsDetails = resource.getDetails(1, fields);

    Assertions.assertEquals(expectedDetails, actualFieldsDetails);
  }
}
