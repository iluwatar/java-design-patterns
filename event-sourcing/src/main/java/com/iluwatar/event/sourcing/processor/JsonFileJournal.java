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

package com.iluwatar.event.sourcing.processor;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.iluwatar.event.sourcing.event.AccountCreateEvent;
import com.iluwatar.event.sourcing.event.DomainEvent;
import com.iluwatar.event.sourcing.event.MoneyDepositEvent;
import com.iluwatar.event.sourcing.event.MoneyTransferEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of event journal. This implementation serialize/deserialize the events
 * with JSON and writes/reads them on a Journal.json file at the working directory.
 *
 * <p>Created by Serdar Hamzaogullari on 06.08.2017.
 */
public class JsonFileJournal {

  private final File file;
  private final List<String> events = new ArrayList<>();
  private int index = 0;

  /**
   * Instantiates a new Json file journal.
   */
  public JsonFileJournal() {
    file = new File("Journal.json");
    if (file.exists()) {
      try (var input = new BufferedReader(
          new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
        String line;
        while ((line = input.readLine()) != null) {
          events.add(line);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      reset();
    }
  }


  /**
   * Write.
   *
   * @param domainEvent the domain event
   */
  public void write(DomainEvent domainEvent) {
    var gson = new Gson();
    JsonElement jsonElement;
    if (domainEvent instanceof AccountCreateEvent) {
      jsonElement = gson.toJsonTree(domainEvent, AccountCreateEvent.class);
    } else if (domainEvent instanceof MoneyDepositEvent) {
      jsonElement = gson.toJsonTree(domainEvent, MoneyDepositEvent.class);
    } else if (domainEvent instanceof MoneyTransferEvent) {
      jsonElement = gson.toJsonTree(domainEvent, MoneyTransferEvent.class);
    } else {
      throw new RuntimeException("Journal Event not recegnized");
    }

    try (var output = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
      var eventString = jsonElement.toString();
      output.write(eventString + "\r\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Reset.
   */
  public void reset() {
    file.delete();
  }


  /**
   * Read next domain event.
   *
   * @return the domain event
   */
  public DomainEvent readNext() {
    if (index >= events.size()) {
      return null;
    }
    var event = events.get(index);
    index++;

    var parser = new JsonParser();
    var jsonElement = parser.parse(event);
    var eventClassName = jsonElement.getAsJsonObject().get("eventClassName").getAsString();
    var gson = new Gson();
    DomainEvent domainEvent;
    if (eventClassName.equals("AccountCreateEvent")) {
      domainEvent = gson.fromJson(jsonElement, AccountCreateEvent.class);
    } else if (eventClassName.equals("MoneyDepositEvent")) {
      domainEvent = gson.fromJson(jsonElement, MoneyDepositEvent.class);
    } else if (eventClassName.equals("MoneyTransferEvent")) {
      domainEvent = gson.fromJson(jsonElement, MoneyTransferEvent.class);
    } else {
      throw new RuntimeException("Journal Event not recegnized");
    }

    domainEvent.setRealTime(false);
    return domainEvent;
  }
}
