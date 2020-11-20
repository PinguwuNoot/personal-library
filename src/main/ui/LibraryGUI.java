package ui;

import model.Book;
import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// TODO: set layout of frame to BorderLayout and put every component in it
// TODO
// TODO
// TODO
// TODO
// TODO
// TODO: scrollable library when > 12 books added;
public class LibraryGUI extends JFrame {
    private static final String DEFAULT_FONT_NAME = UIManager.getDefaults().getFont("TabbedPane.font").getFontName();
    private static final Color bgColor = new Color(0xfdf6e3);
    private static final int WIDTH = 1440;
    private static final int HEIGHT = 810;
    private static final int MAIN_DISPLAY_WIDTH = WIDTH - 50;
    private static final int MAIN_DISPLAY_HEIGHT = HEIGHT - 150;
    private static final int EDGE_SPACE = 10;
    private static final int TOP_BUTTON_HEIGHT = 30;
    private static final int HEIGHT_SUB_TOP_BAR = HEIGHT - EDGE_SPACE - TOP_BUTTON_HEIGHT - 10;
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
    private JButton saveButton;
    private JButton loadButton;
    private JButton addButton;

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
        initSaveButton();
        initLoadButton();
        initAddButton();
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
        setLayout(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(bgColor);
    }

    private void initSaveButton() {
        saveButton = new JButton("Save");
        saveButton.setFocusable(false);
        saveButton.setBounds(10, EDGE_SPACE, 100, TOP_BUTTON_HEIGHT);
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                saveLibrary();
            }
        });

        add(saveButton);
    }

    private void initLoadButton() {
        loadButton = new JButton("Load");
        loadButton.setFocusable(false);
        loadButton.setBounds(120, EDGE_SPACE, 100, TOP_BUTTON_HEIGHT);
        loadButton.setActionCommand("load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                loadLibrary();
                displayLibrary();
            }
        });

        add(loadButton);
    }

    private void initAddButton() {
//        JPanel addButtonPanel = new JPanel(); // only if necessary to properly contain bottom of frame

        addButton = new JButton("Add");
        addButton.setBounds(WIDTH - 110, HEIGHT - 90, 80, 40);
        addButton.setActionCommand("add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAddBookPanel();
            }
        });

        add(addButton);
    }

    // TODO: refactor into abstract class with displayCurrentBookPanel
    private void displayAddBookPanel() {
        setFrameVisibilityFalse();
        setMainDisplayToBookPanel();

        mainDisplay.add(initNewBookInputsPanel(), BorderLayout.PAGE_START);
        mainDisplay.add(initNewBookButtonsPanel(), BorderLayout.PAGE_END);

//        add(mainDisplay); // ?
    }

    private void setFrameVisibilityFalse() {
        saveButton.setVisible(false);
        loadButton.setVisible(false);
        addButton.setVisible(false);
    }

    private void setFrameVisibilityTrue() {
        saveButton.setVisible(true);
        loadButton.setVisible(true);
        addButton.setVisible(true);
    }

    private void setMainDisplayToBookPanel() {
        mainDisplay.removeAll();
        mainDisplay.updateUI();
        mainDisplay.setSize(WIDTH - 500, HEIGHT_SUB_TOP_BAR - 315);
        mainDisplay.setLocation((WIDTH - (WIDTH - 500)) / 2, (HEIGHT - (HEIGHT_SUB_TOP_BAR - 150)) / 2);
        mainDisplay.setBackground(bgColor);
        mainDisplay.setLayout(new BorderLayout(0, 25));
    }

    // TODO: refactor into abstract class
    private JPanel initNewBookInputsPanel() {
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.PAGE_AXIS));
        inputsPanel.setBackground(bgColor); // comment out to see panel sections; temporary

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
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titleField.setFont(new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18));
        authorLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        authorField.setFont(new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18));
        genresLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        genresField.setFont(new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18));
        pagesLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        pagesField.setFont(new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18));
        ratingLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ratingField.setFont(new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18));
        finishedLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        finishedField.setFont(new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18));
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
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setPreferredSize(new Dimension(0, 45));
        buttonsPanel.setBackground(bgColor);

        buttonsPanel.add(initNewBookOkButton());
        buttonsPanel.add(initCancelButton());

        return buttonsPanel;
    }

    private JButton initNewBookOkButton() {
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getInputsInInputsPanel();

                Book newBook = new Book(title, author, genres, pages, rating, finished);
                library.addBook(newBook);
//                saveLibrary();

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
        getFinishedFieldInput(finishedField);
    }

    private List<String> getGenresFieldInput(JTextField genresField) {
        String[] genresArray = genresField.getText().split(GENRES_FIELD_FORMATTING);

        return Arrays.asList(genresArray);
    }

    private void getFinishedFieldInput(JToggleButton finishedField) {
        this.finished = finishedField.isSelected();
    }

    private JButton initCancelButton() {
        JButton cancelButton = new JButton("Cancel");
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
        displayLibrary();
        add(mainDisplay);
    }

    private void displayLibrary() {
        // temporary
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        mainDisplay.setBorder(border);

        setFrameVisibilityTrue();
        resetMainDisplay();
        addEachBook();

//        add(mainDisplay); // ?
    }

    private void loadLibrary() {
        try {
            library = jsonReader.read();
            System.out.println("Loaded library from " + JSON_FILE_PATH);
        } catch (IOException e) {
            System.out.println("Unable to read file from " + JSON_FILE_PATH);
        }
    }

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

    private void resetMainDisplay() {
        mainDisplay.removeAll();
        mainDisplay.updateUI();
        mainDisplay.setSize(MAIN_DISPLAY_WIDTH, MAIN_DISPLAY_HEIGHT);
        mainDisplay.setLocation((WIDTH - (MAIN_DISPLAY_WIDTH)) / 2, HEIGHT - HEIGHT_SUB_TOP_BAR);
        mainDisplay.setBackground(bgColor);
        mainDisplay.setLayout(new FlowLayout(FlowLayout.LEADING, 65, 6));
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
        bookLabel.setPreferredSize(new Dimension((MAIN_DISPLAY_WIDTH / 5) - 80, (MAIN_DISPLAY_HEIGHT / 3) - 10));
        bookLabel.setFont(new Font(DEFAULT_FONT_NAME, Font.BOLD, 14));
        bookLabel.setText(printBookCover(b));
        bookLabel.setHorizontalAlignment(JLabel.CENTER);

        bookLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayCurrentBookPanel(b);
            }
        });

        Border border = BorderFactory.createLineBorder(Color.BLACK, 3); // temporary
        bookLabel.setBorder(border); // temporary

        return bookLabel;
    }

    private String printBookCover(Book b) {
        String bookCover;
        this.title = b.getTitle();
        this.author = b.getAuthor();
//        this.genres = b.getGenres();
//        StringBuilder genresString = genresToString();
//        this.pages = b.getPages();
//        this.rating = b.getRating();
//        this.finished = b.getFinished();
//        String finishedString = finishedToString();
//
//        bookCover = "<html>" + this.title + "<br>" + this.author + "<br>" + genresString + "<br>" + this.pages +
//        "<br>" + this.rating + "<br>" + finishedString;

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

    private JPanel initCurrentBookInputsPanel(Book b) {
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.PAGE_AXIS));
        inputsPanel.setBackground(bgColor); // comment out to see panel sections

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
        buttonsPanel.setPreferredSize(new Dimension(0, 45));
        buttonsPanel.setBackground(bgColor);

        buttonsPanel.add(initCurrentBookOkButton(b));
        buttonsPanel.add(initCancelButton());
        buttonsPanel.add(initRemoveBookButton(b));

        return buttonsPanel;
    }

    private JButton initCurrentBookOkButton(Book b) {
        JButton okButton = new JButton("OK");
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

    private JPanel initRemoveBookButton(Book b) {
        JPanel removeButtonPanel = new JPanel();
        removeButtonPanel.setBackground(bgColor);
        removeButtonPanel.setBorder(new EmptyBorder(0, 290, 0, 0));

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.removeBook(b);

                displayLibrary();
            }
        });

        removeButtonPanel.add(removeButton);

        return removeButtonPanel;
    }

    public JPanel getMainDisplay() {
        return mainDisplay;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public static void main(String[] args) {
        new LibraryGUI();
    }
}
