package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
    private Author author;

    @BeforeEach
    void runBefore() {
        author = new Author("Fyodor Dostoevsky");
    }

    @Test
    void testConstructor() {
        assertEquals("Fyodor Dostoevsky", author.getAuthor());
    }
}