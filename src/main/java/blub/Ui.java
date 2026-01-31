package blub;

import java.util.Scanner;

public class Ui {

    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void sendMessage(String message) {
        System.out.println(message);
    }

    public void close() {
        sc.close();
    }
}
