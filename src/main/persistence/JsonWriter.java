package persistence;

import model.Library;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes the JSON representation of a library to the file
public class JsonWriter {
    private static final int INDENT_FACTOR = 2;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a writer that writes to given destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file invalid
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of library to file
    public void write(Library library) {
        JSONObject jsonLibrary = library.toJson();
        saveToFile(jsonLibrary.toString(INDENT_FACTOR));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String jsonLibraryString) {
        writer.print(jsonLibraryString);
    }
}
