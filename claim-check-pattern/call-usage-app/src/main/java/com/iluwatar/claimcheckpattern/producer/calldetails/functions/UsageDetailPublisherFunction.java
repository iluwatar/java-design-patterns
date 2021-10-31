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

package com.iluwatar.claimcheckpattern.producer.calldetails.functions;

import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationEventData;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationResponse;
import com.iluwatar.claimcheckpattern.domain.Message;
import com.iluwatar.claimcheckpattern.domain.MessageBody;
import com.iluwatar.claimcheckpattern.domain.MessageHeader;
import com.iluwatar.claimcheckpattern.domain.MessageReference;
import com.iluwatar.claimcheckpattern.domain.UsageDetail;
import com.iluwatar.claimcheckpattern.utility.EventHandlerUtility;
import com.iluwatar.claimcheckpattern.utility.MessageHandlerUtility;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * Azure Functions with HTTP Trigger.
 * This is Producer class.
 */
public class UsageDetailPublisherFunction {

  private MessageHandlerUtility<UsageDetail> messageHandlerUtility;
  private EventHandlerUtility<MessageHeader> eventHandlerUtility;

  public UsageDetailPublisherFunction() {
    this.messageHandlerUtility = new MessageHandlerUtility<>();
    this.eventHandlerUtility = new EventHandlerUtility<>();
  }

  public UsageDetailPublisherFunction(MessageHandlerUtility<UsageDetail> messageHandlerUtility,
      EventHandlerUtility<MessageHeader> eventHandlerUtility) {
    this.messageHandlerUtility = messageHandlerUtility;
    this.eventHandlerUtility = eventHandlerUtility;
  }

  /**
   * Azure function which create message, drop it in persistent storage
   * and publish the event to Event Grid topic.
   * @param request represents HttpRequestMessage
   * @param context represents ExecutionContext
   * @return HttpResponseMessage
   */
  @FunctionName("UsageDetailPublisherFunction")
  public HttpResponseMessage run(@HttpTrigger(name = "req", methods = {
      HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS)
      HttpRequestMessage<Optional<String>> request,
      final ExecutionContext context) {
    try {

      var eventGridEvents = EventGridEvent.fromString(request.getBody().get());

      for (EventGridEvent eventGridEvent : eventGridEvents) {
        // Handle system events
        if (eventGridEvent.getEventType()
            .equals("Microsoft.EventGrid.SubscriptionValidationEvent")) {
          SubscriptionValidationEventData subscriptionValidationEventData = eventGridEvent.getData()
              .toObject(SubscriptionValidationEventData.class);
          // Handle the subscription validation event
          var responseData = new SubscriptionValidationResponse();
          responseData.setValidationResponse(subscriptionValidationEventData.getValidationCode());
          return request.createResponseBuilder(HttpStatus.OK).body(responseData).build();

        } else if (eventGridEvent.getEventType().equals("UsageDetail")) {
          // Create message body
          var messageBody = new MessageBody<UsageDetail>();
          var usageDetailsList = new ArrayList<UsageDetail>();
          var random = new Random();
          for (int i = 0; i < 51; i++) {
            var usageDetail = new UsageDetail();
            usageDetail.setUserId("userId" + i);
            usageDetail.setData(random.nextInt(500));
            usageDetail.setDuration(random.nextInt(500));

            usageDetailsList.add(usageDetail);
          }
          messageBody.setData(usageDetailsList);

          // Create message header
          var messageHeader = new MessageHeader();
          messageHeader.setId(UUID.randomUUID().toString());
          messageHeader.setSubject("UsageDetailPublisher");
          messageHeader.setTopic("usagecostprocessorfunction-topic");
          messageHeader.setEventType("UsageDetail");
          messageHeader.setEventTime(OffsetDateTime.now().toString());
          var messageReference = new MessageReference("callusageapp",
              messageHeader.getId() + "/input.json");
          messageHeader.setData(messageReference);
          messageHeader.setDataVersion("v1.0");

          // Create entire message
          var message = new Message<UsageDetail>();
          message.setMessageHeader(messageHeader);
          message.setMessageBody(messageBody);

          // Drop data to persistent storage
          this.messageHandlerUtility.dropToPersistantStorage(message, context.getLogger());

          // Publish event to event grid topic
          eventHandlerUtility.publishEvent(messageHeader, context.getLogger());

          context.getLogger().info("Message is dropped and event is published successfully");
          return request.createResponseBuilder(HttpStatus.OK).body(message).build();
        }
      }
    } catch (Exception e) {
      context.getLogger().warning(e.getMessage());
    }

    return request.createResponseBuilder(HttpStatus.OK).body(null).build();
  }
}
