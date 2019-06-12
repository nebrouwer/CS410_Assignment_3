// Nick Brouwer
// CS 410
// Assignment 3

package SimpleNotePad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleNotePadGUI extends JFrame {
    Actions actions = new Actions();

    JMenuBar menu = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    JMenuItem newFile = new JMenuItem("New File");
    JMenuItem saveFile = new JMenuItem("Save File");
    JMenuItem printFile = new JMenuItem("Print File");
    JMenuItem openFile = new JMenuItem("Open File");
    JMenuItem replace = new JMenuItem("Replace");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem paste = new JMenuItem("Paste");

    public SimpleNotePadGUI()
    {
        setTitle("A Simple Notepad Tool");
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(printFile);
        fileMenu.add(actions.recentFile);

        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(replace);

        // ActionListener for New File function.
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.actionNew(e);
            }
        });
        // ActionListener for Save File function.
        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.actionSave(e);
            }
        });
        // ActionListener for Print File function.
        printFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.actionPrint(e);
            }
        });
        // ActionListener for Copy File function.
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.actionCopy(e);
            }
        });
        // ActionListener for Paste function.
        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.actionPaste(e);
            }
        });
        // ActionListener for Open File function.
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.actionOpen(e);
            }
        });
        // ActionListener for Replace function.
        replace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.actionReplace(e);
            }
        });

        menu.add(fileMenu);
        menu.add(editMenu);
        setJMenuBar(menu);
        add(new JScrollPane(actions.getTextPane()));
        setPreferredSize(new Dimension(600,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
}
