import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestTransformer {
    private Transformer transformer;
    private List<Patient> patients;

    public void setUp() {
        // Initialize a Transformer instance with some sample options
        Map<String, String> options = Map.of("font-size", "16px", "cell-padding", "5px");
        transformer = new Transformer(options);

        // Initialize a list of patients for testing
        patients = new ArrayList<>();
        patients.add(new Patient(1, "John", "Doe", "1990-01-15"));
        patients.add(new Patient(2, "Jane", "Smith", "1985-07-20"));
    }

    @Test
    public void testGenerateHtmlTable() {
        this.setUp();
        transformer.convertModelsToStrings(patients, Patient.class);
        String generatedHtml = transformer.generateHtmlTable();

        // check if html contains certain strings
        assertTrue(generatedHtml.contains("<table>"));
        assertTrue(generatedHtml.contains("<th>dateOfBirth</th>"));
        assertTrue(generatedHtml.contains("1990-01-15</td>"));
        assertTrue(generatedHtml.contains("16px"));
    }
}
