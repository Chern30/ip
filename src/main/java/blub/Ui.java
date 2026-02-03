package blub;

import java.util.Scanner;


// Abstraction of the CLI accessible to the user
public class Ui {

    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    // Reads and returns a line of command from user
    public String readCommand() {
        return sc.nextLine();
    }

    // Sends a string to user
    public void sendMessage(String message) {
        System.out.println(message);
    }

    // Shut down
    public void close() {
        sc.close();
    }
}
