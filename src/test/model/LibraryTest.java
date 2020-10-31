package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library books;
    private String title;
    private String author;
    private List<String> genres;
    private int pages;
    private int rating;
    private boolean complete;

    private Book crimeAndPunishment;


    @BeforeEach
    void runBefore() {
        books = new Library();
        title = "Crime and Punishment";
        author = "Fyodor Dostoevsky";
        genres = new ArrayList<>();
        genres.add("Fiction");
        genres.add("Philosophy");
        genres.add("Psychology");
        genres.add("Crime");
        pages = 545;
        rating = 9;
        complete = false;

        crimeAndPunishment = new Book(title, author, genres, pages, rating, complete);
    }

    @Test
    void testConstructor() {
        assertEquals(0, books.size());
    }

    @Test
    void testAddBook() {
        assertEquals(0, books.size());
        assertFalse(books.contains(crimeAndPunishment));

        books.addBook(crimeAndPunishment);

        //crimeAndPunishment = new Book(title, author, genres, pages, rating, complete);

        assertEquals(1, books.size());
        assertTrue(books.contains(crimeAndPunishment));
    }

    @Test
    void testRemoveBook() {
        assertEquals(0, books.size());
        assertFalse(books.contains(crimeAndPunishment));
        books.addBook(crimeAndPunishment);
        assertEquals(1, books.size());
        assertTrue(books.contains(crimeAndPunishment));

        books.removeBook(crimeAndPunishment);
        assertEquals(0, books.size());
        assertFalse(books.contains(crimeAndPunishment));
    }

    @Test
    void testGetAllBooks() {
        List<Book> listOfBooks = new ArrayList<>();
        assertEquals(listOfBooks, books.getBooks());

        listOfBooks.add(crimeAndPunishment);
        books.addBook(crimeAndPunishment);
        assertEquals(listOfBooks, books.getBooks());
    }
}
