package ui;

import model.Book;
import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LibraryGUI extends JFrame {
    private static final String DEFAULT_FONT_NAME = UIManager.getDefaults().getFont("TabbedPane.font").getFontName();
    private static final Font BOOK_COVER_FONT = new Font(DEFAULT_FONT_NAME, Font.BOLD, 14);
    private static final Font LABEL_FONT = new Font(DEFAULT_FONT_NAME, Font.BOLD, 24);
    private static final Font FIELD_FONT = new Font(DEFAULT_FONT_NAME, Font.PLAIN, 20);
    private static final Color BG_COLOR = new Color(0xFDF6E3);
    private static final Color BOOK_COLOR = new Color(0xD2B48C);
    private static final int WIDTH = 1440;
    private static final int HEIGHT = 810;
    private static final Dimension BOOK_LABEL_DIMENSIONS = new Dimension((WIDTH / 5) - 100, (HEIGHT / 3) - 52);
    private static final Dimension BUTTONS_PANEL_DIMENSIONS = new Dimension(0, 350);

    private static final String JSON_FILE_PATH = "./data/library.json";
    private static final String GENRES_FIELD_FORMATTING = ", ";
    private static final String YES = "Yes";
    private static final String NO = "No";

    private Library library;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private String title;
    private String author;
    private List<String> genres;
    private int pages;
    private int rating;
    private boolean finished;

//    private GridBagConstraints gc;
    private JPanel mainDisplay;
    private JPanel topBar;
    private JPanel bottomBar;

    private JLabel titleLabel;
    private JTextField titleField;
    private JLabel authorLabel;
    private JTextField authorField;
    private JLabel genresLabel;
    private JTextField genresField;
    private JLabel pagesLabel;
    private JTextField pagesField;
    private JLabel ratingLabel;
    private JTextField ratingField;
    private JLabel finishedLabel;
    private JToggleButton finishedField;

    // EFFECTS: runs library application
    public LibraryGUI() {
        super("Library");

        initFields();

        initFrame();
        initTopBar();
        initBottomBar();
        initMainDisplay();

        pack();
        setVisible(true);
    }

    private void initFields() {
        library = new Library();
        jsonReader = new JsonReader(JSON_FILE_PATH);
        jsonWriter = new JsonWriter(JSON_FILE_PATH);
    }

    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        getContentPane().setBackground(BG_COLOR);
    }

    private void initTopBar() {
        topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.LEADING));
        topBar.setBackground(BG_COLOR);

        topBar.add(initSaveButton());
        topBar.add(initLoadButton());

        add(topBar, BorderLayout.PAGE_START);
    }

    private JButton initSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.setFocusable(false);
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                saveLibrary();
            }
        });

        return saveButton;
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

    private JButton initLoadButton() {
        JButton loadButton = new JButton("Load");
        loadButton.setFocusable(false);
        loadButton.setActionCommand("load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                loadLibrary();
                displayLibrary();
            }
        });

        return loadButton;
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

    private void initBottomBar() {
        bottomBar = new JPanel();
        bottomBar.setLayout(new FlowLayout(FlowLayout.TRAILING));
        bottomBar.setBackground(BG_COLOR);

        bottomBar.add(initAddButton());

        add(bottomBar, BorderLayout.PAGE_END);
    }

    private JButton initAddButton() {
//        JPanel addButtonPanel = new JPanel(); // only if necessary to properly contain bottom of frame

        JButton addButton = new JButton("+");
        addButton.setFocusable(false);
        addButton.setFont(LABEL_FONT);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAddBookPanel();
            }
        });

        return addButton;
    }

    // TODO: refactor into abstract class with displayCurrentBookPanel
    private void displayAddBookPanel() {
        setFrameVisibilityFalse();
        setMainDisplayToBookPanel();

        mainDisplay.add(initNewBookInputsPanel(), BorderLayout.PAGE_START);
        mainDisplay.add(initNewBookButtonsPanel(), BorderLayout.PAGE_END);
    }

    private void setFrameVisibilityFalse() {
        topBar.setVisible(false);
        bottomBar.setVisible(false);
    }

    private void setFrameVisibilityTrue() {
        topBar.setVisible(true);
        bottomBar.setVisible(true);
    }

    private void setMainDisplayToBookPanel() {
        mainDisplay.removeAll();
        mainDisplay.updateUI();
        mainDisplay.setBackground(BG_COLOR);
        mainDisplay.setLayout(new BorderLayout());
    }

    // TODO: refactor into abstract class
    private JPanel initNewBookInputsPanel() {
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.PAGE_AXIS));
        inputsPanel.setBackground(BG_COLOR);

        initInputsPanelFields();
        optimizeInputsPanelFields();
        addToInputsPanel(inputsPanel);

        return inputsPanel;
    }

    private void initInputsPanelFields() {
        titleLabel = new JLabel("Title");
        titleField = new JTextField("");
        authorLabel = new JLabel("Author");
        authorField = new JTextField("");
        genresLabel = new JLabel("Genres");
        genresField = new JTextField("");
        pagesLabel = new JLabel("Pages");
        pagesField = new JTextField("0");
        ratingLabel = new JLabel("Rating (/10)");
        ratingField = new JTextField("0");
        finishedLabel = new JLabel("Finished?");
        finishedField = new JToggleButton();
    }

    private void optimizeInputsPanelFields() {
        titleLabel.setFont(LABEL_FONT);
        titleField.setFont(FIELD_FONT);
        authorLabel.setFont(LABEL_FONT);
        authorField.setFont(FIELD_FONT);
        genresLabel.setFont(LABEL_FONT);
        genresField.setFont(FIELD_FONT);
        pagesLabel.setFont(LABEL_FONT);
        pagesField.setFont(FIELD_FONT);
        ratingLabel.setFont(LABEL_FONT);
        ratingField.setFont(FIELD_FONT);
        finishedLabel.setFont(LABEL_FONT);
        finishedField.setFont(FIELD_FONT);

        finishedField.setText(finishedField.isSelected() ? YES : NO);
        finishedField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                finishedField.setText(finishedField.isSelected() ? YES : NO);
            }
        });
    }

    private void addToInputsPanel(JPanel inputsPanel) {
        inputsPanel.add(titleLabel);
        inputsPanel.add(titleField);
        inputsPanel.add(authorLabel);
        inputsPanel.add(authorField);
        inputsPanel.add(genresLabel);
        inputsPanel.add(genresField);
        inputsPanel.add(pagesLabel);
        inputsPanel.add(pagesField);
        inputsPanel.add(ratingLabel);
        inputsPanel.add(ratingField);
        inputsPanel.add(finishedLabel);
        inputsPanel.add(finishedField);
    }

    // TODO: refactor into abstract class
    private JPanel initNewBookButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setPreferredSize(BUTTONS_PANEL_DIMENSIONS);
        buttonsPanel.setBackground(BG_COLOR);

        buttonsPanel.add(initNewBookOkButton());
        buttonsPanel.add(initCancelButton());

        return buttonsPanel;
    }

    private JButton initNewBookOkButton() {
        JButton okButton = new JButton("OK");
        okButton.setFont(LABEL_FONT);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getInputsInInputsPanel();

                Book newBook = new Book(title, author, genres, pages, rating, finished);
                library.addBook(newBook);

                displayLibrary();
            }
        });

        return okButton;
    }

    private void getInputsInInputsPanel() {
        this.title = titleField.getText();
        this.author = authorField.getText();
        this.genres = getGenresFieldInput(genresField);
        this.pages = Integer.parseInt(pagesField.getText());
        this.rating = Integer.parseInt(ratingField.getText());
        this.finished = finishedField.isSelected();
    }

    private List<String> getGenresFieldInput(JTextField genresField) {
        String[] genresArray = genresField.getText().split(GENRES_FIELD_FORMATTING);

        return Arrays.asList(genresArray);
    }

    private JButton initCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(LABEL_FONT);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLibrary();
            }
        });

        return cancelButton;
    }

    private void initMainDisplay() {
//        gc = new GridBagConstraints();

        mainDisplay = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        displayLibrary();
        add(scrollPane, BorderLayout.CENTER);
    }

    private void displayLibrary() {
        setFrameVisibilityTrue();
        resetMainDisplay();
        addEachBook();

        // temporary
//        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
//        mainDisplay.setBorder(border);
    }

    private void resetMainDisplay() {
        mainDisplay.removeAll();
        mainDisplay.updateUI();
        mainDisplay.setBackground(BG_COLOR);
        mainDisplay.setLayout(new WrapLayout(WrapLayout.LEADING, 80, 6));
//        mainDisplay.setLayout(new FlowLayout(FlowLayout.LEADING, 80, 6));
//        mainDisplay.setLayout(new GridLayout(3, 5, 85, 6));
//        mainDisplay.setLayout(new GridBagLayout());
    }

    private void addEachBook() {
        List<Book> books = library.getBooks();

        for (Book b : books) {
            mainDisplay.add(initBookLabel(b));
        }

//        int i = 0;
//        int j = 0;
//        for (Book b : books) {
//            if (i > 4) {
//                i = 0;
//                j++;
//            }
//
//            setGridBagConstraints(i, j);
//            mainDisplay.add(initBookLabel(b), gc);
//
//            i++;
//        }
    }

