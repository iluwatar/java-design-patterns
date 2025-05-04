package com.iluwatar.daofactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/** {@link FlatFileCustomerDAO} */
public class FlatFileCustomerDAOTest {
  private Path filePath;
  private File file;
  private Gson gson;

  private final Type customerListType = new TypeToken<List<Customer<Long>>>() {}.getType();
  private final Customer<Long> existingCustomer = new Customer<>(1L, "Thanh");
  private FlatFileCustomerDAO flatFileCustomerDAO;
  private FileReader fileReader;
  private FileWriter fileWriter;

  @BeforeEach
  void setUp() {
    filePath = mock(Path.class);
    file = mock(File.class);
    gson = mock(Gson.class);
    fileReader = mock(FileReader.class);
    fileWriter = mock(FileWriter.class);
    flatFileCustomerDAO =
        new FlatFileCustomerDAO(filePath, gson) {
          @Override
          protected Reader createReader(Path filePath) throws IOException {
            return fileReader;
          }

          @Override
          protected Writer createWriter(Path filePath) throws IOException {
            return fileWriter;
          }
        };
    when(filePath.toFile()).thenReturn(file);
  }

  /** Class test with scenario Save Customer */
  @Nested
  class Save {
    @Test
    void giveFilePathNotExist_whenSaveCustomer_thenCreateNewFileWithCustomer() {
      when(file.exists()).thenReturn(false);
      flatFileCustomerDAO.save(existingCustomer);

      verify(gson)
          .toJson(
              argThat(
                  (List<Customer<Long>> list) ->
                      list.size() == 1 && list.getFirst().equals(existingCustomer)),
              eq(fileWriter));
    }

    @Test
    void givenEmptyFileExist_whenSaveCustomer_thenAddCustomer() {
      when(file.exists()).thenReturn(true);
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(new LinkedList<>());
      flatFileCustomerDAO.save(existingCustomer);

      verify(gson).fromJson(fileReader, customerListType);
      verify(gson)
          .toJson(
              argThat(
                  (List<Customer<Long>> list) ->
                      list.size() == 1 && list.getFirst().equals(existingCustomer)),
              eq(fileWriter));
    }

    @Test
    void givenFileWithCustomerExist_whenSaveCustomer_thenShouldAppendCustomer() {
      List<Customer<Long>> customers = new LinkedList<>();
      customers.add(new Customer<>(2L, "Duc"));
      customers.add(new Customer<>(3L, "Nguyen"));
      when(file.exists()).thenReturn(true);
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(customers);

      flatFileCustomerDAO.save(existingCustomer);

      verify(gson).fromJson(fileReader, customerListType);
      verify(gson).toJson(argThat((List<Customer<Long>> list) -> list.size() == 3), eq(fileWriter));
    }

    @Test
    void whenReadFails_thenThrowException() {
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) throws IOException {
              throw new IOException("Failed to read file");
            }

