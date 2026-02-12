package blub;

// Abstraction of the UI accessible to the user
public class Ui {

    private String lastMessage = "";

    // Sends a string to user
    public void sendMessage(String message) {
        lastMessage = message;
    }

    // Returns the last message sent
    public String getLastMessage() {
        return lastMessage;
    }
}
