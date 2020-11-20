package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

/*
INVARIANT: pages > 0;
           0 <= rating <= 10
 */

// Represents a book with a title, author, list of genres, pages, rating /10, and state of whether book has been read;
public class Book implements Writable {
    private String title;
    private String author;
    private List<String> genres;
    private int pages;
    private int rating;
    private boolean finished;

    // EFFECTS: Creates a new book with given title, author, list of genres, pages, rating /10, and state of whether
    //          book has been read;
    public Book(String title, String author, List<String> genres, int pages, int rating, boolean finished) {
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
        this.finished = finished;
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

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // MODIFIES: this
    // EFFECTS: if finished is true, set to false; if finished is false, set to true
    public void toggleFinished() {
        finished = !finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
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

    public boolean getFinished() {
        return finished;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Book book = (Book) o;
//        return pages == book.pages
//                && rating == book.rating
//                && complete == book.complete
//                && title.equals(book.title)
//                && author.equals(book.author)
//                && genres.equals(book.genres);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(title, author, genres, pages, rating, complete);
//    }

    // EFFECTS: converts and returns book as a json object
    @Override
    public JSONObject toJson() {
        JSONObject jsonBook = new JSONObject();

        jsonBook.put("title", title);
        jsonBook.put("author", author);
        JSONArray jsonGenres = new JSONArray();
        for (String g : genres) {
            jsonGenres.put(g);
        }
        jsonBook.put("genres", jsonGenres);
        jsonBook.put("pages", pages);
        jsonBook.put("rating", rating);
        jsonBook.put("finished", finished);

        return jsonBook;
    }
}
