import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

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

    // Static method to read data from a text file and create Patient instances
    // This could just as easily be in another class seperate to the Patients class
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
