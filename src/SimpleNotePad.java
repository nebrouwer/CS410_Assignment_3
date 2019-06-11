// Nick Brouwer
// CS 410
// Assignment 3

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.LinkedList;

public class SimpleNotePad extends JFrame{
    JMenuBar menu = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    JTextPane text = new JTextPane();
    JMenuItem newFile = new JMenuItem("New File");
    JMenuItem saveFile = new JMenuItem("Save File");
    JMenuItem printFile = new JMenuItem("Print File");
    JMenuItem openFile = new JMenuItem("Open File");
    JMenu recentFile = new JMenu("Recent");
    JMenuItem replace = new JMenuItem("Replace");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem paste = new JMenuItem("Paste");
    LinkedList<File> recents = new LinkedList<>();

    public SimpleNotePad()
    {
        setTitle("A Simple Notepad Tool");
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(printFile);
        fileMenu.add(recentFile);

        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(replace);

        newFile.addActionListener(this::actionNew);
        saveFile.addActionListener(this::actionSave);
        printFile.addActionListener(this::actionPrint);
        copy.addActionListener(this::actionCopy);
        paste.addActionListener(this::actionPaste);
        openFile.addActionListener(this::actionOpen);
        replace.addActionListener(this::actionReplace);

        menu.add(fileMenu);
        menu.add(editMenu);
        setJMenuBar(menu);
        add(new JScrollPane(text));
        setPreferredSize(new Dimension(600,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        SimpleNotePad app = new SimpleNotePad();
    }

    // ActionEvent method for New File function
    private void actionNew(ActionEvent e){
        text.setText("");
    }

    // ActionEvent method for Open File function
    private void actionOpen(ActionEvent e){
        File fileToOpen = null;
        JFileChooser file = new JFileChooser();
        int result = file.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            fileToOpen = file.getSelectedFile();
        }
        open(fileToOpen);
    }

    // Method for opening files
    private void open(File file){
        updateRecent(file);
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            text.read(input, "Opening...");
            input.close();
        } catch (IOException ex) {
        }
    }

    // ActionEvent method for Save File function
    private void actionSave(ActionEvent e) {
        File fileToWrite = null;
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            fileToWrite = fc.getSelectedFile();
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileToWrite));
            out.println(text.getText());
            JOptionPane.showMessageDialog(null, "File is saved successfully...");
            out.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Save failed" + ex, "Save error",
                    JOptionPane.ERROR_MESSAGE);
        }
        updateRecent(fileToWrite);
    }

    // ActionEvent method for Print File function
    private void actionPrint(ActionEvent e) {
        try{
            PrinterJob pjob = PrinterJob.getPrinterJob();
            pjob.setJobName("Sample Command Pattern");
            pjob.setCopies(1);
            pjob.setPrintable(new Printable() {
                public int print(Graphics pg, PageFormat pf, int pageNum) {
                    if (pageNum>0)
                        return Printable.NO_SUCH_PAGE;
                    pg.drawString(text.getText(), 500, 500);
                    paint(pg);
                    return Printable.PAGE_EXISTS;
                }
            });

            if (pjob.printDialog() == false)
                return;
            pjob.print();
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null,
                    "Printer error" + pe, "Printing error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the the file list of the Recent function
    private void updateRecent(File file){
        if(recents.contains(file)){
            recents.remove(file);
        }
        if(recents.size() == 5){
            recents.removeLast();
            recents.addFirst(file);
        }else{
            recents.addFirst(file);
        }
        recentFile.removeAll();
        for(File f : recents){
            JMenuItem r = new JMenuItem(f.getName());
            r.addActionListener(this :: actionRecent);
            r.setActionCommand(f.getAbsolutePath());
            recentFile.add(r);
        }
    }

    // ActionEvent method for Recent function
    private void actionRecent(ActionEvent e){
        open(new File(e.getActionCommand()));
    }

    // ActionEvent method for Copy function
    private void actionCopy(ActionEvent e) {
        text.copy();
    }

    // ActionEvent method for Paste function
    private void actionPaste(ActionEvent e) {
        text.paste();
    }

    // ActionEvent method for Replace function
    private void actionReplace(ActionEvent e){
        String userInput = JOptionPane.showInputDialog("Enter or paste text to replace selection:");
        if(userInput != null) {
            text.replaceSelection(userInput);
        }
    }
}