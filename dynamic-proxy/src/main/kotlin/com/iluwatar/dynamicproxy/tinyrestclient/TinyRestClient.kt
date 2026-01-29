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

// ABOUTME: HTTP client that handles REST API communication using Java's HttpClient.
// ABOUTME: Uses reflection to read method annotations and build HTTP requests dynamically.
package com.iluwatar.dynamicproxy.tinyrestclient

import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Body
import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Http
import com.iluwatar.dynamicproxy.tinyrestclient.annotation.Path
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.util.UriUtils
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.net.HttpURLConnection
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

private val logger = KotlinLogging.logger {}

/**
 * Class to handle all the HTTP communication with a REST API. It is supported by the HttpClient
 * Java library.
 *
 * @param baseUrl Root URL for endpoints.
 * @param httpClient Handle the HTTP communication.
 */
class TinyRestClient(
    private val baseUrl: String,
    private val httpClient: HttpClient
) {

    companion object {
        private val httpAnnotationByMethod = mutableMapOf<Method, Annotation?>()
    }

    /**
     * Creates an HTTP communication to request and receive data from an endpoint.
     *
     * @param method Interface's method which is annotated with an HTTP method.
     * @param args Method's arguments passed in the call.
     * @return Response from the endpoint.
     */
    fun send(method: Method, args: Array<Any>?): Any? {
        val httpAnnotation = getHttpAnnotation(method) ?: return null
        val httpAnnotationName = httpAnnotation.annotationClass.java.simpleName.uppercase()
        val url = baseUrl + buildUrl(method, args, httpAnnotation)
        val bodyPublisher = buildBodyPublisher(method, args)
        val httpRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .method(httpAnnotationName, bodyPublisher)
            .build()
        val httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
        val statusCode = httpResponse.statusCode()
        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            val errorDetail = httpResponse.body()
            logger.error { "Error from server: $errorDetail" }
            return null
        }
        return getResponse(method, httpResponse)
    }

    private fun buildUrl(method: Method, args: Array<Any>?, httpMethodAnnotation: Annotation): String {
        var url = annotationValue(httpMethodAnnotation) ?: return ""
        method.parameters.forEachIndexed { index, parameter ->
            val pathAnnotation = parameter.declaredAnnotations.find { it is Path } as? Path
            if (pathAnnotation != null && args != null) {
                val pathParam = "{${pathAnnotation.value}}"
                val pathValue = UriUtils.encodePath(args[index].toString(), StandardCharsets.UTF_8)
                url = url.replace(pathParam, pathValue)
            }
        }
        return url
    }

    private fun buildBodyPublisher(method: Method, args: Array<Any>?): HttpRequest.BodyPublisher {
        method.parameters.forEachIndexed { index, parameter ->
            val bodyAnnotation = parameter.declaredAnnotations.find { it is Body }
            if (bodyAnnotation != null && args != null) {
                val body = JsonUtil.objectToJson(args[index])
                return HttpRequest.BodyPublishers.ofString(body)
            }
        }
        return HttpRequest.BodyPublishers.noBody()
    }

    private fun getResponse(method: Method, httpResponse: HttpResponse<String>): Any? {
        val rawData = httpResponse.body()
        val returnType = try {
            method.genericReturnType
        } catch (e: Exception) {
            logger.error { "Cannot get the generic return type of the method ${method.name}()" }
            return null
        }
        return if (returnType is ParameterizedType) {
            val responseClass = returnType.actualTypeArguments[0] as Class<*>
            JsonUtil.jsonToList(rawData, responseClass)
        } else {
            val responseClass = method.returnType
            JsonUtil.jsonToObject(rawData, responseClass)
        }
    }

    private fun getHttpAnnotation(method: Method): Annotation? {
        return httpAnnotationByMethod.getOrPut(method) {
            method.declaredAnnotations.find { annot ->
                annot.annotationClass.java.isAnnotationPresent(Http::class.java)
            }
        }
    }

    private fun annotationValue(annotation: Annotation): String? {
        val valueMethod = annotation.annotationClass.java.declaredMethods.find { m -> m.name == "value" }
            ?: return null
        val result = try {
            valueMethod.invoke(annotation)
        } catch (e: Exception) {
            logger.error(e) {
                "Cannot read the value ${annotation.annotationClass.java.simpleName}.${valueMethod.name}()"
            }
            null
        }
        return result as? String
    }
}
