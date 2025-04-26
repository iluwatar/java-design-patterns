package domain;

import java.util.List;

public interface TodoRepository {
    void add(TodoItem item);
    List<TodoItem> getAll();
    void markAsCompleted(int id);
}
