package blub;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_byeCommand_returnsTrue() throws BlubException {
        assertTrue(Parser.parse("bye"));
    }

    @Test
    public void parse_unknownCommand_throwsBlubException() {
        BlubException exception = assertThrows(BlubException.class, () -> {
            Parser.parse("unknown command");
        });
        assertEquals("I'm sorry, but I don't know what that means.", exception.getMessage());
    }

    @Test
    public void parse_emptyInput_throwsBlubException() {
        assertThrows(BlubException.class, () -> {
            Parser.parse("");
        });
    }

    @Test
    public void parse_randomGibberish_throwsBlubException() {
        assertThrows(BlubException.class, () -> {
            Parser.parse("asdfghjkl");
        });
    }
}
