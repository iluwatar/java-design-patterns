package com.callusage.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import com.callusage.domain.Message;
import com.callusage.domain.MessageHeader;
import com.callusage.domain.UsageDetail;
import com.callusage.interfaces.IPersistentCommonStorageUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

@Service
public class PersistentLocalStorageUtility implements IPersistentCommonStorageUtility {
    @Override
    public Message readMessageFromPersistentStorage(MessageHeader messageHeader) {
    	Gson gson = new Gson();
    	Message<UsageDetail> message = null;
    	Type typeToken = new TypeToken<Message<UsageDetail>>() { }.getType();
    	try {
    		//Files.createDirectories(Paths.get(messageHeader.getDataLocation()));
			message = gson.fromJson(new BufferedReader(new FileReader(messageHeader.getDataLocation()+"\\"+messageHeader.getDataFileName())),
					typeToken);
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
