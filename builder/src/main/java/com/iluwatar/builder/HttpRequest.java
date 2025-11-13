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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 실무 예제: HTTP 요청 빌더
 *
 * <p>HttpRequest는 HTTP 요청을 구성하는 Builder 패턴 예제입니다.
 * 복잡한 HTTP 요청을 단계적으로 구성할 수 있습니다.
 *
 * <h2>실무 활용 사례:</h2>
 * <ul>
 *   <li>REST API 클라이언트 구현</li>
 *   <li>HTTP 라이브러리 래퍼</li>
 *   <li>테스트용 HTTP 요청 생성</li>
 *   <li>마이크로서비스 간 통신</li>
 * </ul>
 *
 * <h2>사용 예제:</h2>
 * <pre>
 * HttpRequest request = HttpRequest.builder()
 *     .method(HttpMethod.POST)
 *     .url("https://api.example.com/users")
 *     .addHeader("Content-Type", "application/json")
 *     .addHeader("Authorization", "Bearer token123")
 *     .body("{\"name\": \"John Doe\"}")
 *     .timeout(5000)
 *     .build();
 *
 * // 요청 실행 시뮬레이션
 * String response = request.execute();
 * </pre>
 *
 * <h2>장점:</h2>
 * <ul>
 *   <li>복잡한 HTTP 요청을 명확하게 표현</li>
 *   <li>선택적 헤더와 파라미터 쉽게 추가</li>
 *   <li>Fluent API로 가독성 향상</li>
 *   <li>불변 객체로 Thread-safe</li>
 * </ul>
 */
public final class HttpRequest {

  private final HttpMethod method;
  private final String url;
  private final Map<String, String> headers;
  private final Map<String, String> queryParams;
  private final String body;
  private final Integer timeout;
  private final boolean followRedirects;

  /**
   * HTTP 메서드 열거형.
   */
  public enum HttpMethod {
    GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS
  }

  /**
   * Private 생성자 - Builder를 통해서만 객체 생성 가능.
   */
  private HttpRequest(Builder builder) {
    this.method = builder.method;
    this.url = builder.url;
    this.headers = Collections.unmodifiableMap(new HashMap<>(builder.headers));
    this.queryParams = Collections.unmodifiableMap(new HashMap<>(builder.queryParams));
    this.body = builder.body;
    this.timeout = builder.timeout;
    this.followRedirects = builder.followRedirects;
  }

  /**
   * Builder 인스턴스 생성.
   *
   * @return HttpRequest Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  // Getters
  public HttpMethod getMethod() {
    return method;
  }

  public String getUrl() {
    return url;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public Map<String, String> getQueryParams() {
    return queryParams;
  }

  public String getBody() {
    return body;
  }

  public Integer getTimeout() {
    return timeout;
  }

  public boolean isFollowRedirects() {
    return followRedirects;
  }

  /**
   * 완전한 URL 생성 (쿼리 파라미터 포함).
   *
   * @return 완전한 URL
   */
  public String getFullUrl() {
    if (queryParams.isEmpty()) {
      return url;
    }

    StringBuilder fullUrl = new StringBuilder(url);
    fullUrl.append("?");

    boolean first = true;
    for (Map.Entry<String, String> entry : queryParams.entrySet()) {
      if (!first) {
        fullUrl.append("&");
      }
      fullUrl.append(entry.getKey()).append("=").append(entry.getValue());
      first = false;
    }

    return fullUrl.toString();
  }

  /**
   * HTTP 요청 실행 시뮬레이션.
   *
   * @return 응답 문자열
   */
  public String execute() {
    StringBuilder result = new StringBuilder();
    result.append("Executing HTTP Request:\n");
    result.append("Method: ").append(method).append("\n");
    result.append("URL: ").append(getFullUrl()).append("\n");

    if (!headers.isEmpty()) {
      result.append("Headers:\n");
      headers.forEach((key, value) ->
          result.append("  ").append(key).append(": ").append(value).append("\n")
      );
    }

    if (body != null) {
      result.append("Body: ").append(body).append("\n");
    }

    result.append("Timeout: ").append(timeout != null ? timeout + "ms" : "default").append("\n");
    result.append("Follow Redirects: ").append(followRedirects).append("\n");
    result.append("\nResponse: 200 OK (simulated)");

    return result.toString();
  }

