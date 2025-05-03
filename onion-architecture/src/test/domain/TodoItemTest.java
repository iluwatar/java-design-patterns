package domain;

import domain.TodoItem;

public class TodoItemTest {
    public static void main(String[] args) {
        TodoItem item = new TodoItem(1, "Write tests");

        assert item.getId() == 1 : "ID should be 1";
        assert item.getTitle().equals("Write tests") : "Title should match";
        assert !item.isCompleted() : "Item should not be completed initially";

        item.markCompleted();

        assert item.isCompleted() : "Item should be marked as completed";

        System.out.println("TodoItemTest passed");
    }
}
