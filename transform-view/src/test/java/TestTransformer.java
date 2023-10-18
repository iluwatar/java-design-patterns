import static org.junit.Assert.assertTrue;

import com.iluwater.transformview.Patient;
import com.iluwater.transformview.Transformer;
import org.junit.Test;

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
}
