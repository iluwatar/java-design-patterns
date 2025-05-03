package application;

import application.TodoService;
import domain.TodoItem;
import infrastructure.TodoRepositoryImpl;

import java.util.List;

public class TodoServiceTest {
    public static void main(String[] args) {
        TodoRepositoryImpl fakeRepo = new TodoRepositoryImpl();
        TodoService service = new TodoService(fakeRepo);

        service.createTodo("Learn onion architecture");
        service.createTodo("Write unit tests");

        List<TodoItem> todos = service.getTodos();

        assert todos.size() == 2 : "Should have 2 todos";
        assert todos.get(0).getTitle().equals("Learn onion architecture");
        assert !todos.get(0).isCompleted();

        int idToComplete = todos.get(0).getId();
        service.completeTodo(idToComplete);

        todos = service.getTodos();
        assert todos.get(0).isCompleted() : "First item should be completed";

        System.out.println("TodoServiceTest passed");
    }
}
