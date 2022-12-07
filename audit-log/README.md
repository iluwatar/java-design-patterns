---
title: Audit Log
category: Creational
language: en
tag:
- Security
- Data Access
---

## Name / classification

Audit Log.

## Also known as

Audit trail.

## Intent

Have a simple log of changes, intended to be easily written and non-intrusive.

## Explanation

Real-world example

> A customer changes their address, we want an independent record that this change occured, and 
> when it has occurred.

In plain words

> Whenever some property changes, log the change in a file.

Wikipedia says

> An audit trail (also called audit log) is a security-relevant chronological record, set of 
> records, and/or destination and source of records that provide documentary evidence of the 
> sequence of activities that have affected at any time a specific operation, procedure, event, 
> or device.  

**Programmatic Example**:

In this example implementation, `Customer` is an object that is being logged by an Audit Log, 
`AuditLog`, and changes to the `Customer`'s address, or name will be logged by the logger.

Here are the relevant parts of `Customer`,

```java
public class Customer {
  private String address;
  private String name;
  private final int id;

  public Customer(String name, int id) {
    this.name = name;
    this.id = id;
  }
  
  public void setAddress(String address, SimpleDate changeDate) {
    AuditLog.log(changeDate, this.getClass().getTypeName(), String.valueOf(id),
            address.getClass().getTypeName(), "address", this.address, address);
    this.address = address;
  }
  
  public void setName(String name, SimpleDate changeDate) {
    AuditLog.log(changeDate, this.getClass().getTypeName(), String.valueOf(id),
            name.getClass().getTypeName(), "name", this.name, name);
    this.name = name;
  }
}
```

And here are the relevant parts of `AuditLog`

* Note that in the `addRecord` function design choices have been made so that the output to a log 
  file is invalid xml (though inclusion of an opening and closing tag at the start and end of 
  the log validates it), and this choice was made for speed. In particular, instead of 
  re-generating valid xml and adding a new record, a new self-contained record is appended to 
  the log file without generating xml. 

```java
public class AuditLog {
  private static File logFile;

  public static void write(String string) throws IOException {
    Files.write(getLogFile().toPath(), string.getBytes(StandardCharsets.UTF_8),
            StandardOpenOption.APPEND);
  }

  public static void log(SimpleDate givenDate,
                         String objectType, String objectName,
                         String variableType, String variableName,
                         String oldValue, String newValue) {
    SimpleDate trueDate = SimpleDate.getToday();
    AuditLogRecord record = new AuditLogRecord(trueDate.toString(), givenDate.toString(),
            objectType, objectName, variableType, variableName, oldValue, newValue);
    addRecord(record);
  }

  private static void addRecord(AuditLogRecord record) {
    // requires a single record to be self-contained, appending another record to the bottom
    // should keep the file correct.
    try (FileWriter fWriter = new FileWriter(getLogFile(), true)) {
      // if file not empty, add new line character so entries are properly split
      if (getLogFile().length() > 0){
        fWriter.write('\n');
      }

      // set up the xml marshaller
      JAXBContext context = JAXBContext.newInstance(AuditLogRecord.class);
      Marshaller marshall = context.createMarshaller();
      marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshall.setProperty(Marshaller.JAXB_FRAGMENT, true);
      marshall.marshal(record, fWriter);
    } catch (IOException | JAXBException ignored) {
      
    }
  }
}
```

and the helper class `AuditLogRecord`

```java
public class AuditLogRecord {
  private String dateGiven;
  private String dateRecordChanged;
  private String objectType;
  private String objectName;
  private String variableType;
  private String variableName;
  private String valueOld;
  private String valueNew;
}
```

Then, with an empty log file, we have an application using the log.
```java
  public static void main(String[] args) {
    SimpleDate.setToday(new SimpleDate(2000, 5, 7));
    Customer john = new Customer("John Smith", 1);

    john.setAddress("1234 Street St", new SimpleDate(1999, 1, 4));

    // change dates to simulate time passing
    SimpleDate.setToday(new SimpleDate(2001, 6, 3));

    // note that john's address and name have changed in the past
    john.setName("John Johnson", new SimpleDate(2001, 4, 17));
    john.setAddress("4321 House Ave", new SimpleDate(2000, 8, 23));
  }
```

Then, the log file after this has been run is 
```
<auditLogRecord>
    <dateGiven>SimpleDate(2000-05-07)</dateGiven>
    <dateRecordChanged>SimpleDate(1999-01-04)</dateRecordChanged>
    <objectName>1</objectName>
    <objectType>com.iluwatar.auditlog.Customer</objectType>
    <valueNew>1234 Street St</valueNew>
    <variableName>address</variableName>
    <variableType>java.lang.String</variableType>
</auditLogRecord>
<auditLogRecord>
    <dateGiven>SimpleDate(2001-06-03)</dateGiven>
    <dateRecordChanged>SimpleDate(2001-04-17)</dateRecordChanged>
    <objectName>1</objectName>
    <objectType>com.iluwatar.auditlog.Customer</objectType>
    <valueNew>John Dough</valueNew>
    <valueOld>John Smith</valueOld>
    <variableName>name</variableName>
    <variableType>java.lang.String</variableType>
</auditLogRecord>
<auditLogRecord>
    <dateGiven>SimpleDate(2001-06-03)</dateGiven>
    <dateRecordChanged>SimpleDate(2000-08-23)</dateRecordChanged>
    <objectName>1</objectName>
    <objectType>com.iluwatar.auditlog.Customer</objectType>
    <valueNew>4321 House Ave</valueNew>
    <valueOld>1234 Street St</valueOld>
    <variableName>address</variableName>
    <variableType>java.lang.String</variableType>
</auditLogRecord>

```

## Class diagram

![alt text](./etc/audit-log.urm.png "Audit Log")

## Applicability

Use the Audit Log pattern when 
* You want to track changes to temporal properties in your implementation

## Tutorials

[Creating an Audit Trail for Spring Controllers](https://www.stackhawk.com/blog/creating-an-audit-trail-for-spring-controllers/)

## Known uses

[Log4j Audit](https://logging.apache.org/log4j-audit/latest/index.html)

## Consequences
Pros:
* Have a simple log of temporal variable changes.

Cons:
* Is difficult to process, the tighter the accessing of temporal information is integrated, the 
  less useful Audit Logs are.

## Related patterns
* [Temporal Property](https://martinfowler.com/eaaDev/TemporalProperty.html)
* [Temporal Object](https://martinfowler.com/eaaDev/TemporalObject.html)
* [Effectivity](https://martinfowler.com/eaaDev/Effectivity.html)

## Credits
* [Martin Fowler](https://martinfowler.com/eaaDev/AuditLog.html)