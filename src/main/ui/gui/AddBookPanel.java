package ui.gui;

import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.gui.LibraryGUI.*;

// Represents a book panel when adding a book to the library
public class AddBookPanel extends BookPanel {

    public AddBookPanel(LibraryGUI libraryGUI) {
        super(libraryGUI);
    }

    @Override
    protected JPanel initBookInputsPanel() {
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.PAGE_AXIS));
        inputsPanel.setBackground(BG_COLOR);

        initInputsPanelFields();
        optimizeInputsPanelFields();
        addToInputsPanel(inputsPanel);

        return inputsPanel;
    }

    //EFFECTS: initializes and draws a new panel for bottom buttons in book panel;
    //         adds ok button and cancel button
    @Override
    protected JPanel initBookButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setPreferredSize(BUTTONS_PANEL_DIMENSIONS);
        buttonsPanel.setBackground(BG_COLOR);

        buttonsPanel.add(initBookOkButton());
        buttonsPanel.add(initCancelButton());

        return buttonsPanel;
    }

    // EFFECTS: initializes a new button that adds new book to library based on inputs, and exits out of book panel back
    //          to main library
    @Override
    protected JButton initBookOkButton() {
        JButton okButton = new JButton("OK");
        okButton.setFont(LABEL_FONT);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getInputsInInputsPanel();

                book = new Book(title, author, genres, pages, rating, finished);
                library.addBook(book);

                libraryGUI.displayLibrary();
            }
        });

        return okButton;
    }
}
