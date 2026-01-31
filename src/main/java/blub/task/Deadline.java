package blub.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    private String deadline;

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

    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + deadline;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.deadline + ")";
    }
}
