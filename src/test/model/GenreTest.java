package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {
    private Genre genre;

    @BeforeEach
    void runBefore() {
        genre = new Genre("Philosophy");
    }

    @Test
    void runConstructor() {
        assertEquals("Philosophy", genre.getGenre());
    }
}
