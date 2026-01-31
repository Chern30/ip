import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class blub {

    static String bot_name = "Blub";
    static ArrayList<Task> bot_brain = new ArrayList<Task>();
    static String FILE_PATH = "src/main/data/tasks.txt";
    static Ui ui = new Ui();

    public static void main(String[] args) {
        readFromFile();
        ui.sendMessage(hiMsg());
        converse();
        writeToFile();
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
            String user_input = ui.readCommand();

            try {
                if (user_input.equals("bye")) {
                    break;
                } else if (user_input.equals("list")) {
                    ui.sendMessage(taskListMsg());
                } else if (user_input.matches("mark \\d+")) {
                    mark(Integer.parseInt(user_input.substring(5)) - 1);
                } else if (user_input.matches("delete \\d+")) {
                    delete(Integer.parseInt(user_input.substring(7)) - 1);
                } else if (user_input.equals("todo") || user_input.startsWith("todo ")) {
                    addTodo(user_input.substring(4).trim());
                } else if (user_input.equals("deadline") || user_input.startsWith("deadline ")) {
                    addDeadline(user_input.substring(8).trim());
                } else if (user_input.equals("event") || user_input.startsWith("event ")) {
                    addEvent(user_input.substring(5).trim());
                } else {
                    throw new BlubException("I'm sorry, but I don't know what that means.");
                }
            } catch (BlubException e) {
                ui.sendMessage("Error: " + e.getMessage());
            }
        }
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

    public static void writeToFile() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file);
            for (Task task : bot_brain) {
                fw.write(task.toFileString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            ui.sendMessage("Error writing to file: " + e.getMessage());
        }
    }

    public static void readFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                Task task;
                switch (type) {
                case "T":
                    task = new Todo(parts[2]);
                    break;
                case "D":
                    task = new Deadline(parts[2], parts[3]);
                    break;
                case "E":
                    task = new Event(parts[2], parts[3], parts[4]);
                    break;
                default:
                    continue;
                }
                if (isDone) {
                    task.markAsDone();
                }
                bot_brain.add(task);
            }
            sc.close();
        } catch (IOException e) {
            ui.sendMessage("Error reading from file: " + e.getMessage());
        }
    }
}
