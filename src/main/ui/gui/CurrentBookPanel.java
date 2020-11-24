package ui.gui;

import model.Book;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.gui.LibraryGUI.*;

public class CurrentBookPanel extends BookPanel {
    private Book book;

    public CurrentBookPanel(LibraryGUI libraryGUI, Book book) {
        super(libraryGUI);
        this.book = book;
    }

    @Override
    protected JPanel initBookInputsPanel() {
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.PAGE_AXIS));
        inputsPanel.setBackground(BG_COLOR); // comment out to see panel sections

        initInputsPanelFields();
        optimizeInputsPanelFields();
        setInputsPanelFields();
        addToInputsPanel(inputsPanel);

        return inputsPanel;
    }

    // MODIFIES: this
    // EFFECTS: gets book's title, author, list of genres, pages, rating, and finished values, and sets them to the
    // text fields of panel
    private void setInputsPanelFields() {
        title = book.getTitle();
        author = book.getAuthor();
        genres = book.getGenres();
        pages = book.getPages();
        rating = book.getRating();
        finished = book.getFinished();

        titleField.setText(title);
        authorField.setText(author);
        genresField.setText(genresToString());
        pagesField.setText(String.valueOf(pages));
        ratingField.setText(String.valueOf(rating));
        finishedField.setSelected(finished);
    }

    // EFFECTS: converts list of genres to a string to put in the text field
    private String genresToString() {
        StringBuilder genresString = new StringBuilder();

        int i = 1;
        for (String g : genres) {
            if (i == genres.size()) {
                genresString.append(g);
            } else {
                genresString.append(g).append(GENRES_FIELD_FORMATTING);
            }
            i++;
        }

        return genresString.toString();
    }

    @Override
    protected JPanel initBookButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setPreferredSize(BUTTONS_PANEL_DIMENSIONS);
        buttonsPanel.setBackground(BG_COLOR);

        buttonsPanel.add(initBookOkButton());
        buttonsPanel.add(initCancelButton());
        buttonsPanel.add(initDeleteBookButton());

        return buttonsPanel;
    }

    @Override
    protected JButton initBookOkButton() {
        JButton okButton = new JButton("OK");
        okButton.setFont(LABEL_FONT);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getInputsInInputsPanel();

                book.setTitle(title);
                book.setAuthor(author);
                book.setGenres(genres);
                book.setPages(pages);
                book.setRating(rating);
                book.setFinished(finished);

                libraryGUI.displayLibrary();
            }
        });

        return okButton;
    }

    // EFFECTS: initializes a new button to delete book from library
    private JPanel initDeleteBookButton() {
        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.setBackground(BG_COLOR);
        deleteButtonPanel.setBorder(new EmptyBorder(0, 480, 0, 0));

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(LABEL_FONT);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.removeBook(book);

                libraryGUI.displayLibrary();
            }
        });

        deleteButtonPanel.add(deleteButton);

        return deleteButtonPanel;
    }
}
