package com.iluwatar.utility;

import java.util.logging.Logger;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;

public class EventHandlerUtility<T> {

    public void publishEvent(T event, Logger logger){
        try{
            EventGridPublisherClient<BinaryData> customEventClient = new EventGridPublisherClientBuilder()
                .endpoint(System.getenv("EventGridURL"))
                .credential(new AzureKeyCredential(System.getenv("EventGridKey")))
                .buildCustomEventPublisherClient();
            customEventClient.sendEvent(BinaryData.fromObject(event));
        }catch(Exception e){
            logger.info(e.getMessage());
        }
    }

    public void receiveEvent(T object){
        
    }
    
}
