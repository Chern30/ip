import java.util.ArrayList;
import java.util.Scanner;

public class blub {

    static String bot_name = "Blub";
    static ArrayList<Task> bot_brain = new ArrayList<Task>();

    public static void main(String[] args) {
        sayHi();
        converse();
        sayBye();
    }

    public static void sayHi() {
        System.out.println("Hello! I am " + bot_name);
        System.out.println("What can I do for you?");
    }

    public static void sayBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void converse() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String user_input = sc.nextLine();

            try {
                if (user_input.equals("bye")) {
                    break;
                } else if (user_input.equals("list")) {
                    vomit();
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
                System.out.println("Error: " + e.getMessage());
            }
        }

        sc.close();
    }

    public static void addTodo(String description) throws BlubException {
        if (description.isEmpty()) {
            throw new BlubException("The description of a todo cannot be empty.");
        }
        Task task = new Todo(description);
        bot_brain.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + bot_brain.size() + " tasks in the list.");
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
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + bot_brain.size() + " tasks in the list.");
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
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + bot_brain.size() + " tasks in the list.");
    }

    public static void vomit() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < bot_brain.size(); i++) {
            Task task = bot_brain.get(i);
            System.out.println((i + 1) + ". " + task);
        }
    }

    public static void mark(int index) {
        Task task = bot_brain.get(index);
        task.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public static void delete(int index) {
        Task task = bot_brain.remove(index);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + bot_brain.size() + " tasks in the list.");
    }
}
