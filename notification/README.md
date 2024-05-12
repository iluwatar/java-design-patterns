title: Notification
category: Behavioural
language: en 
tags:
- Decoupling
- Presentation
- Domain
---

## Intent

To capture information about errors and other information that occurs in the domain layer 
which acts as the internal logic and validation tool. The notification then communicates this information 
back to the presentation for handling and displaying to the user what errors have occurred and why.

## Explanation

Real world example

> You need to register a worker for your company. The information submitted needs to be valid
> before the worker can be added to the database, and if there are any errors you need to know about them.

In plain words

> A notification is simply a way of collecting information about errors and communicating it to the user 
> so that they are aware of them

**Programatic example**
Building off the example of registering a worker for a company, we can now explore a coded example for a full picture
of how this pattern looks when coded up. For full code please visit the github repository.

To begin with, the user submits information to a form through the presentation layer of our software. The information 
given to register a worker is the worker's name, occupation, and date of birth. The program will then make sure none of
these fields are blank (validation) and that the worker is over 18 years old. If there are any errors, 
the program will inform the user.

The code for the form is given below. This form acts as our presentation layer, taking input from the user and printing
output using a LOGGER (in this case). The form then gives this information to the service layer RegisterWorkerService
through a data transfer object (DTO).

The service handles information validation and the presentation can then check the notification stored within the DTO for 
any errors and display them to the user if necessary. Otherwise, it will inform the user the submission was processed
successfully.

form:
```java
/**
 * The form submitted by the user, part of the presentation layer,
 * linked to the domain layer through a data transfer object and
 * linked to the service layer directly.
 */
@Slf4j
public class RegisterWorkerForm {
  String name;
  String occupation;
  LocalDate dateOfBirth;
  RegisterWorkerDto worker;
  /**
   * Service super type which the form uses as part of its service layer.
   */
  RegisterWorkerService service = new RegisterWorkerService();

  /**
   * Creates the form.
   *
   * @param name name of worker
   * @param occupation occupation of the worker
   * @param dateOfBirth date of birth of the worker
   */
  public RegisterWorkerForm(String name, String occupation, LocalDate dateOfBirth) {
    this.name = name;
    this.occupation = occupation;
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * Attempts to submit the form for registering a worker.
   */
  public void submit() {
    //Save worker information (like name, occupation, dob) to our transfer object to be communicated between layers
    saveToWorker();
    //call the service layer to register our worker
    service.registerWorker(worker);

    //check for any errors
    if (worker.getNotification().hasErrors()) {
      indicateErrors(); //displays errors to users
      LOGGER.info("Not registered, see errors");
    } else {
      LOGGER.info("Registration Succeeded");
    }
  }
  
  ...
}
```

The data transfer object (DTO) created stores the information submitted (name, occupation, date of birth), as well as 
information on the notification after this data has been validated (stored in the DTO class it extends). 
This acts as the link between the service layer and our domain layer which runs the internal logic. 
It also holds information on the error types created. 

DTO:
```java
/**
 * Data transfer object which stores information about the worker. This is carried between
 * objects and layers to reduce the number of method calls made.
 */
@Getter
@Setter
public class RegisterWorkerDto extends DataTransferObject {
  private String name;
  private String occupation;
  private LocalDate dateOfBirth;

  /**
   * Error for when name field is blank or missing.
   */
  public static final NotificationError MISSING_NAME =
      new NotificationError(1, "Name is missing");

  /**
   * Error for when occupation field is blank or missing.
   */
  public static final NotificationError MISSING_OCCUPATION =
      new NotificationError(2, "Occupation is missing");

  /**
   * Error for when date of birth field is blank or missing.
   */
  public static final NotificationError MISSING_DOB =
      new NotificationError(3, "Date of birth is missing");

  /**
   * Error for when date of birth is less than 18 years ago.
   */
  public static final NotificationError DOB_TOO_SOON =
      new NotificationError(4, "Worker registered must be over 18");


  protected RegisterWorkerDto() {
    super();
  }
  
  ...
}
```

These errors are stored within a simple wrapper class called NotificationError.

