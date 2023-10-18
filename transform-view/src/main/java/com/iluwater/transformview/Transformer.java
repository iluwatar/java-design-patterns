package com.iluwater.transformview;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Transformer class is responsible for transforming data into an HTML table and saving it to a file.
 */
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

  /**
   * Generates an HTML table based on the provided data and styling options.
   *
   * @return The generated HTML table as a string.
   */
  public String generateHtmlTable() {
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html>\n");
    html.append("<html>\n<head>\n<title>Generated Table</title>\n");
    html.append("<style>\n");

    // CSS styles based on the options
    if (options != null) {
      for (Map.Entry<String, String> entry : options.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        String end = "; }\n";
        switch (key) {
          case "font-size" -> html.append("table { font-size: ").append(value).append(end);
          case "table-margin" -> html.append("table { margin: ").append(value).append(end);
          case "cell-padding" -> html.append("td, th { padding: ").append(value).append(end);
          case "border" -> html.append("table { border: ").append(value).append(end);
          case "column-spacing" -> html.append("td, th { margin-right: ").append(value).append(end);
          case "cell-border" -> html.append("td, th { border: ").append(value).append(end);
          default -> { } // do nothing if not recognised
        }
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

  /**
   * Converts a list of models to a list of strings, which can be used as data for an HTML table.
   *
   * @param models      The list of models to convert.
   * @param modelClass  The class of the models.
   * @param <T>         The type of the models.
   */
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
          // skip this field
        }
      }
      dataRows.add(fieldValues);
    }

    data = dataRows;
  }

  /**
   * Saves the provided HTML content to a file.
   *
   * @param htmlContent The HTML content to be saved.
   * @param filePath    The path to the file where the HTML content will be saved.
   */
  public void saveHtmlToFile(String htmlContent, String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(htmlContent);
    } catch (IOException e) {
      // don't write to file
    }
  }
}