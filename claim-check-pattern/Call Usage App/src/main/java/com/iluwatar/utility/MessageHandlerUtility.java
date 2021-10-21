package com.iluwatar.utility;

import java.util.logging.Logger;
import com.azure.core.util.BinaryData;
import com.azure.core.util.serializer.TypeReference;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.iluwatar.domain.Message;
import com.iluwatar.domain.MessageReference;

public class MessageHandlerUtility<T>{

    private BlobServiceClient blobServiceClient;

    public MessageHandlerUtility(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    public MessageHandlerUtility() {
        // Create a BlobServiceClient object which will be used to create a container client
        this.blobServiceClient = new BlobServiceClientBuilder().connectionString(System.getenv("BlobStorageConnectionString")).buildClient();

    }
    
    public Message<T> readFromPersistantStorage(MessageReference messageReference, Logger logger){
        Message<T> message = null;
        try{

            // Get container name from message reference
            String containerName = messageReference.getDataLocation();

            // Get blob name from message reference
            String blobName = messageReference.getDataFileName();

            // Get container client
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            // Get a reference to a blob
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            
            // download the blob
            message = blobClient.downloadContent().toObject(new TypeReference<Message<T>>() { });
        }
        catch(Exception e){
            logger.info(e.getMessage());
        }
        return message;

    }

    public void dropToPersistantStorage(Message<T> message, Logger logger){
        try{

            // Get message reference 
            MessageReference messageReference = (MessageReference)message.getMessageHeader().getData();

            //Create a unique name for the container
            String containerName = messageReference.getDataLocation();

            // Create the container and return a container client object
            BlobContainerClient containerClient = this.blobServiceClient.getBlobContainerClient(containerName);
            if(!containerClient.exists()){
                containerClient.create();
            }

            // Get a reference to a blob
            BlobClient blobClient = containerClient.getBlobClient(messageReference.getDataFileName());
            
            // Upload the blob
            blobClient.upload(BinaryData.fromObject(message));
        }
        catch(Exception e){
            logger.info(e.getMessage());
        }

    }
    
}
