package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library library;
    private String title;
    private String author;
    private List<String> genres;
    private int pages;
    private int rating;
    private boolean complete;

    private Book crimeAndPunishment;


    @BeforeEach
    void runBefore() {
        library = new Library();
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
        assertEquals(0, library.size());
    }

    @Test
    void testAddBook() {
        assertEquals(0, library.size());
        assertFalse(library.contains(crimeAndPunishment));

        library.addBook(crimeAndPunishment);

        assertEquals(1, library.size());
        assertTrue(library.contains(crimeAndPunishment));
    }

    @Test
    void testRemoveBook() {
        assertEquals(0, library.size());
        assertFalse(library.contains(crimeAndPunishment));
        library.addBook(crimeAndPunishment);
        assertEquals(1, library.size());
        assertTrue(library.contains(crimeAndPunishment));

        library.removeBook(crimeAndPunishment);
        assertEquals(0, library.size());
        assertFalse(library.contains(crimeAndPunishment));
    }
}
