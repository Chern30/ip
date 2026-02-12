package blub;

import blub.gui.Gui;
import blub.task.Task;

import java.io.IOException;

import javafx.application.Application;

// Main Class
public class blub {

    static String botName = "Blub";
    static TaskList taskList = new TaskList();
    static Ui ui = new Ui();
    static Storage storage = new Storage("data/tasks.txt");

    public blub() {
        try {
            taskList = new TaskList(storage.load());
        } catch (IOException e) {
            taskList = new TaskList();
        }
    }

    public String getResponse(String userInput) {
        try {
            boolean isExit = Parser.parse(userInput);
            if (isExit) {
                try {
                    storage.save(taskList.getTasks());
                } catch (IOException e) {
                    return "Error writing to file: " + e.getMessage();
                }
                return byeMsg();
            }
        } catch (BlubException e) {
            return "Error: " + e.getMessage();
        }
        return ui.getLastMessage();
    }

    public static void main(String[] args) {
        Application.launch(Gui.class, args);
    }

    public String getWelcome() {
        return hiMsg();
    }

    private static String hiMsg() {
        return "Hello! I am " + botName + "\nWhat can I do for you?";
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

    public static void find(String keyword) throws BlubException {
        if (keyword.isEmpty()) {
            throw new BlubException("Please specify a keyword to search for.");
        }
        java.util.ArrayList<Task> matchingTasks = taskList.filterTasks(keyword);
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(matchingTasks.get(i));
        }
        ui.sendMessage(sb.toString());
    }
}
