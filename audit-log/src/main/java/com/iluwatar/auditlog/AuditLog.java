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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * AuditLog is an implementation of the AuditLog design pattern, and on changes to specific temporal
 * properties on other classes, the AuditLog will write a description of the change to its current
 * logging file.
 *
 * @see <a href="https://martinfowler.com/eaaDev/AuditLog.html"> https://martinfowler.com/eaaDev/AuditLog.html </a>
 */
public class AuditLog {
  private static File logFile;
  private static final String defaultLog = "./etc/log.txt";

  /**
   * Sets the current log file.
   *
   * @param file The file to set as the current log file.
   */
  public static void setLogFile(File file) throws IOException {
    logFile = file;
    // if the file does not exist, create it.
    if (!logFile.exists()) {
      boolean succ = logFile.createNewFile();
      if (!succ) {
        throw new IOException("Failed to create new file.");
      }
    }
  }

  /**
   * Add log of change to log file.
   * Note that as mentioned in <a href="https://martinfowler.com/eaaDev/AuditLog.html">
   *   https://martinfowler.com/eaaDev/AuditLog.html </a>, 'today's date' is also included so that the record can be
   *   reconstituted.
   *
   * @param givenDate The date that the change of variable has occurred.
   * @param objectType Type of the changed object.
   * @param objectName Name / identifier of the changed object.
   * @param variableType Type of the changed variable.
   * @param variableName Name of the changed variable.
   * @param oldValue Old value before the change.
   * @param newValue New value after the change.
   */
  public static void log(SimpleDate givenDate,
                         String objectType, String objectName,
                         String variableType, String variableName,
                         String oldValue, String newValue) {
    SimpleDate trueDate = SimpleDate.getToday();
    AuditLogRecord record = new AuditLogRecord(givenDate.toString(), trueDate.toString(),
            objectType, objectName, variableType, variableName, oldValue, newValue);
    addRecord(record);
  }

  /**
   * Adds xml-like record to log.
   *
   * @param record Record to be added.
   */
  private static void addRecord(AuditLogRecord record) {
    // requires a single record to be self-contained, appending another record to the bottom
    // should keep the file correct.
    try (FileWriter fWriter = new FileWriter(getLogFile(), true)) {
      // if file not empty, add new line character so entries are properly split
      if (getLogFile().length() > 0) {
        fWriter.write('\n');
      }

      // set up the xml marshaller
      JAXBContext context = JAXBContext.newInstance(AuditLogRecord.class);
      Marshaller marshall = context.createMarshaller();
      marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshall.setProperty(Marshaller.JAXB_FRAGMENT, true);
      marshall.marshal(record, fWriter);
    } catch (IOException | JAXBException ignored) {
      ;
    }
  }

  /**
  * Returns the file currently used for logging.
  *
  * @return The file currently used for logging.
  * @throws IOException When log file is null and the setting to the default file fails.
  */
  public static File getLogFile() throws IOException {
    if (logFile == null) {
      setLogFile(new File(defaultLog));
    }
    return logFile;
  }
}


