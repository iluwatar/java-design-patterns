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

package com.callusage.utility;

import com.callusage.domain.Message;
import com.callusage.domain.MessageHeader;
import com.callusage.domain.UsageDetail;
import com.callusage.interfaces.IPersistentCommonStorageUtility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.springframework.stereotype.Service;

/**
 * 
 * This is an implementation of persistent storage interface.
 *
 */
@Service
public class PersistentLocalStorageUtility implements IPersistentCommonStorageUtility {
    @Override
    public Message readMessageFromPersistentStorage(MessageHeader messageHeader) {
    	Gson gson = new Gson();
    	Message<UsageDetail> message = null;
    	try {
    		Files.createDirectories(Paths.get(messageHeader.getDataLocation()));
			message = gson.fromJson(new BufferedReader(new FileReader(messageHeader.getDataLocation()+"\\"+messageHeader.getDataFileName())),
					Message.class);
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
        return message;
    }

    @Override
    public void dropMessageToPersistentStorage(Message message) {
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	try {
    		Files.createDirectories(Paths.get(message.getMessageHeader().getDataLocation()));
    		FileWriter fileWriter =  new FileWriter(message.getMessageHeader().getDataLocation()+"\\"+message.getMessageHeader().getDataFileName());
			gson.toJson(message,fileWriter);
			fileWriter.flush();
			fileWriter.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
    }
}
