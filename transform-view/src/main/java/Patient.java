import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Patient class represents information about a patient, including their ID, first name, last name, and date of birth.
 */
public class Patient {
  private int id;
  private String firstName;
  private String lastName;
  private String dateOfBirth;

  /**
   * Constructs a new Patient instance with the provided information.
   *
   * @param id          The unique ID of the patient.
   * @param firstName   The first name of the patient.
   * @param lastName    The last name of the patient.
   * @param dateOfBirth The date of birth of the patient.
   */
  public Patient(int id, String firstName, String lastName, String dateOfBirth) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Reads patient data from a text file and creates Patient instances.
   *
   * @param fileName The name of the text file to read patient data from.
   * @return A list of Patient instances created from the file.
   */
  public static List<Patient> readPatientsFromFile(String fileName) {
    List<Patient> patients = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;

      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 4) {
          int id = Integer.parseInt(parts[0]);
          String firstName = parts[1];
          String lastName = parts[2];
          String dateOfBirth = parts[3];

          // Create a new Patient instance and add it to the list
          Patient patient = new Patient(id, firstName, lastName, dateOfBirth);
          patients.add(patient);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return patients;
  }
}