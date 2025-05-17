package com.iluwatar.onionarchitecture.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.iluwatar.onionarchitecture.domain.TodoItem;
import com.iluwatar.onionarchitecture.domain.TodoRepository;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TodoServiceTest {

  @Mock private TodoRepository repository;

  private TodoService todoService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    reset(repository);

    AtomicInteger idCounter = new AtomicInteger(1);


    Mockito.doAnswer(invocation -> {
        TodoItem todo = invocation.getArgument(0);
        int id = idCounter.getAndIncrement();  
        todo.setId(id);  
        return null; 
    }).when(repository).add(any(TodoItem.class));

    todoService = new TodoService(repository);
    
  }

  @Test
    public void testCreateTodo() {

    String title = "Test Todo";
    TodoItem expectedTodo = new TodoItem(1, title);

    ArgumentCaptor<TodoItem> captor = ArgumentCaptor.forClass(TodoItem.class);

    todoService.createTodo(title);

    verify(repository).add(captor.capture());

    TodoItem capturedTodo = captor.getValue();
    assertEquals(expectedTodo.getTitle(), capturedTodo.getTitle());
    assertEquals(expectedTodo.getId(), capturedTodo.getId());
}

  @Test
  public void testGetTodos() {

    TodoItem todo1 = new TodoItem(1, "Test Todo 1");
    TodoItem todo2 = new TodoItem(2, "Test Todo 2");
    List<TodoItem> todos = List.of(todo1, todo2);
    when(repository.getAll()).thenReturn(todos);

    List<TodoItem> result = todoService.getTodos();

    assertEquals(2, result.size());
    assertTrue(result.contains(todo1));
    assertTrue(result.contains(todo2));
  }

  @Test
  public void testCompleteTodo() {

    int todoId = 1;

    todoService.completeTodo(todoId);

    verify(repository).markAsCompleted(todoId);
  }

  @Test
  public void testDeleteTodo() {

    int todoId = 1;

    todoService.delete(todoId);

    verify(repository).delete(todoId);
  }

  @Test
    public void testCreateTodoWithUniqueId() {
        String title1 = "First Todo";
        String title2 = "Second Todo";

        todoService.createTodo(title1);
        todoService.createTodo(title2);

        ArgumentCaptor<TodoItem> captor = ArgumentCaptor.forClass(TodoItem.class);
        

        verify(repository, times(2)).add(captor.capture());


        List<TodoItem> capturedArgs = captor.getAllValues();

        assertEquals(1, capturedArgs.get(0).getId());
        assertEquals(title1, capturedArgs.get(0).getTitle());
        
        assertEquals(2, capturedArgs.get(1).getId());
        assertEquals(title2, capturedArgs.get(1).getTitle());
    }


  @Test
  public void testGetTodosNoItems() {

    when(repository.getAll()).thenReturn(List.of());

    List<TodoItem> result = todoService.getTodos();

    assertTrue(result.isEmpty());
  }
}
