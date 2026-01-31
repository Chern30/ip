package blub;

import blub.task.Task;

import java.io.IOException;

public class blub {

    static String bot_name = "Blub";
    static TaskList taskList = new TaskList();
    static Ui ui = new Ui();
    static Storage storage = new Storage("data/tasks.txt");

    public static void main(String[] args) {
        try {
            taskList = new TaskList(storage.load());
        } catch (IOException e) {
            ui.sendMessage("Error reading from file: " + e.getMessage());
        }
        ui.sendMessage(hiMsg());
        converse();
        try {
            storage.save(taskList.getTasks());
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
                + "\nNow you have " + taskList.size() + " tasks in the list.";
    }

    private static String taskMarkedMsg(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    private static String taskDeletedMsg(Task task) {
        return "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + taskList.size() + " tasks in the list.";
    }

    private static String taskListMsg() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(taskList.get(i));
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
        taskList.addTodo(description);
        ui.sendMessage(taskAddedMsg(taskList.get(taskList.size() - 1)));
    }

    public static void addDeadline(String input) throws BlubException {
        taskList.addDeadline(input);
        ui.sendMessage(taskAddedMsg(taskList.get(taskList.size() - 1)));
    }

    public static void addEvent(String input) throws BlubException {
        taskList.addEvent(input);
        ui.sendMessage(taskAddedMsg(taskList.get(taskList.size() - 1)));
    }

    public static void mark(int index) {
        taskList.mark(index);
        ui.sendMessage(taskMarkedMsg(taskList.get(index)));
    }

    public static void delete(int index) {
        Task task = taskList.delete(index);
        ui.sendMessage(taskDeletedMsg(task));
    }
}
