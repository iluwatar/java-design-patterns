package com.iluwatar.consumer.callcostprocessor.functions;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.microsoft.azure.functions.annotation.*;
import com.azure.core.util.BinaryData;
import com.azure.core.util.serializer.TypeReference;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationEventData;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationResponse;
import com.google.gson.Gson;
import com.iluwatar.domain.Message;
import com.iluwatar.domain.MessageBody;
import com.iluwatar.domain.MessageHeader;
import com.iluwatar.domain.MessageReference;
import com.iluwatar.domain.UsageCostDetail;
import com.iluwatar.domain.UsageDetail;
import com.iluwatar.utility.MessageHandlerUtility;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class UsageCostProcessorFunction {

    private MessageHandlerUtility<UsageDetail> messageHandlerUtilityForUsageDetail;
    private MessageHandlerUtility<UsageCostDetail> messageHandlerUtilityForUsageCostDetail;

    public UsageCostProcessorFunction() {
        this.messageHandlerUtilityForUsageDetail = new MessageHandlerUtility<>();
        this.messageHandlerUtilityForUsageCostDetail = new MessageHandlerUtility<>();
    }

    public UsageCostProcessorFunction(MessageHandlerUtility<UsageDetail> messageHandlerUtilityForUsageDetail,
            MessageHandlerUtility<UsageCostDetail> messageHandlerUtilityForUsageCostDetail) {
        this.messageHandlerUtilityForUsageDetail = messageHandlerUtilityForUsageDetail;
        this.messageHandlerUtilityForUsageCostDetail = messageHandlerUtilityForUsageCostDetail;
    }

    @FunctionName("UsageCostProcessorFunction")
    public HttpResponseMessage run(@HttpTrigger(name = "req", methods = { HttpMethod.GET,
            HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        try {
            List<EventGridEvent> eventGridEvents = EventGridEvent.fromString(request.getBody().get());
            for (EventGridEvent eventGridEvent : eventGridEvents) {
                // Handle system events
                if (eventGridEvent.getEventType().equals("Microsoft.EventGrid.SubscriptionValidationEvent")) {
                    SubscriptionValidationEventData subscriptionValidationEventData = eventGridEvent.getData()
                            .toObject(SubscriptionValidationEventData.class);
                    // Handle the subscription validation event
                    SubscriptionValidationResponse responseData = new SubscriptionValidationResponse();
                    responseData.setValidationResponse(subscriptionValidationEventData.getValidationCode());
                    return request.createResponseBuilder(HttpStatus.OK).body(responseData).build();

                } else if (eventGridEvent.getEventType().equals("UsageDetail")) {
                    // Get message header and reference
                    MessageReference messageReference = eventGridEvent.getData().toObject(MessageReference.class);

                    // Read message from persistant storage
                    Message<UsageDetail> message = this.messageHandlerUtilityForUsageDetail
                            .readFromPersistantStorage(messageReference, context.getLogger());

                    // Get Data and generate cost details
                    System.out.println("actual message: " + message.getMessageBody().getData().get(0).getData());

                    List<UsageDetail> usageDetailsList = BinaryData.fromObject(message.getMessageBody().getData())
                            .toObject(new TypeReference<List<UsageDetail>>() {
                            });
                    List<UsageCostDetail> usageCostDetailsList = this.calculateUsageCostDetails(usageDetailsList);

                    // Create message body
                    MessageBody<UsageCostDetail> newMessageBody = new MessageBody<UsageCostDetail>();
                    newMessageBody.setData(usageCostDetailsList);

                    // Create message header
                    MessageReference newMessageReference = new MessageReference("callusageapp",
                            eventGridEvent.getId() + "/output.json");
                    MessageHeader newMessageHeader = new MessageHeader();
                    newMessageHeader.setId(eventGridEvent.getId());
                    newMessageHeader.setSubject("UsageCostProcessor");
                    newMessageHeader.setTopic("");
                    newMessageHeader.setEventType("UsageCostDetail");
                    newMessageHeader.setEventTime(OffsetDateTime.now().toString());
                    newMessageHeader.setData(newMessageReference);
                    newMessageHeader.setDataVersion("v1.0");

                    // Create entire message
                    Message<UsageCostDetail> newMessage = new Message<>();
                    newMessage.setMessageHeader(newMessageHeader);
                    newMessage.setMessageBody(newMessageBody);

                    // Drop data to persistent storage
                    this.messageHandlerUtilityForUsageCostDetail.dropToPersistantStorage(newMessage,
                            context.getLogger());

                    context.getLogger().info("Message is dropped successfully");
                    return request.createResponseBuilder(HttpStatus.OK).body("Message is dropped successfully").build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body(null).build();
    }

    private List<UsageCostDetail> calculateUsageCostDetails(List<UsageDetail> usageDetailsList) {
        if (usageDetailsList == null) {
            return null;
        }
        List<UsageCostDetail> usageCostDetailsList = new ArrayList<>();

        usageDetailsList.forEach(usageDetail -> {
            UsageCostDetail usageCostDetail = new UsageCostDetail();
            usageCostDetail.setUserId(usageDetail.getUserId());
            usageCostDetail.setCallCost(usageDetail.getDuration() * 0.30); // 0.30₹ per minute
            usageCostDetail.setDataCost(usageDetail.getData() * 0.20); // 0.20₹ per MB

            usageCostDetailsList.add(usageCostDetail);
        });

        return usageCostDetailsList;
    }
}
