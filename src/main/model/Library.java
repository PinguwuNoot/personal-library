package model;

import java.util.ArrayList;
import java.util.List;

// Represents a library that contains a list of books
public class Library {
    protected List<Book> library;

    // EFFECTS: creates an empty library
    public Library() {
        library = new ArrayList<>();
    }

    // REQUIRES: library must not already contain book
    // MODIFIES: this
    // EFFECTS: adds book to library
    public void addBook(Book book) {
        library.add(book);
    }

    // REQUIRES: library must already contain book
    // MODIFIES: this
    // EFFECTS: removes book from library
    public void removeBook(Book book) {
        library.remove(book);
    }

    // EFFECTS: returns the number of books in library
    public int size() {
        return library.size();
    }

    // EFFECTS: returns true if library contains book, false otherwise
    public boolean contains(Book book) {
        return library.contains(book);
    }
}
