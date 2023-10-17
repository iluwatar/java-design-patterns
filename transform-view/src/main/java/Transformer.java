import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Transformer {
    private List<List<String>> data;
    private Map<String, String> options;

    public Transformer(Map<String, String> options) {
        this.options = options;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String generateHtmlTable() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n<head>\n<title>Generated Table</title>\n");
        html.append("<style>\n");

        // CSS styles based on the options
        if (options != null) {
            if (options.containsKey("font-size")) {
                html.append("table { font-size: ").append(options.get("font-size")).append("; }\n");
            }
            if (options.containsKey("table-margin")) {
                html.append("table { margin: ").append(options.get("table-margin")).append("; }\n");
            }
            if (options.containsKey("cell-padding")) {
                html.append("td, th { padding: ").append(options.get("cell-padding")).append("; }\n");
            }
            if (options.containsKey("border")) {
                html.append("table { border: ").append(options.get("border")).append("; }\n");
            }
            if (options.containsKey("column-spacing")) {
                html.append("td, th { margin-right: ").append(options.get("column-spacing")).append("; }\n");
            }
            if (options.containsKey("cell-border")) {
                html.append("td, th { border: ").append(options.get("cell-border")).append("; }\n");
            }
        }

        html.append("</style>\n");
        html.append("</head>\n<body>\n");
        html.append("<table>\n");

        // Ensure that there's data to include in the table
        if (data != null && !data.isEmpty()) {
            List<String> headerRow = data.get(0);
            html.append("<tr>");
            for (String cell : headerRow) {
                html.append("<th>").append(cell).append("</th>");
            }
            html.append("</tr>\n");

            for (int i = 1; i < data.size(); i++) {
                List<String> rowData = data.get(i);
                html.append("<tr>");
                for (String cell : rowData) {
                    html.append("<td>").append(cell).append("</td>");
                }
                html.append("</tr>\n");
            }
        }

        html.append("</table>\n</body>\n</html>");
        return html.toString();
    }


    public <T> void convertModelsToStrings(List<T> models, Class<T> modelClass) {
        // Get the fields of the specified model class
        Field[] fields = modelClass.getDeclaredFields();

        List<List<String>> dataRows = new ArrayList<>();

        // Create a header row with field names
        List<String> headerRow = new ArrayList<>();
        for (Field field : fields) {
            headerRow.add(field.getName());
        }
        dataRows.add(headerRow);

        // Iterate through models and retrieve field values
        for (T model : models) {
            List<String> fieldValues = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(model);
                    String fieldValue = (value != null) ? value.toString() : "null";
                    fieldValues.add(fieldValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            dataRows.add(fieldValues);
        }

        data = dataRows;
    }

    public void saveHtmlToFile(String htmlContent, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}