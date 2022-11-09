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
package com.iluwatar.claimcheckpattern.consumer.callcostprocessor.functions;

import com.azure.core.util.BinaryData;
import com.azure.core.util.serializer.TypeReference;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationEventData;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationResponse;
import com.iluwatar.claimcheckpattern.domain.Message;
import com.iluwatar.claimcheckpattern.domain.MessageBody;
import com.iluwatar.claimcheckpattern.domain.MessageHeader;
import com.iluwatar.claimcheckpattern.domain.MessageReference;
import com.iluwatar.claimcheckpattern.domain.UsageCostDetail;
import com.iluwatar.claimcheckpattern.domain.UsageDetail;
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

/**
 * Azure Functions with HTTP Trigger.
 * This is Consumer class.
 */
public class UsageCostProcessorFunction {

  private MessageHandlerUtility<UsageDetail> messageHandlerUtilityForUsageDetail;
  private MessageHandlerUtility<UsageCostDetail> messageHandlerUtilityForUsageCostDetail;

  public UsageCostProcessorFunction() {
    this.messageHandlerUtilityForUsageDetail = new MessageHandlerUtility<>();
    this.messageHandlerUtilityForUsageCostDetail = new MessageHandlerUtility<>();
  }

  public UsageCostProcessorFunction(
      MessageHandlerUtility<UsageDetail> messageHandlerUtilityForUsageDetail,
      MessageHandlerUtility<UsageCostDetail> messageHandlerUtilityForUsageCostDetail) {
    this.messageHandlerUtilityForUsageDetail = messageHandlerUtilityForUsageDetail;
    this.messageHandlerUtilityForUsageCostDetail = messageHandlerUtilityForUsageCostDetail;
  }

  /**
   * Azure function which gets triggered when event grid event send event to it.
   * After receiving event, it read input file from blob storage, calculate call cost details.
   * It creates new message with cost details and drop message to blob storage.
   * @param request represents HttpRequestMessage
   * @param context represents ExecutionContext
   * @return HttpResponseMessage
   */
  @FunctionName("UsageCostProcessorFunction")
  public HttpResponseMessage run(@HttpTrigger(name = "req", methods = { HttpMethod.GET,
      HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS)
                                       HttpRequestMessage<Optional<String>> request,
                                 final ExecutionContext context) {
    try {
      var eventGridEvents = EventGridEvent.fromString(request.getBody().get());
      for (var eventGridEvent : eventGridEvents) {
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
          // Get message header and reference
          var messageReference = eventGridEvent.getData()
              .toObject(MessageReference.class);

          // Read message from persistent storage
          var message = this.messageHandlerUtilityForUsageDetail
              .readFromPersistantStorage(messageReference, context.getLogger());

          // Get Data and generate cost details
          List<UsageDetail> usageDetailsList = BinaryData.fromObject(
              message.getMessageBody().getData())
              .toObject(new TypeReference<>() {
              });
          var usageCostDetailsList = calculateUsageCostDetails(usageDetailsList);

          // Create message body
          var newMessageBody = new MessageBody<UsageCostDetail>();
          newMessageBody.setData(usageCostDetailsList);

          // Create message header
          var newMessageReference = new MessageReference("callusageapp",
              eventGridEvent.getId() + "/output.json");
          var newMessageHeader = new MessageHeader();
          newMessageHeader.setId(eventGridEvent.getId());
          newMessageHeader.setSubject("UsageCostProcessor");
          newMessageHeader.setTopic("");
          newMessageHeader.setEventType("UsageCostDetail");
          newMessageHeader.setEventTime(OffsetDateTime.now().toString());
          newMessageHeader.setData(newMessageReference);
          newMessageHeader.setDataVersion("v1.0");

          // Create entire message
          var newMessage = new Message<UsageCostDetail>();
          newMessage.setMessageHeader(newMessageHeader);
          newMessage.setMessageBody(newMessageBody);

          // Drop data to persistent storage
          this.messageHandlerUtilityForUsageCostDetail.dropToPersistantStorage(newMessage,
              context.getLogger());

          context.getLogger().info("Message is dropped successfully");
          return request.createResponseBuilder(HttpStatus.OK)
              .body("Message is dropped successfully").build();
        }
      }
    } catch (Exception e) {
      context.getLogger().warning(e.getMessage());
    }

    return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body(null).build();
  }

  private List<UsageCostDetail> calculateUsageCostDetails(List<UsageDetail> usageDetailsList) {
    if (usageDetailsList == null) {
      return null;
    }
    var usageCostDetailsList = new ArrayList<UsageCostDetail>();

    usageDetailsList.forEach(usageDetail -> {
      var usageCostDetail = new UsageCostDetail();
      usageCostDetail.setUserId(usageDetail.getUserId());
      usageCostDetail.setCallCost(usageDetail.getDuration() * 0.30); // 0.30₹ per minute
      usageCostDetail.setDataCost(usageDetail.getData() * 0.20); // 0.20₹ per MB

      usageCostDetailsList.add(usageCostDetail);
    });

    return usageCostDetailsList;
  }
}