            @Override
            protected Writer createWriter(Path filePath) {
              return fileWriter;
            }
          };
      when(file.exists()).thenReturn(true);
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.save(existingCustomer));
    }

    @Test
    void whenWriteFails_thenThrowException() {
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(new LinkedList<>());
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) {
              return fileReader;
            }

            @Override
            protected Writer createWriter(Path filePath) throws IOException {
              throw new IOException("Failed to write file");
            }
          };
      when(file.exists()).thenReturn(true);
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.save(existingCustomer));
    }
  }

  /** Class test with scenario Update Customer */
  @Nested
  class Update {
    @Test
    void givenFilePathNotExist_whenUpdateCustomer_thenThrowException() {
      when(file.exists()).thenReturn(false);
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.update(existingCustomer));
    }

    @Test
    void whenReadFails_thenThrowException() {
      when(file.exists()).thenReturn(true);
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) throws IOException {
              throw new IOException("Failed to read file");
            }

            @Override
            protected Writer createWriter(Path filePath) throws IOException {
              return fileWriter;
            }
          };
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.update(existingCustomer));
    }

    @Test
    void whenWriteFails_thenThrowException() {
      when(file.exists()).thenReturn(true);
      when(gson.fromJson(any(Reader.class), eq(customerListType)))
          .thenReturn(
              new LinkedList<>() {
                {
                  add(new Customer<>(1L, "Quang"));
                }
              });
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) {
              return fileReader;
            }

            @Override
            protected Writer createWriter(Path filePath) throws IOException {
              throw new IOException("Failed to write file");
            }
          };
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.update(existingCustomer));
    }

    @Test
    void givenValidCustomer_whenUpdateCustomer_thenUpdateSucceed() {
      when(file.exists()).thenReturn(true);
      List<Customer<Long>> existingListCustomer = new LinkedList<>();
      existingListCustomer.add(new Customer<>(1L, "Quang"));
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(existingListCustomer);
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) {
              return fileReader;
            }

            @Override
            protected Writer createWriter(Path filePath) throws IOException {
              return fileWriter;
            }
          };
      flatFileCustomerDAO.update(existingCustomer);
      verify(gson)
          .toJson(
              argThat(
                  (List<Customer<Long>> customers) ->
                      customers.size() == 1
                          && customers.stream()
                              .anyMatch(c -> c.getId().equals(1L) && c.getName().equals("Thanh"))),
              eq(fileWriter));
    }

    @Test
    void givenIdCustomerNotExist_whenUpdateCustomer_thenThrowException() {
      when(file.exists()).thenReturn(true);
      List<Customer<Long>> existingListCustomer = new LinkedList<>();
      existingListCustomer.add(new Customer<>(2L, "Quang"));
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(existingListCustomer);
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) {
              return fileReader;
            }

            @Override
            protected Writer createWriter(Path filePath) {
              return fileWriter;
            }
          };
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.update(existingCustomer));
    }
  }

  /** Class test with scenario Delete Customer */
  @Nested
  class Delete {
    @Test
    void givenFilePathNotExist_whenDeleteCustomer_thenThrowException() {
      when(file.exists()).thenReturn(false);
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.delete(1L));
    }

    @Test
    void whenReadFails_thenThrowException() {
      when(file.exists()).thenReturn(true);
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) throws IOException {
              throw new IOException("Failed to read file");
            }

            @Override
            protected Writer createWriter(Path filePath) {
              return fileWriter;
            }
          };
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.delete(1L));
    }

    @Test
    void whenWriteFails_thenThrowException() {
      when(file.exists()).thenReturn(true);
      List<Customer<Long>> existingListCustomer = new LinkedList<>();
      existingListCustomer.add(new Customer<>(1L, "Quang"));
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(existingListCustomer);
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) {
              return fileReader;
            }

            @Override
            protected Writer createWriter(Path filePath) throws IOException {
              throw new IOException("Failed to write file");
            }
          };
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.delete(1L));
    }

    @Test
    void givenValidId_whenDeleteCustomer_thenDeleteSucceed() {
      when(file.exists()).thenReturn(true);
      List<Customer<Long>> existingListCustomer = new LinkedList<>();
      existingListCustomer.add(new Customer<>(1L, "Quang"));
      existingListCustomer.add(new Customer<>(2L, "Thanh"));
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(existingListCustomer);
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) {
              return fileReader;
            }

            @Override
            protected Writer createWriter(Path filePath) {
              return fileWriter;
            }
          };

      flatFileCustomerDAO.delete(1L);
      assertEquals(1, existingListCustomer.size());
      verify(gson)
          .toJson(
              argThat(
                  (List<Customer<Long>> customers) ->
                      customers.stream()
                          .noneMatch(c -> c.getId().equals(1L) && c.getName().equals("Quang"))),
              eq(fileWriter));
    }

    @Test
    void givenIdNotExist_whenDeleteCustomer_thenThrowException() {
      when(file.exists()).thenReturn(true);
      List<Customer<Long>> existingListCustomer = new LinkedList<>();
      existingListCustomer.add(new Customer<>(1L, "Quang"));
      existingListCustomer.add(new Customer<>(2L, "Thanh"));
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(existingListCustomer);
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) {
              return fileReader;
            }

            @Override
            protected Writer createWriter(Path filePath) {
              return fileWriter;
            }
          };
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.delete(3L));
    }
  }

  /** Class test with scenario Find All Customer */
  @Nested
  class FindAll {
    @Test
    void givenFileNotExist_thenThrowException() {
      when(file.exists()).thenReturn(false);
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.findAll());
    }

    @Test
    void whenReadFails_thenThrowException() {
      when(file.exists()).thenReturn(true);
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) throws IOException {
              throw new IOException("Failed to read file");
            }

            @Override
            protected Writer createWriter(Path filePath) {
              return fileWriter;
            }
          };
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.findAll());
    }

    @Test
    void givenEmptyCustomer_thenReturnEmptyList() {
      when(file.exists()).thenReturn(true);
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(new LinkedList<>());
      List<Customer<Long>> customers = flatFileCustomerDAO.findAll();
      assertEquals(0, customers.size());
      verify(gson).fromJson(fileReader, customerListType);
    }

    @Test
    void givenCustomerExist_thenReturnCustomerList() {
      when(file.exists()).thenReturn(true);
      List<Customer<Long>> existingListCustomer = new LinkedList<>();
      existingListCustomer.add(new Customer<>(1L, "Quang"));
      existingListCustomer.add(new Customer<>(2L, "Thanh"));
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(existingListCustomer);
      List<Customer<Long>> customers = flatFileCustomerDAO.findAll();
      assertEquals(2, customers.size());
    }
  }

  /** Class test with scenario Find By Id Customer */
  @Nested
  class FindById {

    @Test
    void givenFilePathNotExist_whenFindById_thenThrowException() {
      when(file.exists()).thenReturn(false);
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.findById(1L));
    }

    @Test
    void whenReadFails_thenThrowException() {
      when(file.exists()).thenReturn(true);
      flatFileCustomerDAO =
          new FlatFileCustomerDAO(filePath, gson) {
            @Override
            protected Reader createReader(Path filePath) throws IOException {
              throw new IOException("Failed to read file");
            }

            @Override
            protected Writer createWriter(Path filePath) {
              return fileWriter;
            }
          };
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.findById(1L));
    }

    @Test
    void givenIdCustomerExist_whenFindById_thenReturnCustomer() {
      when(file.exists()).thenReturn(true);
      List<Customer<Long>> existingListCustomer = new LinkedList<>();
      existingListCustomer.add(new Customer<>(1L, "Quang"));
      existingListCustomer.add(new Customer<>(2L, "Thanh"));
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(existingListCustomer);
      Optional<Customer<Long>> customer = flatFileCustomerDAO.findById(1L);
      assertTrue(customer.isPresent());
      assertEquals("Quang", customer.get().getName());
    }

    @Test
    void givenIdCustomerNotExist_whenFindById_thenReturnEmpty() {
      when(file.exists()).thenReturn(true);
      when(gson.fromJson(any(Reader.class), eq(customerListType))).thenReturn(new LinkedList<>());
      Optional<Customer<Long>> customers = flatFileCustomerDAO.findById(1L);
      assertTrue(customers.isEmpty());
    }
  }

  /** Clas test with scenario Delete schema */
  @Nested
  class DeleteSchema {
    @Test
    void givenFilePathExist_thenDeleteFile() {
      when(file.exists()).thenReturn(true);
      flatFileCustomerDAO.deleteSchema();
      verify(file, times(1)).delete();
    }

    @Test
    void givenFilePathNotExist_thenThrowException() {
      when(file.exists()).thenReturn(false);
      assertThrows(RuntimeException.class, () -> flatFileCustomerDAO.deleteSchema());
      verify(file, times(0)).delete();
    }
  }
}
