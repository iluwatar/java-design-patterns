package com.iluwater.notification;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class FormRegisterWorker {
    String name;
    String occupation;
    LocalDate DOB;

    RegisterWorkerDTO worker;

    RegisterWorkerService service = new RegisterWorkerService();

    public FormRegisterWorker(String name, String occupation, LocalDate DOB) {
        this.name = name;
        this.occupation = occupation;
        this.DOB = DOB;
    }

    public void submit() {
        saveToClaim();
        service.registerWorker(worker);
        if (worker.getNotification().hasErrors()) {
            indicateErrors();
            LOGGER.info("Not registered, see errors");
        } else {
            LOGGER.info("Registration Succeeded");
        }
    }

    private void saveToClaim() {
        worker = new RegisterWorkerDTO();
        worker.setName(name);
        worker.setOccupation(occupation);
        worker.setDateOfBirth(DOB);
    }

    private void indicateErrors() {
        checkError(RegisterWorkerDTO.MISSING_NAME, name);
        checkError(RegisterWorkerDTO.MISSING_DOB, DOB.toString());
        checkError(RegisterWorkerDTO.MISSING_OCCUPATION, occupation);
        checkError(RegisterWorkerDTO.DOB_TOO_SOON, DOB.toString());
    }

    private void checkError (NotificationError error, String info) {
        if (worker.getNotification().getErrors().contains(error)) {
            showError(info, error.toString());
        }
    }

    void showError (String info, String message) {
        LOGGER.info(message + ": \"" + info + "\"");
    }
}
