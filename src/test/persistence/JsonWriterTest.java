package persistence;

import model.Book;
import model.Library;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    private JsonWriter writer;

    @Test
    void testJsonWriterInvalidFile() {
        try {
            writer = new JsonWriter("./data/my\0illegal:fileName.json");
            Library library = new Library();

            writer.open();
            fail("Expected IOException");
        } catch (IOException e) {

        }
    }

    @Test
    void testJsonWriterEmptyLibrary() {
        try {
            writer = new JsonWriter("./data/testJsonWriterEmptyLibrary.json");
            Library library = new Library();

            writer.open();
            writer.write(library);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterEmptyLibrary.json");
            library = reader.read();

            assertEquals(0, library.size());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    void testJsonWriterNonEmptyLibrary() {
        try {
            writer = new JsonWriter("./data/testJsonWriterNonEmptyLibrary.json");
            Library library = new Library();

            List<String> genres1 = new ArrayList<>();
            genres1.add("Fiction");
            genres1.add("Psychology");
            genres1.add("Philosophy");
            genres1.add("Crime");
            Book book1 = new Book("Crime and Punishment", "Fyodor Dostoevsky", genres1, 545, 10,
                    false);

            List<String> genres2 = new ArrayList<>();
            genres2.add("Nonfiction");
            genres2.add("Psychology");
            genres2.add("Philosophy");
            genres2.add("Autobiography");
            Book book2 = new Book("Man's Search for Meaning", "Viktor Frankl", genres2, 200, 10,
                    true);
            
            library.addBook(book1);
            library.addBook(book2);

            writer.open();
            writer.write(library);
            writer.close();


            JsonReader reader = new JsonReader("./data/testJsonWriterNonEmptyLibrary.json");
            library = reader.read();
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
