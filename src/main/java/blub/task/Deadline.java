package blub.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    private String deadline;

    // Create Deadline
    public Deadline(String description, String deadline) {
        super(description, TaskType.DEADLINE);
        this.deadline = handleDate(deadline);
    }

    private static String handleDate(String date) {
        try {
            LocalDate parsed = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return parsed.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } catch (DateTimeParseException e) {
            return date;
        }
    }

    // Convert Deadline to storable file format
    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + deadline;
    }

    @Override
    public String toCommandString() {
        return "deadline " + getDescription() + " /by " + deadline;
    }

    // Convert Deadline to String
    @Override
    public String toString() {
        return super.toString() + " (by: " + this.deadline + ")";
    }
}
