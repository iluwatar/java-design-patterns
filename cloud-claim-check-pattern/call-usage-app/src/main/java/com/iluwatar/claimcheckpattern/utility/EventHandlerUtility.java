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

package com.iluwatar.claimcheckpattern.utility;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import java.util.logging.Logger;

/**
 * This class is event publisher utility which published message header to Event Grid topic.
 * @param <T> represents UsageDetail or UsageCostDetail
 */
public class EventHandlerUtility<T> {

  private EventGridPublisherClient<BinaryData> customEventClient;

  /** Default constructor.
   */
  public EventHandlerUtility() {
    this.customEventClient = new EventGridPublisherClientBuilder()
            .endpoint(System.getenv("EventGridURL"))
        .credential(new AzureKeyCredential(System.getenv("EventGridKey")))
            .buildCustomEventPublisherClient();
  }

  /**
  Parameterized constructor.
   */
  public EventHandlerUtility(EventGridPublisherClient<BinaryData> customEventClient) {
    this.customEventClient = customEventClient;
  }

  /**
  Method for publishing event to Event Grid Topic.
   */
  public void publishEvent(T customEvent, Logger logger) {
    try {
      customEventClient.sendEvent(BinaryData.fromObject(customEvent));
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
  }
}
