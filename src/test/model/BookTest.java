package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private String title;
    private String author;
    private List<String> genres;
    private int pages;
    private int rating;
    private boolean complete;
    private Book book;

    @BeforeEach
    void runBefore() {
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
        book = new Book(title, author, genres, pages, rating, complete);
    }

    @Test
    void testConstructor() {
        assertEquals("Crime and Punishment", book.getTitle());
        assertEquals("Fyodor Dostoevsky", book.getAuthor());
        assertEquals(genres, book.getGenres());
        assertEquals(545, book.getPages());
        assertEquals(9, book.getRating());
        assertFalse(book.getComplete());
    }

    @Test
    void testSetTitle() {
        assertEquals("Crime and Punishment", book.getTitle());

        book.setTitle("Different Title");

        assertEquals("Different Title", book.getTitle());
    }

    @Test
    void testSetAuthor() {
        assertEquals("Fyodor Dostoevsky", book.getAuthor());

        book.setAuthor("Different Author");

        assertEquals("Different Author", book.getAuthor());
    }

    @Test
    void testAddGenre() {
        assertFalse(book.getGenres().contains("Fantasy"));

        book.addGenre("Fantasy");

        assertTrue(book.getGenres().contains("Fantasy"));
    }

    @Test
    void testRemoveGenre() {
        assertFalse(book.getGenres().contains("Fantasy"));
        book.addGenre("Fantasy");
        assertTrue(book.getGenres().contains("Fantasy"));

        book.removeGenre("Fantasy");

        assertFalse(book.getGenres().contains("Fantasy"));
    }

    @Test
    void testSetPages() {
        assertEquals(545, book.getPages());

        book.setPages(100);

        assertEquals(100, book.getPages());
    }

    @Test
    void testSetRating() {
        assertEquals(9, book.getRating());

        book.setRating(10);

        assertEquals(10, book.getRating());
    }

    @Test
    void testToggleComplete() {
        assertFalse(book.getComplete());
        book.toggleComplete();
        assertTrue(book.getComplete());
        book.toggleComplete();
        assertFalse(book.getComplete());
    }
}
