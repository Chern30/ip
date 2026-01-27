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

            if (user_input.equals("bye")) {
                break;
            } else if (user_input.equals("list")) {
                vomit();
            } else if (user_input.matches("mark \\d+")) {
                mark(Integer.parseInt(user_input.substring(5)) - 1);
            } else if (user_input.startsWith("todo ")) {
                addTodo(user_input.substring(5));
            } else if (user_input.startsWith("deadline ")) {
                addDeadline(user_input.substring(9));
            } else if (user_input.startsWith("event ")) {
                addEvent(user_input.substring(6));
            } else {
                System.out.println("Unknown command");
            }

        }

        sc.close();
    }

    public static void addTodo(String description) {
        Task task = new Todo(description);
        bot_brain.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + bot_brain.size() + " tasks in the list.");
    }

    public static void addDeadline(String input) {
        String[] parts = input.split(" /by ");
        String description = parts[0];
        String by = parts[1];
        Task task = new Deadline(description, by);
        bot_brain.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + bot_brain.size() + " tasks in the list.");
    }

    public static void addEvent(String input) {
        String[] parts = input.split(" /from ");
        String description = parts[0];
        String[] timeParts = parts[1].split(" /to ");
        String from = timeParts[0];
        String to = timeParts[1];
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
}
