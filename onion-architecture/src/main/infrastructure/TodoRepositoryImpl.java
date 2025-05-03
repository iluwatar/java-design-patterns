package infrastructure;

import domain.TodoItem;
import domain.TodoRepository;

import java.util.ArrayList;
import java.util.List;

public class TodoRepositoryImpl implements TodoRepository {
    private final List<TodoItem> todos = new ArrayList<>();

    @Override
    public void add(TodoItem item) {
        todos.add(item);
    }

    @Override
    public List<TodoItem> getAll() {
        return new ArrayList<>(todos);
    }

    @Override
    public void markAsCompleted(int id) {
        for (TodoItem item : todos) {
            if (item.getId() == id) {
                item.markCompleted();
                break;
            }
        }
    }
}