//    private void setGridBagConstraints(int i, int j) {
//        gc.gridx = i;
//        gc.gridy = j;
//        gc.weightx = 0.5;
//        gc.weighty = 0.5;
//        gc.fill = GridBagConstraints.BOTH;
//        gc.insets = new Insets(5, 50, 5, 30);
//    }

    private JLabel initBookLabel(Book b) {
        JLabel bookLabel = new JLabel();
        bookLabel.setPreferredSize(BOOK_LABEL_DIMENSIONS);
        bookLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        bookLabel.setBackground(BOOK_COLOR);
        bookLabel.setOpaque(true);
        bookLabel.setFont(BOOK_COVER_FONT);
        bookLabel.setText(printBookCover(b));
        bookLabel.setHorizontalAlignment(JLabel.CENTER);
        bookLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayCurrentBookPanel(b);
            }
        });

        return bookLabel;
    }

    private String printBookCover(Book b) {
        String bookCover;
        this.title = b.getTitle();
        this.author = b.getAuthor();

        bookCover = "<html>" + this.title + "<br><br>" + this.author;

        return bookCover;
    }

    // TODO: refactor into abstract class with displayAddBookPanel
    private void displayCurrentBookPanel(Book b) {
        setFrameVisibilityFalse();
        setMainDisplayToBookPanel();

        mainDisplay.add(initCurrentBookInputsPanel(b), BorderLayout.PAGE_START);
        mainDisplay.add(initCurrentBookButtonsPanel(b), BorderLayout.PAGE_END);
    }

    // TODO: refactor into abstract class
    private JPanel initCurrentBookInputsPanel(Book b) {
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.PAGE_AXIS));
        inputsPanel.setBackground(BG_COLOR); // comment out to see panel sections

        initInputsPanelFields();
        optimizeInputsPanelFields();
        setInputsPanelFields(b);
        addToInputsPanel(inputsPanel);

        return inputsPanel;
    }

    private void setInputsPanelFields(Book b) {
        this.title = b.getTitle();
        this.author = b.getAuthor();
        this.genres = b.getGenres();
        this.pages = b.getPages();
        this.rating = b.getRating();
        this.finished = b.getFinished();

        titleField.setText(this.title);
        authorField.setText(this.author);
        genresField.setText(genresToString());
        pagesField.setText(String.valueOf(this.pages));
        ratingField.setText(String.valueOf(this.rating));
        finishedField.setSelected(this.finished);
    }

    private String genresToString() {
        StringBuilder genresString = new StringBuilder();

        int i = 1;
        for (String g : this.genres) {
            if (i == this.genres.size()) {
                genresString.append(g);
            } else {
                genresString.append(g).append(GENRES_FIELD_FORMATTING);
            }
            i++;
        }

        return genresString.toString();
    }

    private JPanel initCurrentBookButtonsPanel(Book b) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setPreferredSize(BUTTONS_PANEL_DIMENSIONS);
        buttonsPanel.setBackground(BG_COLOR);

        buttonsPanel.add(initCurrentBookOkButton(b));
        buttonsPanel.add(initCancelButton());
        buttonsPanel.add(initDeleteBookButton(b));

        return buttonsPanel;
    }

    private JButton initCurrentBookOkButton(Book b) {
        JButton okButton = new JButton("OK");
        okButton.setFont(LABEL_FONT);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getInputsInInputsPanel();

                b.setTitle(title);
                b.setAuthor(author);
                b.setGenres(genres);
                b.setPages(pages);
                b.setRating(rating);
                b.setFinished(finished);

                displayLibrary();
            }
        });

        return okButton;
    }

    private JPanel initDeleteBookButton(Book b) {
        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.setBackground(BG_COLOR);
        deleteButtonPanel.setBorder(new EmptyBorder(0, 480, 0, 0));

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(LABEL_FONT);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.removeBook(b);

                displayLibrary();
            }
        });

        deleteButtonPanel.add(deleteButton);

        return deleteButtonPanel;
    }

    public static void main(String[] args) {
        new LibraryGUI();
    }
}
