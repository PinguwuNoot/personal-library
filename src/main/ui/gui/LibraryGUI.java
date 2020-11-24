package ui.gui;

import model.Book;
import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// GUI application for library
public class LibraryGUI extends JFrame {
    private static final String DEFAULT_FONT_NAME = UIManager.getDefaults().getFont("TabbedPane.font").getFontName();
    private static final Font BOOK_COVER_FONT = new Font(DEFAULT_FONT_NAME, Font.BOLD, 14);
    protected static final Font LABEL_FONT = new Font(DEFAULT_FONT_NAME, Font.BOLD, 24);
    protected static final Font FIELD_FONT = new Font(DEFAULT_FONT_NAME, Font.PLAIN, 20);
    protected static final Color BG_COLOR = new Color(0xFDF6E3);
    private static final Color BOOK_COLOR = new Color(0xD2B48C);
    private static final int WIDTH = 1440;
    private static final int HEIGHT = 810;
    private static final Dimension BOOK_LABEL_DIMENSIONS = new Dimension((WIDTH / 5) - 100, (HEIGHT / 3) - 52);
    protected static final Dimension BUTTONS_PANEL_DIMENSIONS = new Dimension(0, 350);

    private static final String JSON_FILE_PATH = "./data/library.json";
    protected static final String GENRES_FIELD_FORMATTING = ", ";
    protected static final String YES = "Yes";
    protected static final String NO = "No";

    private Library library;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private LibraryGUI libraryGUI;
    private JPanel mainDisplay;
    private JPanel topBar;
    private JPanel bottomBar;
    private BookPanel bookPanel;

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

    // MODIFIES: this
    // EFFECTS: initializes non-GUI fields
    private void initFields() {
        library = new Library();
        jsonReader = new JsonReader(JSON_FILE_PATH);
        jsonWriter = new JsonWriter(JSON_FILE_PATH);
        libraryGUI = this;
    }

    // MODIFIES: this
    // EFFECTS: draws main frame
    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        getContentPane().setBackground(BG_COLOR);
    }

    // MODIFIES: this
    // EFFECTS: initializes and draws top bar of frame and adds to frame
    private void initTopBar() {
        topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.LEADING));
        topBar.setBackground(BG_COLOR);

        topBar.add(initSaveButton());
        topBar.add(initLoadButton());

        add(topBar, BorderLayout.PAGE_START);
    }

    // EFFECTS: initializes a new save button to be used in the top bar
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

    // EFFECTS: saves library to file
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

    // EFFECTS: initializes a new load button to be used in the top bar
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
    // EFFECTS: loads library from file
    private void loadLibrary() {
        try {
            library = jsonReader.read();
            System.out.println("Loaded library from " + JSON_FILE_PATH);
        } catch (IOException e) {
            System.out.println("Unable to read file from " + JSON_FILE_PATH);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes and draws bottom bar of frame and adds to frame
    private void initBottomBar() {
        bottomBar = new JPanel();
        bottomBar.setLayout(new FlowLayout(FlowLayout.TRAILING));
        bottomBar.setBackground(BG_COLOR);

        bottomBar.add(initAddButton());

        add(bottomBar, BorderLayout.PAGE_END);
    }

    // EFFECTS: initializes a new add button to be used in the bottom bar
    private JButton initAddButton() {
        JButton addButton = new JButton("+");
        addButton.setFocusable(false);
        addButton.setFont(LABEL_FONT);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookPanel = new AddBookPanel(libraryGUI);
                bookPanel.displayBookPanel();
            }
        });

        return addButton;
    }

    // MODIFIES: this
    // EFFECTS: initializes main display of frame and adds to a scroll pane, then adds scroll pane to frame
    private void initMainDisplay() {
        mainDisplay = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        displayLibrary();
        add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: draws main library
    protected void displayLibrary() {
        setFrameVisibilityTrue();
        resetMainDisplay();
        addEachBook();
    }

    // MODIFIES: this
    // EFFECTS: sets top and bottom bars to visible
    private void setFrameVisibilityTrue() {
        topBar.setVisible(true);
        bottomBar.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets top and bottom bars to invisible
    protected void setFrameVisibilityFalse() {
        topBar.setVisible(false);
        bottomBar.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: redraws main display panel to display main library
    private void resetMainDisplay() {
        mainDisplay.removeAll();
        mainDisplay.updateUI();
        mainDisplay.setBackground(BG_COLOR);
        mainDisplay.setLayout(new WrapLayout(WrapLayout.LEADING, 80, 6));
//        mainDisplay.setLayout(new FlowLayout(FlowLayout.LEADING, 80, 6));
//        mainDisplay.setLayout(new GridLayout(3, 5, 85, 6));
//        mainDisplay.setLayout(new GridBagLayout());
    }

    // MODIFIES: this
    // EFFECTS: draws each book in library and adds to main display
    private void addEachBook() {
        List<Book> books = library.getBooks();

        for (Book b : books) {
            mainDisplay.add(initBookLabel(b));
        }
    }

    // EFFECTS: initializes and draws a new label to display a book
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
                bookPanel = new CurrentBookPanel(libraryGUI, b);
                bookPanel.displayBookPanel();
            }
        });

        return bookLabel;
    }

    // MODIFIES: this
    // EFFECTS: gets title and author from book and converts it into a string for book label
    private String printBookCover(Book b) {
        String bookCover;
        String title = b.getTitle();
        String author = b.getAuthor();

        bookCover = "<html>" + title + "<br><br>" + author;

        return bookCover;
    }

    public JPanel getMainDisplay() {
        return mainDisplay;
    }

    public Library getLibrary() {
        return library;
    }

    public static void main(String[] args) {
        new LibraryGUI();
    }
}