Our service layer (RegisterWorkerService) represents the framework of our service layer. Currently, it will 
run the commands necessary to validate our Java object without handling any of the internal logic itself, 
passing on the work, along with our DTO, to the domain layer.

This validation itself is done in RegisterWorker which works within our domain layer. ServerCommand acts as 
a SuperType here for the domain and holds any DTOs needed. If it passes validation, our worker is then added into 
the database as submission was successful!

validation:
```java
/**
 * Handles internal logic and validation for worker registration.
 * Part of the domain layer which collects information and sends it back to the presentation.
 */
@Slf4j
public class RegisterWorker extends ServerCommand {
  protected RegisterWorker(RegisterWorkerDto worker) {
    super(worker);
  }

  /**
   * Validates the data provided and adds it to the database in the backend.
   */
  public void run() {
    //make sure the information submitted is valid
    validate();
    if (!super.getNotification().hasErrors()) {
      //Add worker to system in backend (not implemented here)
      LOGGER.info("Register worker in backend system");
    }
  }

  /**
   * Validates our data. Checks for any errors and if found, stores them in our notification.
   */
  private void validate() {
    var ourData = ((RegisterWorkerDto) this.data);
    //check if any of submitted data is not given
    failIfNullOrBlank(ourData.getName(), RegisterWorkerDto.MISSING_NAME);
    failIfNullOrBlank(ourData.getOccupation(), RegisterWorkerDto.MISSING_OCCUPATION);
    failIfNullOrBlank(ourData.getDateOfBirth().toString(), RegisterWorkerDto.MISSING_DOB);
    //only if DOB is not blank, then check if worker is over 18 to register.
    if (!super.getNotification().getErrors().contains(RegisterWorkerDto.MISSING_DOB)) {
      var dateOfBirth = ourData.getDateOfBirth();
      var current = now().minusYears(18);
      fail(dateOfBirth.compareTo(current) > 0, RegisterWorkerDto.DOB_TOO_SOON);
    }
  }
  
  ...
}
```

After all of this explanation, we can then simulate the following inputs into the form and submit them:

input:
```java
  /**
   * Variables to be submitted in the form.
   */
  private static final String NAME = "";
  private static final String OCCUPATION = "";
  private static final LocalDate DATE_OF_BIRTH = LocalDate.of(2016, 7, 13);

  RegisterWorkerForm form = new RegisterWorkerForm(NAME, OCCUPATION, DATE_OF_BIRTH);
  form.submit();
```

The form then processes the submission and returns these error messages to the user, showing our notification worked.

output:
```java
18:10:00.075 [main] INFO com.iluwater.RegisterWorkerForm - Error 1: Name is missing: ""
18:10:00.079 [main] INFO com.iluwater.RegisterWorkerForm - Error 2: Occupation is missing: ""
18:10:00.079 [main] INFO com.iluwater.RegisterWorkerForm - Error 4: Worker registered must be over 18: "2016-07-13"
18:10:00.080 [main] INFO com.iluwater.RegisterWorkerForm - Not registered, see errors
```

## Class diagram

![alt text](./etc/notification.urm.png "Notification")

## Applicability

Use the notification pattern when:

* You wish to communicate information about errors between the domain layer and the presentation layer. This is most applicable when a seperated presentation pattern is being used as this does not allow for direct communication between the domain and presentation.

## Related patterns

* [Service Layer](https://java-design-patterns.com/patterns/service-layer/)
* [Data Transfer Object](https://java-design-patterns.com/patterns/data-transfer-object/)
* [Domain Model](https://java-design-patterns.com/patterns/domain-model/)
* [Remote Facade](https://martinfowler.com/eaaCatalog/remoteFacade.html)
* [Autonomous View](https://martinfowler.com/eaaDev/AutonomousView.html)
* [Layer Supertype](https://martinfowler.com/eaaCatalog/layerSupertype.html)
* [Separated Presentation](https://java-design-patterns.com/patterns/data-transfer-object/)

## Credits

* [Martin Fowler - Notification Pattern](https://martinfowler.com/eaaDev/Notification.html)