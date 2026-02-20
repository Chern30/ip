package blub.task;

public class Event extends Task {

    private String start;
    private String end;

    public Event(String description, String start, String end) {
        super(description, TaskType.EVENT);
        this.start = start;
        this.end = end;
    }

    // Convert Event to storable file format
    @Override
    public String toFileString() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + start + " | " + end;
    }

    @Override
    public String toCommandString() {
        return "event " + getDescription() + " /from " + start + " /to " + end;
    }

    // Convert Event to String
    @Override
    public String toString() {
        return super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }
}
