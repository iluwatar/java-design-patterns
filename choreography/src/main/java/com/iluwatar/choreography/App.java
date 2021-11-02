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

package com.iluwatar.choreography;

import com.iluwatar.choreography.response.Response;
import com.iluwatar.choreography.servicedelivery.DeliveryService;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * The choreography pattern is a way of managing operations in a microservice architecture.
 * </p>
 * <p>
 * When an operation requires multiple microservices to be completed, that is called a "Saga".
 * The microservices need to have a coordinator which will keep track of the information returned from each service,
 * and which services still owe it information.
 * </p>
 * There are 2 main ways of creating sagas<ol>
 * <li>the orchestrator pattern</li>
 * <li>the choreography pattern</li>
 * </ol>
 * <p>
 * They differ in their approach.
 * The orchestrator pattern dictates everything that should happen, procedurally.
 * The analogy is that an orchestra only moves forwards when the conductor bids them to -
 * they should not try to do anything unless the conductor asks for it.
 * In contrast, the choreographer announces to a central queue that events have happened,
 * and trusts that each microservice will be able to figure out what to do next based on that knowledge.
 * </p>
 */
public class App {

    static String INVALID_ADDRESS = "'Middle of Nowhere'";

    /**
     * Program entry point.
     *
     * This starts the delivery process for 3 random addresses.
     *
     * @param args command line args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MainService mainService = new MainService();
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    Response post = mainService.requestDeliveryTo(getSampleAddress());
                    System.out.println(post.getMessage());
                }),
                CompletableFuture.runAsync(() -> {
                    Response post = mainService.requestDeliveryTo(getSampleAddress());
                    System.out.println(post.getMessage());
                }),
                CompletableFuture.runAsync(() -> {
                    Response post = mainService.requestDeliveryTo(getSampleAddress());
                    System.out.println(post.getMessage());
                })
        ).get();
    }

    static String getSampleAddress() {
        switch (Math.abs(new Random().nextInt()) % 3) {
            case 0:
                return DeliveryService.WALLABY_WAY;
            case 1:
                return DeliveryService.BUCKINGHAM;
            case 2:
                return INVALID_ADDRESS;
        }
        return null;
    }
}

