package application;

import domain.TodoItem;
import domain.TodoRepository;

import java.util.List;

public class TodoService {
    private final TodoRepository repository;
    private static int idCounter = 1; 
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public void createTodo(String title) {
        TodoItem item = new TodoItem(idCounter++, title); 
        repository.add(item);
    }

    public List<TodoItem> getTodos() {
        return repository.getAll();
    }

    public void completeTodo(int id) {
        repository.markAsCompleted(id);
    }
}
