import java.util.ArrayList;
import java.util.Scanner;

public class blub {

    static String bot_name = "Blub";
    static ArrayList<String> bot_brain = new java.util.ArrayList<String>();

    public static void main(String[] args) {
        sayHi();
        echo();
        sayBye();

    }

    public static void sayHi() {
        String welcome_message =
                "Hello! I am " + bot_name + "\n" +
                "What can I do you for \n\n";
        System.out.println(welcome_message);
    }

    public static void sayBye() {
        String bye_message = "Bye. Hope to see you again soon!\n";
        System.out.println(bye_message);
    }

    public static void echo() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String user_input = sc.nextLine();

            if (user_input.equals("bye")) {
                break;
            }

            System.out.println(user_input);
        }

        sc.close();
    }
}
