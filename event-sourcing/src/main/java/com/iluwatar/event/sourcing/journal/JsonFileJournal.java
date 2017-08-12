package com.iluwatar.event.sourcing.journal;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.iluwatar.event.sourcing.api.DomainEvent;
import com.iluwatar.event.sourcing.api.ProcessorJournal;
import com.iluwatar.event.sourcing.event.AccountCreateEvent;
import com.iluwatar.event.sourcing.event.MoneyDepositEvent;
import com.iluwatar.event.sourcing.event.MoneyTransferEvent;
import com.iluwatar.event.sourcing.event.MoneyWithdrawalEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by serdarh on 06.08.2017.
 */
public class JsonFileJournal implements ProcessorJournal{

    private File aFile;
    private List<String> events = new ArrayList<>();
    private int index = 0;
    public JsonFileJournal() {
        aFile = new File("Journal.json");
        if(aFile.exists()) {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(aFile), "UTF-8"))) {
                String line;
                while ((line = input.readLine()) != null) {
                    events.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            reset();
        }
    }

    @Override
    public void write(DomainEvent domainEvent) {
        Gson gson = new Gson();
        JsonElement jsonElement;
        if(domainEvent instanceof AccountCreateEvent) {
            jsonElement = gson.toJsonTree(domainEvent, AccountCreateEvent.class);
        }else if(domainEvent instanceof MoneyDepositEvent) {
            jsonElement = gson.toJsonTree(domainEvent, MoneyDepositEvent.class);
        }else if(domainEvent instanceof MoneyWithdrawalEvent) {
            jsonElement = gson.toJsonTree(domainEvent, MoneyWithdrawalEvent.class);
        }else if(domainEvent instanceof MoneyTransferEvent) {
            jsonElement = gson.toJsonTree(domainEvent, MoneyTransferEvent.class);
        }else{
            throw new RuntimeException("Journal Event not recegnized");
        }

        try (Writer output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(aFile, true), "UTF-8"))) {
            String eventString = jsonElement.toString();
            output.write(eventString +"\r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reset() {
            aFile.delete();
    }


    @Override
    public DomainEvent readNext() {
        if(index>=events.size()){
            return null;
        }
        String event = events.get(index);
        index++;

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(event);
        String eventClassName = jsonElement.getAsJsonObject().get("eventClassName").getAsString();
        Gson gson = new Gson();
        DomainEvent domainEvent;
        if(eventClassName.equals("AccountCreateEvent")) {
            domainEvent = gson.fromJson(jsonElement, AccountCreateEvent.class);
        }else if(eventClassName.equals("MoneyDepositEvent")) {
            domainEvent = gson.fromJson(jsonElement, MoneyDepositEvent.class);
        }else if(eventClassName.equals("MoneyTransferEvent")) {
            domainEvent = gson.fromJson(jsonElement, MoneyTransferEvent.class);
        }else if(eventClassName.equals("MoneyWithdrawalEvent")) {
            domainEvent = gson.fromJson(jsonElement, MoneyWithdrawalEvent.class);
        }else{
            throw new RuntimeException("Journal Event not recegnized");
        }

        domainEvent.setRealTime(false);
        return domainEvent;
    }
}
