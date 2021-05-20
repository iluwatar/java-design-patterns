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

package com.iluwatar.consumer.callcostprocessor.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.iluwatar.consumer.callcostprocessor.domain.Message;
import com.iluwatar.consumer.callcostprocessor.domain.MessageHeader;
import com.iluwatar.consumer.callcostprocessor.domain.UsageDetail;
import com.iluwatar.consumer.callcostprocessor.interfaces.IPersistentCommonStorageUtility;
import com.iluwatar.consumer.callcostprocessor.utility.PersistentLocalStorageUtility;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * This is an implementation of persistent storage interface.
 * Here we are using Windows File System as persistent storage.
 */
@Slf4j
@Service
public class PersistentLocalStorageUtility<T> implements IPersistentCommonStorageUtility<T> {
  @Override
    public Message<T> readMessageFromPersistentStorage(MessageHeader messageHeader) {
    var gson = new Gson();
    Message<T> message = null;
    var typeToken = new TypeToken<Message<UsageDetail>>() { }.getType();
    try {
      message = gson.fromJson(new BufferedReader(new FileReader(messageHeader.getDataLocation() 
        + "\\" + messageHeader.getDataFileName())), typeToken);
    } catch (JsonSyntaxException | JsonIOException | IOException e) {
      LOGGER.error(e.getMessage());
    }
    return message;
  }

  @Override
    public void dropMessageToPersistentStorage(Message<T> message) {
    var gson = new GsonBuilder().setPrettyPrinting().create();
    try {
      Files.createDirectories(Paths.get(message.getMessageHeader().getDataLocation()));
      var fileWriter =  new FileWriter(message.getMessageHeader().getDataLocation() 
          + "\\" + message.getMessageHeader().getDataFileName());
      gson.toJson(message, fileWriter);
      fileWriter.flush();
      fileWriter.close();
    } catch (JsonIOException | IOException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
