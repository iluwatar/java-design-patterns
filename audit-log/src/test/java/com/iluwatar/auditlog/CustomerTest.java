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
package com.iluwatar.auditlog;

import ch.qos.logback.core.joran.spi.NoAutoStartUtil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.CollectionUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
  private Customer martin;

  private File logFile;

  private String franklin = "961 Franklin St";
  private String worcester = "88 Worcester St";

  private SimpleDate jul1 = new SimpleDate(1996, 7, 1);
  private SimpleDate jul15 = new SimpleDate(1996, 7, 15);
  private SimpleDate startDate = new SimpleDate(1996, 1, 1);


  @BeforeEach
  public void setUp () {
    logFile = new File("./etc/logTest.txt");
    try {
      AuditLog.setLogFile(logFile);
    } catch (IOException e) {
      e.printStackTrace();
      fail("Failed to set log file.");
    }
    // putting different addresses as time changes
    SimpleDate.setToday(startDate);
    martin = new Customer ("Martin", 15);
  }

  @AfterEach
  public void removeLogFile(){
    boolean succ = logFile.delete();
    if (!succ){
      fail("Failed to remove log file");
    }
  }

  @Test
  public void locationAtTime(){
    martin.setAddress(worcester, jul1);
    assertEquals(worcester, martin.getAddress());
    martin.setAddress(franklin, jul15);
    assertEquals(franklin, martin.getAddress());
  }

  @Test
  public void idGet(){
    assertEquals(15, martin.getId());
  }

  @Test
  public void nameGetSet(){
    assertEquals("Martin", martin.getName());
    martin.setName("John", jul1);
    assertEquals("John", martin.getName());
  }

  @Test
  public void correctFileOutput() {
    martin.setAddress(franklin, jul1);

    SimpleDate nextDate = new SimpleDate(1997, 1, 3);

    SimpleDate.setToday(nextDate);
    martin.setAddress(worcester, jul15);
    martin.setName("John", jul15);

    // manually create logs (hacky but effectively forced for simple implementation)
    HashSet<AuditLogRecord> records = new HashSet<>();
    records.add(new AuditLogRecord(jul1.toString(), startDate.toString(), martin.getClass().getName(),
            String.valueOf(martin.getId()), franklin.getClass().getName(), "address", null,
            franklin));
    records.add(new AuditLogRecord(jul15.toString(), nextDate.toString(),martin.getClass().getName(),
            String.valueOf(martin.getId()), worcester.getClass().getName(), "address", franklin,
            worcester));
    records.add(new AuditLogRecord(jul15.toString(), nextDate.toString(),martin.getClass().getName(),
            String.valueOf(martin.getId()), worcester.getClass().getName(), "name", "Martin",
            "John"));
    System.out.println(records);


    try{
      String outString = Files.readString(logFile.toPath());

      // split records by closing audit log record tag, then re-add it
      String[] splitString = outString.split("</auditLogRecord>");

      JAXBContext jaxbContext 	= JAXBContext.newInstance( AuditLogRecord.class );
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();



      HashSet<AuditLogRecord> loggedRecords = new HashSet<>();
      for (String i : splitString){
        String recordXML = i+"</auditLogRecord>";
        AuditLogRecord logSet = (AuditLogRecord) jaxbUnmarshaller.unmarshal(new StringReader(recordXML));
        loggedRecords.add(logSet);
      }

      AuditLogRecord[] recordArr = new AuditLogRecord[3];
      AuditLogRecord[] loggedArr = new AuditLogRecord[3];
      records.toArray(recordArr);
      loggedRecords.toArray(loggedArr);

      assertArrayEquals(recordArr, loggedArr);

    } catch (IOException | JAXBException e) {
      fail("Log file reading failed, with exception " + e.toString());
    }
  }
}
