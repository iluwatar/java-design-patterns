package com.iluwatar.visitor.example.datatransfer;
import com.iluwatar.visitor.example.DataTransferHashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataTransferHashMapTest {

  @Test
  public void testAddData() {
    DataTransferHashMap transferHash = new DataTransferHashMap();
    transferHash.addData("Name", "Alice");

    // Assert that the data is added correctly
    assertEquals("Alice", transferHash.getData("Name"));
  }

  @Test
  public void testGetData() {
    DataTransferHashMap transferHash = new DataTransferHashMap();
    transferHash.addData("City", "Paris");

    // Assert that the correct value is retrieved
    assertEquals("Paris", transferHash.getData("City"));
  }

  @Test
  public void testRemoveData() {
    DataTransferHashMap transferHash = new DataTransferHashMap();
    transferHash.addData("Country", "France");
    transferHash.removeData("Country");

    // Assert that the data is removed
    assertNull(transferHash.getData("Country"));
  }

  @Test
  public void testDisplayData() {
    DataTransferHashMap transferHash = new DataTransferHashMap();
    transferHash.addData("Name", "John");
    transferHash.addData("Age", "30");


    // Mock a console output if needed (or just visually verify it during test runs)
    transferHash.displayData();

  }
}
