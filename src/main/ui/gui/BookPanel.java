package ui.gui;

import model.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.List;

import static ui.gui.LibraryGUI.*;

// An abstract class representing a panel for a book
public abstract class BookPanel {
    protected LibraryGUI libraryGUI;
    protected JPanel mainDisplay;

    protected Library library;

    protected static String title;
    protected static String author;
    protected static List<String> genres;
    protected static int pages;
    protected static int rating;
    protected static boolean finished;

    protected static JLabel titleLabel;
    protected static JTextField titleField;
    protected static JLabel authorLabel;
    protected static JTextField authorField;
    protected static JLabel genresLabel;
    protected static JTextField genresField;
    protected static JLabel pagesLabel;
    protected static JTextField pagesField;
    protected static JLabel ratingLabel;
    protected static JTextField ratingField;
    protected static JLabel finishedLabel;
    protected static JToggleButton finishedField;

    // EFFECTS: constructs a book panel object
    public BookPanel(LibraryGUI libraryGUI) {
        this.libraryGUI = libraryGUI;
        mainDisplay = libraryGUI.getMainDisplay();

        library = libraryGUI.getLibrary();
    }

    // MODIFIES: this
    // EFFECTS: redraws frame components to add a new book
    protected void displayBookPanel() {
        libraryGUI.setFrameVisibilityFalse();
        setMainDisplayToBookPanel();

        mainDisplay.add(initBookInputsPanel(), BorderLayout.PAGE_START);
        mainDisplay.add(initBookButtonsPanel(), BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: redraws main display as a book panel
    private void setMainDisplayToBookPanel() {
        mainDisplay.removeAll();
        mainDisplay.updateUI();
        mainDisplay.setBackground(BG_COLOR);
        mainDisplay.setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: initializes and draws a new panel for input labels and text fields in book panel
    protected abstract JPanel initBookInputsPanel();

    // MODIFIES: this
    // EFFECTS: initializes inputs panel label and text field fields
    protected void initInputsPanelFields() {
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

    // MODIFIES: this
    // EFFECTS: draws inputs panel labels and text fields
    protected void optimizeInputsPanelFields() {
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

    // MODIFIES: inputsPanel
    // EFFECTS: adds all labels and text fields to inputs panel
    protected void addToInputsPanel(JPanel inputsPanel) {
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

    // EFFECTS: initializes and draws a new panel for bottom buttons in book panel
    protected abstract JPanel initBookButtonsPanel();

    // EFFECTS: initializes a new button that updates library based on inputs, and exits out of book panel back to main
    //          library
    protected abstract JButton initBookOkButton();

    // MODIFIES: this
    // EFFECTS: gets all the inputs from the text field inputs
    protected void getInputsInInputsPanel() {
        title = titleField.getText();
        author = authorField.getText();
        genres = getGenresFieldInput(genresField);
        pages = Integer.parseInt(pagesField.getText());
        rating = Integer.parseInt(ratingField.getText());
        finished = finishedField.isSelected();
    }

    // EFFECTS: converts genres text field input to a string array list
    private List<String> getGenresFieldInput(JTextField genresField) {
        String[] genresArray = genresField.getText().split(GENRES_FIELD_FORMATTING);

        return Arrays.asList(genresArray);
    }

    // EFFECTS: initializes and draws a new button that cancels current action and exits out of book panel back to main
    //          library
    protected JButton initCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(LABEL_FONT);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                libraryGUI.displayLibrary();
            }
        });

        return cancelButton;
    }
}
