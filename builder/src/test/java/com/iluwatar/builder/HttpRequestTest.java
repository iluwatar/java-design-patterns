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

package com.iluwatar.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.builder.HttpRequest.HttpMethod;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * HttpRequest 테스트.
 */
class HttpRequestTest {

  @Test
  void testBasicGetRequest() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .build();

    // Then
    assertNotNull(request);
    assertEquals(HttpMethod.GET, request.getMethod());
    assertEquals("https://api.example.com/users", request.getUrl());
    assertEquals(30000, request.getTimeout());
    assertTrue(request.isFollowRedirects());
  }

  @Test
  void testPostRequest() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .post()
        .url("https://api.example.com/users")
        .jsonBody("{\"name\": \"John Doe\"}")
        .build();

    // Then
    assertEquals(HttpMethod.POST, request.getMethod());
    assertEquals("{\"name\": \"John Doe\"}", request.getBody());
    assertEquals("application/json", request.getHeaders().get("Content-Type"));
  }

  @Test
  void testPutRequest() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .put()
        .url("https://api.example.com/users/1")
        .body("updated data")
        .build();

    // Then
    assertEquals(HttpMethod.PUT, request.getMethod());
  }

  @Test
  void testDeleteRequest() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .delete()
        .url("https://api.example.com/users/1")
        .build();

    // Then
    assertEquals(HttpMethod.DELETE, request.getMethod());
  }

  @Test
  void testHeaders() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .addHeader("Authorization", "Bearer token123")
        .addHeader("Accept", "application/json")
        .build();

    // Then
    assertEquals(2, request.getHeaders().size());
    assertEquals("Bearer token123", request.getHeaders().get("Authorization"));
    assertEquals("application/json", request.getHeaders().get("Accept"));
  }

  @Test
  void testMultipleHeaders() {
    // Given
    Map<String, String> headers = new HashMap<>();
    headers.put("X-Custom-Header-1", "value1");
    headers.put("X-Custom-Header-2", "value2");

    // When
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .headers(headers)
        .build();

    // Then
    assertTrue(request.getHeaders().containsKey("X-Custom-Header-1"));
    assertTrue(request.getHeaders().containsKey("X-Custom-Header-2"));
  }

  @Test
  void testQueryParams() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .addQueryParam("page", "1")
        .addQueryParam("size", "10")
        .build();

    // Then
    assertEquals(2, request.getQueryParams().size());
    assertEquals("1", request.getQueryParams().get("page"));
    assertEquals("10", request.getQueryParams().get("size"));
  }

  @Test
  void testFullUrlWithQueryParams() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .addQueryParam("page", "1")
        .addQueryParam("size", "10")
        .build();

    // Then
    String fullUrl = request.getFullUrl();
    assertTrue(fullUrl.contains("page=1"));
    assertTrue(fullUrl.contains("size=10"));
    assertTrue(fullUrl.contains("?"));
  }

  @Test
  void testFullUrlWithoutQueryParams() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .build();

    // Then
    assertEquals("https://api.example.com/users", request.getFullUrl());
  }

  @Test
  void testTimeout() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .timeout(5000)
        .build();

    // Then
    assertEquals(5000, request.getTimeout());
  }

  @Test
  void testFollowRedirects() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .followRedirects(false)
        .build();

    // Then
    assertFalse(request.isFollowRedirects());
  }

  @Test
  void testJsonBody() {
    // Given
    String json = "{\"username\": \"john\", \"email\": \"john@example.com\"}";

    // When
    HttpRequest request = HttpRequest.builder()
        .post()
        .url("https://api.example.com/users")
        .jsonBody(json)
        .build();

    // Then
    assertEquals(json, request.getBody());
    assertEquals("application/json", request.getHeaders().get("Content-Type"));
  }

  @Test
  void testExecute() {
    // Given
    HttpRequest request = HttpRequest.builder()
        .post()
        .url("https://api.example.com/users")
        .addHeader("Authorization", "Bearer token")
        .jsonBody("{\"name\": \"John\"}")
        .build();

    // When
    String response = request.execute();

    // Then
    assertNotNull(response);
    assertTrue(response.contains("Executing HTTP Request"));
    assertTrue(response.contains("POST"));
    assertTrue(response.contains("https://api.example.com/users"));
  }

  @Test
  void testMissingUrlThrowsException() {
    // Given
    HttpRequest.Builder builder = HttpRequest.builder();

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testInvalidUrlThrowsException() {
    // Given
    HttpRequest.Builder builder = HttpRequest.builder()
        .url("invalid-url");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testNegativeTimeoutThrowsException() {
    // Given
    HttpRequest.Builder builder = HttpRequest.builder()
        .url("https://api.example.com/users")
        .timeout(-1000);

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testImmutableHeaders() {
    // Given
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .addHeader("Authorization", "Bearer token")
        .build();

    // When & Then
    assertThrows(UnsupportedOperationException.class, () -> {
      request.getHeaders().put("New-Header", "value");
    });
  }

  @Test
  void testImmutableQueryParams() {
    // Given
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .addQueryParam("page", "1")
        .build();

    // When & Then
    assertThrows(UnsupportedOperationException.class, () -> {
      request.getQueryParams().put("size", "10");
    });
  }

  @Test
  void testToString() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .post()
        .url("https://api.example.com/users")
        .addHeader("Authorization", "Bearer token")
        .jsonBody("{\"name\": \"John\"}")
        .build();

    String result = request.toString();

    // Then
    assertTrue(result.contains("POST"));
    assertTrue(result.contains("https://api.example.com/users"));
  }

  @Test
  void testConvenienceMethodGet() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .get()
        .url("https://api.example.com/users")
        .build();

    // Then
    assertEquals(HttpMethod.GET, request.getMethod());
  }

  @Test
  void testMultipleQueryParams() {
    // Given
    Map<String, String> params = new HashMap<>();
    params.put("filter", "active");
    params.put("sort", "name");

    // When
    HttpRequest request = HttpRequest.builder()
        .url("https://api.example.com/users")
        .queryParams(params)
        .build();

    // Then
    assertEquals(2, request.getQueryParams().size());
    assertEquals("active", request.getQueryParams().get("filter"));
    assertEquals("sort", request.getQueryParams().get("sort"));
  }

  @Test
  void testGetRequestWithoutBody() {
    // Given & When
    HttpRequest request = HttpRequest.builder()
        .get()
        .url("https://api.example.com/users")
        .build();

    // Then
    assertNull(request.getBody());
  }
}
