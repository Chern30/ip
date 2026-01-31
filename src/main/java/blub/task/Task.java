package blub.task;

public abstract class Task {
    private String description;
    private boolean isDone;
    private TaskType type;

    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public TaskType getType() {
        return type;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String getDescription() {
        return description;
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return type.getPrefix() + "[" + getStatusIcon() + "] " + description;
    }
}
