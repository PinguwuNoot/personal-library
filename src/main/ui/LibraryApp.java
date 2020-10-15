package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Library application
public class LibraryApp {
    private Library library;
    private Scanner scanner;

    // EFFECTS: Runs the library application
    public LibraryApp() {
        init();
        runLibrary();
    }

    // MODIFIES: this
    // EFFECTS: initializes library
    private void init() {
        library = new Library();
        scanner = new Scanner(System.in);

        String title = "Crime and Punishment";
        String author = "Fyodor Dostoevsky";
        List<String> genres = new ArrayList<>();
        genres.add("Fiction");
        genres.add("Philosophy");
        genres.add("Psychology");
        genres.add("Crime");
        int pages = 545;
        int rating = 9;
        boolean complete = false;

        Book testBook = new Book(title, author, genres, pages, rating, complete);
        library.addBook(testBook);
        Book testBook1 = new Book("Different Title", author, genres, pages, rating, complete);
        library.addBook(testBook1);
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
        System.out.println();

        if (library.size() == 0) {
            System.out.println("Empty library");
        } else {
            for (Book b : library.getAllBooks()) {
                System.out.println(b.getTitle() + " - " + b.getAuthor());
            }
        }
    }

    // EFFECTS: display list of options in library
    private void displayMenu() {
        System.out.println("\n1. Add book to library");
        System.out.println("2. Remove book from library");
        System.out.println("3. View book information");
        System.out.println("4. Edit book information\n");
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
        } else {
            System.out.println("Wrong input");
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

        System.out.print("\nHave you read this book? (y/n)");
        String input = scanner.nextLine();
        boolean complete = input.toLowerCase().equals("y");

        Book book = new Book(title, author, genres, pages, rating, complete);
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
//            boolean complete;
//            if (input.toLowerCase().equals("cancel")) {
//                cancel = true;
//            } else if (input.toLowerCase().equals("y")) {
//                complete = true;
//            } else if (input.toLowerCase().equals("n")) {
//                complete = false;
//            }
//
//            Book book = new Book(title, author, genres, pages, rating, complete);
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
            for (Book b : library.getAllBooks()) {
                System.out.println(i + ". " +  b.getTitle() + " - " + b.getAuthor());
                i++;
            }

            System.out.println("\nChoose book to remove: ");
            int input = scanner.nextInt();
            i = 1;
            for (Book b : library.getAllBooks()) {
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
            for (Book b : library.getAllBooks()) {
                System.out.println(i + ". " + b.getTitle() + " - " + b.getAuthor());
                i++;
            }

            System.out.print("\nChoose book to view: ");
            int input = scanner.nextInt();
            i = 1;
            for (Book b : library.getAllBooks()) {
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

        String genres = "";
        int i = 1;
        for (String g : book.getGenres()) {
            if (i == book.getGenres().size()) {
                genres += g;
            } else {
                genres += g + ", ";
            }
            i++;
        }

        System.out.println("Genres: " + genres);

        System.out.println("Pages: " + book.getPages());
        System.out.println("Rating: " + book.getRating());

        if (book.getComplete()) {
            System.out.println("Finished");
        } else {
            System.out.println("Unfinished");
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
            for (Book b : library.getAllBooks()) {
                System.out.println(i + ". " + b.getTitle() + " - " + b.getAuthor());
                i++;
            }

            System.out.print("\nChoose book to edit: ");
            int input = scanner.nextInt();
            i = 1;
            for (Book b : library.getAllBooks()) {
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
            book.toggleComplete();
        }
    }

    // EFFECTS: displays all information about a book numerically
    private void displayBookInfoNumbered(Book book) {
        System.out.println("\n1. Title: " + book.getTitle());
        System.out.println("2. Author: " + book.getAuthor());

        String genres = "";
        int i = 1;
        for (String g : book.getGenres()) {
            if (i == book.getGenres().size()) {
                genres += g;
            } else {
                genres += g + ", ";
            }
            i++;
        }

        System.out.println("3. Genres: " + genres);

        System.out.println("4. Pages: " + book.getPages());
        System.out.println("5. Rating: " + book.getRating());

        if (book.getComplete()) {
            System.out.println("6. Finished");
        } else {
            System.out.println("6. Unfinished");
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

}
