import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public void addTodo(String description) throws BlubException {
        if (description.isEmpty()) {
            throw new BlubException("The description of a todo cannot be empty.");
        }
        add(new Todo(description));
    }

    public void addDeadline(String input) throws BlubException {
        if (input.isEmpty()) {
            throw new BlubException("The description of a deadline cannot be empty.");
        }
        if (!input.contains(" /by ")) {
            throw new BlubException("Please specify a deadline using /by.");
        }
        String[] parts = input.split(" /by ");
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new BlubException("The description of a deadline cannot be empty.");
        }
        String by = parts[1].trim();
        if (by.isEmpty()) {
            throw new BlubException("The deadline date/time cannot be empty.");
        }
        add(new Deadline(description, by));
    }

    public void addEvent(String input) throws BlubException {
        if (input.isEmpty()) {
            throw new BlubException("The description of an event cannot be empty.");
        }
        if (!input.contains(" /from ")) {
            throw new BlubException("Please specify event start time using /from.");
        }
        if (!input.contains(" /to ")) {
            throw new BlubException("Please specify event end time using /to.");
        }
        String[] parts = input.split(" /from ");
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new BlubException("The description of an event cannot be empty.");
        }
        String[] timeParts = parts[1].split(" /to ");
        String from = timeParts[0].trim();
        String to = timeParts[1].trim();
        if (from.isEmpty() || to.isEmpty()) {
            throw new BlubException("Event start and end times cannot be empty.");
        }
        add(new Event(description, from, to));
    }

    public void mark(int index) {
        tasks.get(index).markAsDone();
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }
}
