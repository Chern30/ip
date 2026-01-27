import java.util.ArrayList;
import java.util.Scanner;

public class blub {

    public record Pair(String x, String y) {}

    static String bot_name = "Blub";
    static ArrayList<Pair> bot_brain = new java.util.ArrayList<Pair>();

    public static void main(String[] args) {
        sayHi();
        converse();
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
            } else {
                add(user_input);
            }

            System.out.println("\n");
        }

        sc.close();
    }

    // public static void echo() {
    //     Scanner sc = new Scanner(System.in);

    //     while (true) {
    //         String user_input = sc.nextLine();

    //         if (user_input.equals("bye")) {
    //             break;
    //         }

    //         System.out.println(user_input);
    //     }

    //     sc.close();
    // }

    // public static void accumulate() {
    //     Scanner sc = new Scanner(System.in);

    //     while (true) {
    //         String user_input = sc.nextLine();

    //         if (user_input.equals("list")) {
    //             vomit();
    //             break;
    //         }

    //         bot_brain.add(user_input);
    //         System.out.println("added: " + user_input);
    //     }

    //     sc.close();
    // }

    public static void add(String user_input) {
        bot_brain.add(new Pair( " ", user_input));
        System.out.println("added: " + user_input);
    }

    public static void vomit() {
        for (int i = 0; i < bot_brain.size(); i++) {
            Pair found = bot_brain.get(i);
            System.out.println((i + 1) + ". [" + found.x() + "] " + found.y());
        }
    }

    public static void mark(int index) {
        Pair old_record = bot_brain.get(index);
        bot_brain.set(index, new Pair("X", old_record.y()));
        System.out.println("Nice I have marked this task as done");
        System.out.println("[X] " + old_record.y());
    }
}
