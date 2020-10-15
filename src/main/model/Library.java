package model;

import java.util.ArrayList;
import java.util.List;

// Represents a library that contains a list of books
public class Library {
    protected List<Book> books;

    // EFFECTS: creates an empty library
    public Library() {
        books = new ArrayList<>();
    }

    // REQUIRES: library must not already contain book
    // MODIFIES: this
    // EFFECTS: adds book to library
    public void addBook(Book book) {
        books.add(book);
    }

    // REQUIRES: library must already contain book
    // MODIFIES: this
    // EFFECTS: removes book from library
    public void removeBook(Book book) {
        books.remove(book);
    }

    // EFFECTS: returns the number of books in library
    public int size() {
        return books.size();
    }

    // EFFECTS: returns true if library contains book, false otherwise
    public boolean contains(Book book) {
        return books.contains(book);
    }

    // EFFECTS: returns a list of all books in library
    public List<Book> getAllBooks() {
        return books;
    }
}
