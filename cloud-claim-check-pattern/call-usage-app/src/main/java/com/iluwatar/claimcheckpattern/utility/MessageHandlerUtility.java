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
package com.iluwatar.claimcheckpattern.utility;

import com.azure.core.util.BinaryData;
import com.azure.core.util.serializer.TypeReference;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.iluwatar.claimcheckpattern.domain.Message;
import com.iluwatar.claimcheckpattern.domain.MessageReference;
import java.util.logging.Logger;

/**
 * This class read and drop message from Azure blob storage.
 * @param <T> represents UsageDetail or UsageCostDetail
 */
public class MessageHandlerUtility<T> {

  private BlobServiceClient blobServiceClient;

  /**
   * Parameterized constructor.
   * @param blobServiceClient represents BlobServiceClient
   */
  public MessageHandlerUtility(BlobServiceClient blobServiceClient) {
    this.blobServiceClient = blobServiceClient;
  }

  /**
   * Default constructor.
   */
  public MessageHandlerUtility() {
    // Create a BlobServiceClient object which will be used to create a container
    // client
    this.blobServiceClient = new BlobServiceClientBuilder()
        .connectionString(System.getenv("BlobStorageConnectionString")).buildClient();

  }

  /**
   * Read message from blob storage.
   * @param messageReference represents MessageReference
   * @param logger represents Logger
   * @return Message
   */
  public Message<T> readFromPersistantStorage(MessageReference messageReference, Logger logger) {
    Message<T> message = null;
    try {

      // Get container name from message reference
      String containerName = messageReference.getDataLocation();

      // Get blob name from message reference
      String blobName = messageReference.getDataFileName();

      // Get container client
      BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

      // Get a reference to a blob
      BlobClient blobClient = containerClient.getBlobClient(blobName);

      // download the blob
      message = blobClient.downloadContent().toObject(new TypeReference<Message<T>>() {
      });
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return message;

  }

  /**
   * Drop message to blob storage.
   * @param message represents Message
   * @param logger represents Logger
   */
  public void dropToPersistantStorage(Message<T> message, Logger logger) {
    try {

      // Get message reference
      MessageReference messageReference = (MessageReference) message.getMessageHeader().getData();

      // Create a unique name for the container
      String containerName = messageReference.getDataLocation();

      // Create the container and return a container client object
      BlobContainerClient containerClient = this.blobServiceClient
          .getBlobContainerClient(containerName);
      if (!containerClient.exists()) {
        containerClient.create();
      }

      // Get a reference to a blob
      BlobClient blobClient = containerClient.getBlobClient(messageReference.getDataFileName());

      // Upload the blob
      blobClient.upload(BinaryData.fromObject(message));
    } catch (Exception e) {
      logger.info(e.getMessage());
    }

  }

}
