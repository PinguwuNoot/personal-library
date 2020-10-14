package model;

public class Author {
    private String author;

    // EFFECTS: this.author is set to author
    public Author(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
