package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Library application
public class LibraryConsoleUI {
    private static final String JSON_FILE_PATH = "./data/library.json";
    private Library library;
    private Scanner scanner;
    JsonWriter jsonWriter;
    JsonReader jsonReader;

    // EFFECTS: Runs the library application
    public LibraryConsoleUI() {
        init();
        jsonWriter = new JsonWriter(JSON_FILE_PATH);
        jsonReader = new JsonReader(JSON_FILE_PATH);
        runLibrary();
    }

    // MODIFIES: this
    // EFFECTS: initializes library
    private void init() {
        library = new Library();
        scanner = new Scanner(System.in);

//        String title = "Crime and Punishment";
//        String author = "Fyodor Dostoevsky";
//        List<String> genres = new ArrayList<>();
//        genres.add("Fiction");
//        genres.add("Philosophy");
//        genres.add("Psychology");
//        genres.add("Crime");
//        int pages = 545;
//        int rating = 9;
//        boolean finished = false;
//
//        Book testBook = new Book(title, author, genres, pages, rating, finished);
//        library.addBook(testBook);
//        Book testBook1 = new Book("Different Title", "Different Author", genres, pages, rating, finished);
//        library.addBook(testBook1);
    }

    // EFFECTS: receives user input
    private void runLibrary() {
        boolean running = true;

        while (running) {
            displayLibrary();
            displayMenu();

            int input = scanner.nextInt();
            processInput(input);
        }
    }

    // EFFECTS: displays all books in library with title and author
    private void displayLibrary() {
        System.out.println("\nLibrary:");

        if (library.size() == 0) {
            System.out.println("Empty library");
        } else {
            for (Book b : library.getBooks()) {
                System.out.println(b.getTitle() + " - " + b.getAuthor());
            }
        }
    }

    // EFFECTS: display list of options in library
    private void displayMenu() {
        System.out.println("\nChoose option:");
        System.out.println("1. Add book to library");
        System.out.println("2. Remove book from library");
        System.out.println("3. View book information");
        System.out.println("4. Edit book information");
        System.out.println("5. Save library");
        System.out.println("6. Load library");
        System.out.println("0. Exit library\n");
    }

