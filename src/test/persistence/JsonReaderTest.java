package persistence;

import model.Book;
import model.Library;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    private JsonReader reader;

    @Test
    void testJsonReaderInvalidFile() {
        try {
            reader = new JsonReader("./data/nullFile.json");
            Library library = reader.read();
            fail("Expected IOException");
        } catch (IOException e) {

        }
    }

    @Test
    void testJsonReaderEmptyLibrary() {
        try {
            reader = new JsonReader("./data/testJsonReaderEmptyLibrary.json");
            Library library = reader.read();
            assertEquals(0, library.size());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    void testJsonReaderNonEmptyLibrary() {
        try {
            reader = new JsonReader("./data/testJsonReaderNonEmptyLibrary.json");
            Library library = reader.read();

            List<String> genres1 = new ArrayList<>();
            genres1.add("Fiction");
            genres1.add("Psychology");
            genres1.add("Philosophy");
            genres1.add("Crime");

            List<String> genres2 = new ArrayList<>();
            genres2.add("Nonfiction");
            genres2.add("Psychology");
            genres2.add("Philosophy");
            genres2.add("Autobiography");

            List<Book> books = library.getBooks();

            assertEquals(2, library.size());
            checkBook("Crime and Punishment", "Fyodor Dostoevsky", genres1, 545, 10,
                    false, books.get(0));
            checkBook("Man's Search for Meaning", "Viktor Frankl", genres2, 200, 10,
                    true, books.get(1));
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }
}
