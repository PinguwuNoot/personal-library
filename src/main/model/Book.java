package model;

import java.util.List;

// Represents a book with a title, author, list of genres, pages, rating /10, and state of whether book has been read;
// allAuthors and allGenres represents the total set of authors and genres in library
public class Book {
    private String title;
    private String author;
    private List<String> genres;
    private int pages;
    private int rating;
    private boolean complete;

    // REQUIRES: pages > 0; 0 <= rating <= 10
    // EFFECTS: Creates a new book with given title, author, list of genres, pages, rating /10, and state of whether
    //          book has been read;
    //          If allAuthors does not contain author, add author to allAuthors;
    //          If allGenres does not contain genre in genres, add genre to allGenres
    public Book(String title, String author, List<String> genres, int pages, int rating, boolean complete) {
        this.title = title;
        this.author = author;
        //this.author = new Author(author);

        this.genres = genres;
//        this.genres = new ArrayList<>();
//        for (String g : genres) {
//            this.genres.add(new Genre(g));
//        }

        this.pages = pages;
        this.rating = rating;
        this.complete = complete;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // REQUIRES: genres must not already contain newGenre
    // MODIFIES: this
    // EFFECTS: adds newGenre to genres
    public void addGenre(String genre) {
        genres.add(genre);
    }

    // REQUIRES: genres must already contain newGenre
    // MODIFIES: this
    // EFFECTS: removes newGenre from genres
    public void removeGenre(String genre) {
        genres.remove(genre);
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // MODIFIES: this
    // EFFECTS: if complete is true, set to false; if complete is false, set to true
    public void toggleComplete() {
        complete = !complete;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getPages() {
        return pages;
    }

    public int getRating() {
        return rating;
    }

    public boolean getComplete() {
        return complete;
    }
}
