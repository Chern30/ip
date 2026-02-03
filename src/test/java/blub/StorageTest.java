package blub;

import blub.task.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void load_fileNotExists_returnsEmptyList() throws IOException {
        String filePath = tempDir.resolve("nonexistent.txt").toString();
        Storage storage = new Storage(filePath);

        ArrayList<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());
    }

    @Test
    public void saveAndLoad_singleTodo_success() throws IOException {
        String filePath = tempDir.resolve("tasks.txt").toString();
        Storage storage = new Storage(filePath);
        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("read book"));

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0) instanceof Todo);
        assertEquals("read book", loadedTasks.get(0).getDescription());
    }

    @Test
    public void saveAndLoad_markedTask_preservesDoneStatus() throws IOException {
        String filePath = tempDir.resolve("tasks.txt").toString();
        Storage storage = new Storage(filePath);
        ArrayList<Task> tasksToSave = new ArrayList<>();
        Todo todo = new Todo("finish homework");
        todo.markAsDone();
        tasksToSave.add(todo);

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0).isDone());
    }

    @Test
    public void saveAndLoad_multipleTasks_success() throws IOException {
        String filePath = tempDir.resolve("tasks.txt").toString();
        Storage storage = new Storage(filePath);
        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("read book"));
        tasksToSave.add(new Deadline("submit report", "2024-01-15"));
        tasksToSave.add(new Event("meeting", "Mon 2pm", "Mon 4pm"));

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(3, loadedTasks.size());
        assertTrue(loadedTasks.get(0) instanceof Todo);
        assertTrue(loadedTasks.get(1) instanceof Deadline);
        assertTrue(loadedTasks.get(2) instanceof Event);
    }

    @Test
    public void saveAndLoad_unmarkedTask_preservesNotDoneStatus() throws IOException {
        String filePath = tempDir.resolve("tasks.txt").toString();
        Storage storage = new Storage(filePath);
        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("pending task"));

        storage.save(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertFalse(loadedTasks.get(0).isDone());
    }
}
