package blub.task;

public class Todo extends Task {

    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }

    @Override
    public String toCommandString() {
        return "todo " + getDescription();
    }
}
