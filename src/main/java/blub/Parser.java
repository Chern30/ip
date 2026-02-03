package blub;

// Abstraction of parser to decode commands in string, to what functions should be called
public class Parser {

    public static boolean parse(String userInput) throws BlubException {
        if (userInput.equals("bye")) {
            return true;
        } else if (userInput.equals("list")) {
            blub.list();
        } else if (userInput.matches("mark \\d+")) {
            blub.mark(Integer.parseInt(userInput.substring(5)) - 1);
        } else if (userInput.matches("delete \\d+")) {
            blub.delete(Integer.parseInt(userInput.substring(7)) - 1);
        } else if (userInput.equals("todo") || userInput.startsWith("todo ")) {
            blub.addTodo(userInput.substring(4).trim());
        } else if (userInput.equals("deadline") || userInput.startsWith("deadline ")) {
            blub.addDeadline(userInput.substring(8).trim());
        } else if (userInput.equals("event") || userInput.startsWith("event ")) {
            blub.addEvent(userInput.substring(5).trim());
        } else {
            throw new BlubException("I'm sorry, but I don't know what that means.");
        }
        return false;
    }
}
