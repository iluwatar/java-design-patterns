import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.iluwater.transformview.Patient;
import com.iluwater.transformview.Transformer;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The TestTransformer class contains test methods for the Transformer class.
 */
public class TestTransformer {
  /**
   * Test the generateHtmlTable method of the Transformer class.
   */
  @Test
  public void testGenerateHtmlTable() {
    // Initialize a Transformer instance with some sample options
    Map<String, String> options = Map.of(
            "font-size", "16px",
            "cell-padding", "5px",
            "table-margin", "auto",
            "border", "3px solid black",
            "column-spacing", "20px"
    );
    Transformer transformer = new Transformer(options);

    // Read in list of patients
    List<Patient> patients = Patient.readPatientsFromFile("patients.txt");

    // convert patients to strings
    transformer.convertModelsToStrings(patients, Patient.class);
    String generatedHtml = transformer.generateHtmlTable();

    // Check if HTML contains certain strings
    assertTrue(generatedHtml.contains("<table>"));
    assertTrue(generatedHtml.contains("<th>dateOfBirth</th>"));
    assertTrue(generatedHtml.contains("Robert"));
    assertTrue(generatedHtml.contains("Brown"));
    assertTrue(generatedHtml.contains("font-size: 16px"));
  }

  @Test
  public void testModelLoadsCorrectly() {
    // initialise and read
    Transformer transformer = new Transformer(new HashMap<>());
    List<Patient> patients = Patient.readPatientsFromFile("patients.txt");

    // assert that each string conversion is equivalent to whats in the model
    transformer.convertModelsToStrings(patients, Patient.class);
    List<List<String>> data = transformer.getData();
    for (int i = 1; i < patients.size()+1; i++){
      List<String> string_patient = data.get(i);
      Patient regular_patient = patients.get(i-1); // offset here to accommodate the header
      assertEquals(string_patient.get(0), Integer.toString(regular_patient.getId()));
      assertEquals(string_patient.get(1), regular_patient.getFirstName());
      assertEquals(string_patient.get(2), regular_patient.getLastName());
      assertEquals(string_patient.get(3), regular_patient.getDateOfBirth());
    }
  }
}
