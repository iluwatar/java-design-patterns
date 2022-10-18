package com.iluwatar.auditlog;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
  private Customer martin;

  private File logFile;

  private String franklin = "961 Franklin St";
  private String worcester = "88 Worcester St";

  private SimpleDate jul1 = new SimpleDate(1996, 7, 1);
  private SimpleDate jul15 = new SimpleDate(1996, 7, 15);

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
    SimpleDate.setToday(new SimpleDate(1996,1,1));
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
    SimpleDate.setToday(new SimpleDate(1997, 1, 3));
    martin.setAddress(worcester, jul15);
    martin.setName("John", jul15);

    try{
      String outString = Files.readString(logFile.toPath());
      System.out.println(outString);

    } catch (IOException e) {
      fail("Log file reading failed, with exception " + e.toString());
    }
  }
}

