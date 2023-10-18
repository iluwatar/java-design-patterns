import static org.junit.Assert.assertTrue;

import com.iluwater.transformview.Patient;
import com.iluwater.transformview.Transformer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The TestTransformer class contains test methods for the Transformer class.
 */
public class TestTransformer {
  private Transformer transformer;
  private List<Patient> patients;

  /**
   * Set up initial data for testing.
   */
  @Before
  public void setUp() {
    // Initialize a Transformer instance with some sample options
    Map<String, String> options = Map.of("font-size", "16px", "cell-padding", "5px");
    transformer = new Transformer(options);

    // Initialize a list of patients for testing
    patients = new ArrayList<>();
    patients.add(new Patient(1, "John", "Doe", "1990-01-15"));
    patients.add(new Patient(2, "Jane", "Smith", "1985-07-20"));
  }

  /**
   * Test the generateHtmlTable method of the Transformer class.
   */
  @Test
  public void testGenerateHtmlTable() {
    transformer.convertModelsToStrings(patients, Patient.class);
    String generatedHtml = transformer.generateHtmlTable();

    // Check if HTML contains certain strings
    assertTrue(generatedHtml.contains("<table>"));
    assertTrue(generatedHtml.contains("<th>dateOfBirth</th>"));
    assertTrue(generatedHtml.contains("1990-01-15</td>"));
    assertTrue(generatedHtml.contains("16px"));
  }
}
