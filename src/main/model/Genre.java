package model;

public class Genre {
    private String genre;

    // EFFECTS: this.genre is set to genre
    public Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
