package com.iluwater.notification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterWorkerDTO extends DataTransferObject {
    private String name;
    private String occupation;
    private LocalDate dateOfBirth;

    /**
     *
     */
    public static final NotificationError MISSING_NAME =
            new NotificationError(1, "Name is missing");

    /**
     *
     */
    public static final NotificationError MISSING_OCCUPATION =
            new NotificationError(2, "Occupation is missing");

    /**
     *
     */
    public static final NotificationError MISSING_DOB =
            new NotificationError(3, "Date of birth is missing");

    /**
     *
     */
    public static final NotificationError DOB_TOO_SOON =
            new NotificationError(4, "Worker registered must be over 18");


    protected RegisterWorkerDTO() {
        super();
    }
}
