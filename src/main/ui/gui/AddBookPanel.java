package ui.gui;

import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.gui.LibraryGUI.*;

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

    @Override
    protected JButton initBookOkButton() {
        JButton okButton = new JButton("OK");
        okButton.setFont(LABEL_FONT);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getInputsInInputsPanel();

                Book newBook = new Book(title, author, genres, pages, rating, finished);
                library.addBook(newBook);

                libraryGUI.displayLibrary();
            }
        });

        return okButton;
    }
}
