package persistence;

import model.Book;
import model.Library;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Represents a reader that reads a JSON file of a Library
public class JsonReader {
    private String source;

    // EFFECTS: constructs JsonReader to read file from given source path
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: creates and returns a library from JSON file;
    //          throws IOException if error occurs while trying to read file
    public Library read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: reads JSON file data from source path; throws IOException if error occurs while trying to read file
    private String readFile(String source) throws IOException {
        StringBuilder stringData = new StringBuilder();

        List<String> list = Files.readAllLines(Paths.get(source));
        for (String s : list) {
            stringData.append(s);
        }

        return stringData.toString();
    }

    // EFFECTS: parses Library from JSONObject and returns it
    private Library parseLibrary(JSONObject jsonObject) {
        Library library = new Library();
        addBooks(library, jsonObject);

        return library;
    }

    // MODIFIES: library
    // EFFECTS: parses list of books from JSON object and adds to library
    private void addBooks(Library library, JSONObject jsonObject) {
        JSONArray jsonBooks = jsonObject.getJSONArray("books");

        for (Object json : jsonBooks) {
            JSONObject jsonBook = (JSONObject) json;
            addBook(library, jsonBook);
        }
    }

    // MODIFIES: library
    // EFFECTS: parses book from JSON object and adds to library
    private void addBook(Library library, JSONObject jsonBook) {
        String title = jsonBook.getString("title");
        String author = jsonBook.getString("author");

        List<String> genres = new ArrayList<>();
        JSONArray jsonGenres = jsonBook.getJSONArray("genres");
        for (Object jsonGenre : jsonGenres) {
            String genre = (String) jsonGenre;
            genres.add(genre);
        }

        int pages = jsonBook.getInt("pages");
        int rating = jsonBook.getInt("rating");
        boolean finished = jsonBook.getBoolean("finished");

        Book book = new Book(title, author, genres, pages, rating, finished);
        library.addBook(book);
    }
}
