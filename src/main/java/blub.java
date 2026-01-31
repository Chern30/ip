import java.io.IOException;
import java.util.ArrayList;

public class blub {

    static String bot_name = "Blub";
    static ArrayList<Task> bot_brain = new ArrayList<Task>();
    static Ui ui = new Ui();
    static Storage storage = new Storage("data/tasks.txt");

    public static void main(String[] args) {
        try {
            bot_brain = storage.load();
        } catch (IOException e) {
            ui.sendMessage("Error reading from file: " + e.getMessage());
        }
        ui.sendMessage(hiMsg());
        converse();
        try {
            storage.save(bot_brain);
        } catch (IOException e) {
            ui.sendMessage("Error writing to file: " + e.getMessage());
        }
        ui.sendMessage(byeMsg());
    }

    private static String hiMsg() {
        return "Hello! I am " + bot_name + "\nWhat can I do for you?";
    }

    private static String byeMsg() {
        return "Bye. Hope to see you again soon!";
    }

    private static String taskAddedMsg(Task task) {
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + bot_brain.size() + " tasks in the list.";
    }

    private static String taskMarkedMsg(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    private static String taskDeletedMsg(Task task) {
        return "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + bot_brain.size() + " tasks in the list.";
    }

    private static String taskListMsg() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < bot_brain.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(bot_brain.get(i));
        }
        return sb.toString();
    }

    public static void converse() {
        while (true) {
            String userInput = ui.readCommand();
            try {
                boolean isExit = Parser.parse(userInput);
                if (isExit) {
                    break;
                }
            } catch (BlubException e) {
                ui.sendMessage("Error: " + e.getMessage());
            }
        }
    }

    public static void list() {
        ui.sendMessage(taskListMsg());
    }

    public static void addTodo(String description) throws BlubException {
        if (description.isEmpty()) {
            throw new BlubException("The description of a todo cannot be empty.");
        }
        Task task = new Todo(description);
        bot_brain.add(task);
        ui.sendMessage(taskAddedMsg(task));
    }

    public static void addDeadline(String input) throws BlubException {
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
        Task task = new Deadline(description, by);
        bot_brain.add(task);
        ui.sendMessage(taskAddedMsg(task));
    }

    public static void addEvent(String input) throws BlubException {
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
        Task task = new Event(description, from, to);
        bot_brain.add(task);
        ui.sendMessage(taskAddedMsg(task));
    }

    public static void mark(int index) {
        Task task = bot_brain.get(index);
        task.markAsDone();
        ui.sendMessage(taskMarkedMsg(task));
    }

    public static void delete(int index) {
        Task task = bot_brain.remove(index);
        ui.sendMessage(taskDeletedMsg(task));
    }

}