    // EFFECTS: processes user input
    private void processInput(int input) {
        if (input == 1) {
            addBook();
        } else if (input == 2) {
            removeBook();
        } else if (input == 3) {
            viewBook();
        } else if (input == 4) {
            editBook();
        } else if (input == 5) {
            saveLibrary();
        } else if (input == 6) {
            loadLibrary();
        } else if (input == 0) {
            System.exit(0);
        } else {
            System.out.println("Invalid input...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new book to library
    private void addBook() {
        scanner.nextLine();
        System.out.print("\nEnter title: ");
        String title = scanner.nextLine();

        System.out.print("\nEnter author: ");
        String author = scanner.nextLine();

        List<String> genres = enterGenres();

        System.out.print("\nEnter number of pages: ");
        int pages = scanner.nextInt();

        System.out.print("\nEnter rating /10: ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        System.out.print("\nHave you read this book? (y/n) ");
        String input = scanner.nextLine();
        boolean finished = input.toLowerCase().equals("y");

        Book book = new Book(title, author, genres, pages, rating, finished);
        library.addBook(book);
    }

    // EFFECTS: returns user inputs for genre as a list
    private List<String> enterGenres() {
        System.out.print("\nEnter genre: ");
        String genre = scanner.nextLine();
        List<String> genres = new ArrayList<>();
        genres.add(genre);

        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print("\nEnter genre or enter 'done' to move on: ");
            genre = scanner.nextLine();

            if (genre.toLowerCase().equals("done")) {
                keepGoing = false;
            } else {
                genres.add(genre);
            }
        }

        return genres;
    }

//    private void addBook() {
//        boolean cancel = false;
//        while (cancel) {
//            System.out.println("\n(Enter 'cancel' to cancel)\nEnter title: ");
//            String title = scanner.next();
//            if (title.toLowerCase().equals("cancel")) {
//                cancel = true;
//            }
//
//            System.out.println("\n(Enter 'cancel' to cancel)\nEnter author: ");
//            String author = scanner.next();
//            if (author.toLowerCase().equals("cancel")) {
//                cancel = true;
//            }
//
//            System.out.println("\n(Enter 'cancel' to cancel)\nEnter genre: ");
//            String genre = scanner.next();
//            List<String> genres = new ArrayList<>();
//            if (genre.toLowerCase().equals("cancel")) {
//                cancel = true;
//            }
//            genres.add(genre);
//
//            boolean done = false;
//            while (done) {
//                System.out.println("\n(Enter 'cancel' to cancel)\nEnter genre or enter 'done' to move on: ");
//                genre = scanner.next();
//
//                if (genre.toLowerCase().equals("cancel")) {
//                    cancel = true;
//                } else if (genre.toLowerCase().equals("done")) {
//                    done = true;
//                } else {
//                    genres.add(genre);
//                }
//            }
//
//            System.out.println("\n(Enter '-1' to cancel)\nEnter number of pages: ");
//            int pages = scanner.nextInt();
//            if (pages == 0) {
//                cancel = true;
//            }
//
//            System.out.println("\n(Enter '-1' to cancel)\nEnter rating /10: ");
//            int rating = scanner.nextInt();
//            if (rating == -1) {
//                cancel = true;
//            }
//
//            System.out.println("\n(Enter 'cancel' to cancel)\nHave you finished this book? (y/n)");
//            String input = scanner.next();
//            boolean finished;
//            if (input.toLowerCase().equals("cancel")) {
//                cancel = true;
//            } else if (input.toLowerCase().equals("y")) {
//                finished = true;
//            } else if (input.toLowerCase().equals("n")) {
//                finished = false;
//            }
//
//            Book book = new Book(title, author, genres, pages, rating, finished);
//            library.addBook(book);
//            break;
//        }
//    }

    // MODIFIES: this
    // EFFECTS: removes chosen book from library
    private void removeBook() {
        System.out.println();

        if (library.size() == 0) {
            System.out.println("No books to remove");
        } else {
            int i = 1;
            for (Book b : library.getBooks()) {
                System.out.println(i + ". " + b.getTitle() + " - " + b.getAuthor());
                i++;
            }

            System.out.println("\nChoose book to remove: ");
            int input = scanner.nextInt();
            i = 1;
            for (Book b : library.getBooks()) {
                if (input == i) {
                    library.removeBook(b);
                    break;
                }
                i++;
            }
        }
    }

    // EFFECTS: displays information about chosen book
    private void viewBook() {
        System.out.println();

        if (library.size() == 0) {
            System.out.println("No books to view");
        } else {
            int i = 1;
            for (Book b : library.getBooks()) {
                System.out.println(i + ". " + b.getTitle() + " - " + b.getAuthor());
                i++;
            }

            System.out.print("\nChoose book to view: ");
            int input = scanner.nextInt();
            i = 1;
            for (Book b : library.getBooks()) {
                if (input == i) {
                    displayBookInfo(b);
                    break;
                }
                i++;
            }
        }
    }

    // EFFECTS: displays all information about a book
    private void displayBookInfo(Book book) {
        System.out.println("\nTitle: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());

        StringBuilder genres = new StringBuilder();
        int i = 1;
        for (String g : book.getGenres()) {
            if (i == book.getGenres().size()) {
                genres.append(g);
            } else {
                genres.append(g).append(", ");
            }
            i++;
        }
        System.out.println("Genres: " + genres.toString());

        System.out.println("Pages: " + book.getPages());
        System.out.println("Rating: " + book.getRating());

        if (book.getFinished()) {
            System.out.println("Finished?: Yes");
        } else {
            System.out.println("Finished?: No");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates information about chosen book
    private void editBook() {
        System.out.println();

        if (library.size() == 0) {
            System.out.println("No books to edit");
        } else {
            int i = 1;
            for (Book b : library.getBooks()) {
                System.out.println(i + ". " + b.getTitle() + " - " + b.getAuthor());
                i++;
            }

            System.out.print("\nChoose book to edit: ");
            int input = scanner.nextInt();
            i = 1;
            for (Book b : library.getBooks()) {
                if (input == i) {
                    editInfo(b);
                    break;
                }
                i++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the specified type of information in book
    private void editInfo(Book book) {
        displayBookInfoNumbered(book);

        System.out.print("\nChoose which information to edit: ");
        int input = scanner.nextInt();
        scanner.nextLine();

        if (input == 1) {
            editTitle(book);
        } else if (input == 2) {
            editAuthor(book);
        } else if (input == 3) {
            editGenre(book);
        } else if (input == 4) {
            editPages(book);
        } else if (input == 5) {
            editRating(book);
        } else if (input == 6) {
            book.toggleFinished();
        }
    }

    // EFFECTS: displays all information about a book numerically
    private void displayBookInfoNumbered(Book book) {
        System.out.println("\n1. Title: " + book.getTitle());
        System.out.println("2. Author: " + book.getAuthor());

        StringBuilder genres = new StringBuilder();
        int i = 1;
        for (String g : book.getGenres()) {
            if (i == book.getGenres().size()) {
                genres.append(g);
            } else {
                genres.append(g).append(", ");
            }
            i++;
        }
        System.out.println("3. Genres: " + genres.toString());

        System.out.println("4. Pages: " + book.getPages());
        System.out.println("5. Rating: " + book.getRating());

        if (book.getFinished()) {
            System.out.println("6. Finished?: Yes");
        } else {
            System.out.println("6. Finished?: No");
        }
    }

    // MODIFIES: this
    // EFFECTS: changes title of book
    private void editTitle(Book book) {
        System.out.print("\nEnter new title: ");
        String title = scanner.nextLine();
        book.setTitle(title);
    }

    // MODIFIES: this
    // EFFECTS: change author of book
    private void editAuthor(Book book) {
        System.out.print("\nEnter new author: ");
        String author = scanner.nextLine();
        book.setAuthor(author);
    }

    // MODIFIES: this
    // EFFECTS: adds or removes genre from book
    private void editGenre(Book book) {
        int input;
        System.out.print("\nAdd or remove genre? ('1' or '2'): ");
        input = scanner.nextInt();
        scanner.nextLine();

        String genre;
        if (input == 1) {
            System.out.print("Enter new genre: ");
            genre = scanner.nextLine();
            book.addGenre(genre);
        } else if (input == 2) {
            System.out.print("Remove existing genre: ");
            genre = scanner.nextLine();
            book.removeGenre(genre);
        }
    }

    // MODIFIES: this
    // EFFECTS: changes number of pages of book
    private void editPages(Book book) {
        System.out.print("\nEnter new number of pages: ");
        int pages = scanner.nextInt();
        book.setPages(pages);
        scanner.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: changes rating of book
    private void editRating(Book book) {
        System.out.print("\nEnter new rating: ");
        int rating = scanner.nextInt();
        book.setRating(rating);
        scanner.nextLine();
    }


    // EFFECTS: save library to file
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            System.out.println("Saved library to " + JSON_FILE_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file to " + JSON_FILE_PATH);
        }
    }

    // MODIFIES: this
    // EFFECTS: load library from file
    private void loadLibrary() {
        try {
            library = jsonReader.read();
            System.out.println("Loaded library from " + JSON_FILE_PATH);
        } catch (IOException e) {
            System.out.println("Unable to read file from " + JSON_FILE_PATH);
        }
    }

    public static void main(String[] args) {
        new LibraryConsoleUI();
    }
}
