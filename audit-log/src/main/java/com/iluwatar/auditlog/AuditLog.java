package com.iluwatar.auditlog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * AuditLog is an implementation of the AuditLog design pattern.
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
   * Appends given string to the end of the log file.
   * Note that this has significant overhead, as the file is opened and closed
   *  every time something is logged.
   *
   * @param string String to add to log file.
   */
  public static void write(String string) throws IOException {
    Files.write(getLogFile().toPath(), string.getBytes(StandardCharsets.UTF_8),
            StandardOpenOption.APPEND);
  }

  /**
   * Add log of customer change to log file.
   * Note that as mentioned in <a href="https://martinfowler.com/eaaDev/AuditLog.html">
   *   https://martinfowler.com/eaaDev/AuditLog.html </a>, 'today's date' is also included so that the record can be
   *   reconstituted.
   *
   * @param date The date of the change.
   * @param customer The customer who has had the change occur.
   * @param description Description of the change.
   * @param oldValue Old value before the change.
   * @param newValue New value after the change.
   */
  public static void log(SimpleDate date, Customer customer, String description, Object oldValue,
                         Object newValue) {
    try {
      write(date.toString() + "\t" + customer.getId() + "\t" + customer.getName()
              + "\t" + description + "\t" + oldValue + "\t" + newValue + "\t"
              + SimpleDate.getToday().toString() + "\n");
    } catch (IOException e) {
      e.printStackTrace();
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