  @Override
  public String toString() {
    return "HttpRequest{"
        + "method=" + method
        + ", url='" + url + '\''
        + ", headers=" + headers.size()
        + ", queryParams=" + queryParams.size()
        + ", hasBody=" + (body != null)
        + ", timeout=" + timeout
        + '}';
  }

  /**
   * HttpRequest Builder 클래스.
   */
  public static class Builder {
    private HttpMethod method = HttpMethod.GET;
    private String url;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private String body;
    private Integer timeout = 30000; // 기본 30초
    private boolean followRedirects = true;

    /**
     * HTTP 메서드 설정.
     *
     * @param method HTTP 메서드
     * @return Builder
     */
    public Builder method(HttpMethod method) {
      this.method = method;
      return this;
    }

    /**
     * GET 메서드 설정 (편의 메서드).
     *
     * @return Builder
     */
    public Builder get() {
      return method(HttpMethod.GET);
    }

    /**
     * POST 메서드 설정 (편의 메서드).
     *
     * @return Builder
     */
    public Builder post() {
      return method(HttpMethod.POST);
    }

    /**
     * PUT 메서드 설정 (편의 메서드).
     *
     * @return Builder
     */
    public Builder put() {
      return method(HttpMethod.PUT);
    }

    /**
     * DELETE 메서드 설정 (편의 메서드).
     *
     * @return Builder
     */
    public Builder delete() {
      return method(HttpMethod.DELETE);
    }

    /**
     * URL 설정 (필수).
     *
     * @param url 요청 URL
     * @return Builder
     */
    public Builder url(String url) {
      this.url = url;
      return this;
    }

    /**
     * 헤더 추가.
     *
     * @param name 헤더 이름
     * @param value 헤더 값
     * @return Builder
     */
    public Builder addHeader(String name, String value) {
      this.headers.put(name, value);
      return this;
    }

    /**
     * 여러 헤더 추가.
     *
     * @param headers 헤더 맵
     * @return Builder
     */
    public Builder headers(Map<String, String> headers) {
      this.headers.putAll(headers);
      return this;
    }

    /**
     * 쿼리 파라미터 추가.
     *
     * @param name 파라미터 이름
     * @param value 파라미터 값
     * @return Builder
     */
    public Builder addQueryParam(String name, String value) {
      this.queryParams.put(name, value);
      return this;
    }

    /**
     * 여러 쿼리 파라미터 추가.
     *
     * @param params 파라미터 맵
     * @return Builder
     */
    public Builder queryParams(Map<String, String> params) {
      this.queryParams.putAll(params);
      return this;
    }

    /**
     * 요청 본문 설정.
     *
     * @param body 요청 본문
     * @return Builder
     */
    public Builder body(String body) {
      this.body = body;
      return this;
    }

    /**
     * JSON 본문 설정 (편의 메서드).
     *
     * @param json JSON 문자열
     * @return Builder
     */
    public Builder jsonBody(String json) {
      this.body = json;
      this.headers.put("Content-Type", "application/json");
      return this;
    }

    /**
     * 타임아웃 설정 (밀리초).
     *
     * @param timeout 타임아웃 (밀리초)
     * @return Builder
     */
    public Builder timeout(Integer timeout) {
      this.timeout = timeout;
      return this;
    }

    /**
     * 리다이렉트 따라가기 설정.
     *
     * @param followRedirects 리다이렉트 따라가기 여부
     * @return Builder
     */
    public Builder followRedirects(boolean followRedirects) {
      this.followRedirects = followRedirects;
      return this;
    }

    /**
     * HttpRequest 객체 생성.
     * 필수 필드 유효성 검증 수행.
     *
     * @return HttpRequest 객체
     * @throws IllegalStateException 필수 필드가 없거나 유효하지 않은 경우
     */
    public HttpRequest build() {
      if (url == null || url.trim().isEmpty()) {
        throw new IllegalStateException("URL is required");
      }
      if (!url.startsWith("http://") && !url.startsWith("https://")) {
        throw new IllegalStateException("URL must start with http:// or https://");
      }
      if (method == null) {
        throw new IllegalStateException("HTTP method is required");
      }
      if (timeout != null && timeout < 0) {
        throw new IllegalStateException("Timeout cannot be negative");
      }
      if ((method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH)
          && body == null) {
        // Warning: POST/PUT/PATCH without body is allowed but might be unusual
        System.out.println("Warning: " + method + " request without body");
      }
      return new HttpRequest(this);
    }
  }
}
