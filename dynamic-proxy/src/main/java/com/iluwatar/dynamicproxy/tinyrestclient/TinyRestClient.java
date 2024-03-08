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
package com.iluwatar.dynamicproxy.tinyrestclient;

import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Body;
import com.iluwatar.dynamicproxy.tinyrestclient.annotation.HttpMethod;
import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Path;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

/**
 * Class to handle all the http communication with a Rest API.
 * It is supported by the HttpClient Java library.
 */
public class TinyRestClient {

  private String baseUrl;
  private HttpClient httpClient;

  /**
   * Class constructor.
   *
   * @param baseUrl    Root url for endpoints.
   * @param httpClient Handle the http communication.
   */
  public TinyRestClient(String baseUrl, HttpClient httpClient) {
    this.baseUrl = baseUrl;
    this.httpClient = httpClient;
  }

  /**
   * Creates a http communication to request and receive data from an endpoint.
   *
   * @param method Interface's method which is annotated with a http method.
   * @param args   Method's arguments passed in the call.
   * @return Response from the endpoint.
   * @throws IOException          Exception thrown when any fail happens in the call.
   * @throws InterruptedException Exception thrown when call is interrupted.
   */
  public Object send(Method method, Object[] args) throws IOException, InterruptedException {
    var httpMethodAnnotation = getHttpMethodAnnotation(method.getDeclaredAnnotations());
    var httpMethodName = httpMethodAnnotation.annotationType().getSimpleName().toUpperCase();
    var url = baseUrl + buildUrl(method, args, httpMethodAnnotation);
    var bodyPublisher = buildBodyPublisher(method, args);
    var httpRequest = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Content-Type", "application/json")
        .method(httpMethodName, bodyPublisher)
        .build();
    var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    return getResponse(method, httpResponse);
  }

  private String buildUrl(Method method, Object[] args, Annotation httpMethodAnnotation) {
    var url = annotationValue(httpMethodAnnotation);
    if (url == null) {
      return "";
    }
    var index = 0;
    for (var parameter : method.getParameters()) {
      var pathAnnotation = getAnnotationOf(parameter.getDeclaredAnnotations(), Path.class);
      if (pathAnnotation != null) {
        var pathParam = "{" + annotationValue(pathAnnotation) + "}";
        var pathValue = args[index].toString();
        url = url.replace(pathParam, pathValue);
      }
      index++;
    }
    return url;
  }

  private HttpRequest.BodyPublisher buildBodyPublisher(Method method, Object[] args) {
    var index = 0;
    for (var parameter : method.getParameters()) {
      var bodyAnnotation = getAnnotationOf(parameter.getDeclaredAnnotations(), Body.class);
      if (bodyAnnotation != null) {
        var body = JsonUtil.objectToJson(args[index]);
        return HttpRequest.BodyPublishers.ofString(body);
      }
      index++;
    }
    return HttpRequest.BodyPublishers.noBody();
  }

  private Object getResponse(Method method, HttpResponse<String> httpResponse) {
    var rawData = httpResponse.body();
    var returnType = method.getGenericReturnType();
    if (returnType instanceof ParameterizedType) {
      Class<?> responseClass = (Class<?>) (((ParameterizedType) returnType)
          .getActualTypeArguments()[0]);
      return JsonUtil.jsonToList(rawData, responseClass);
    } else {
      Class<?> responseClass = method.getReturnType();
      return JsonUtil.jsonToObject(rawData, responseClass);
    }
  }

  private Annotation getHttpMethodAnnotation(Annotation[] annotations) {
    return Arrays.stream(annotations)
        .filter(annot -> annot.annotationType().isAnnotationPresent(HttpMethod.class))
        .findFirst().orElse(null);
  }

  private Annotation getAnnotationOf(Annotation[] annotations, Class<?> clazz) {
    return Arrays.stream(annotations)
        .filter(annot -> annot.annotationType().equals(clazz))
        .findFirst().orElse(null);
  }

  private String annotationValue(Annotation annotation) {
    var valueMethod = Arrays.stream(annotation.annotationType().getDeclaredMethods())
        .filter(methodAnnot -> methodAnnot.getName().equals("value"))
        .findFirst().orElse(null);
    if (valueMethod == null) {
      return null;
    }
    Object result;
    try {
      result = valueMethod.invoke(annotation, (Object[]) null);
    } catch (Exception e) {
      result = null;
    }
    return (result instanceof String ? (String) result : null);
  }
}
