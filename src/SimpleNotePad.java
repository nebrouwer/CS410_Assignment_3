// Nick Brouwer
// CS 410
// Assignment 3

import javax.swing.*;
import javax.swing.text.Position;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
    JMenuItem recentFile = new JMenuItem("Recent");
    JMenuItem replace = new JMenuItem("Replace");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem paste = new JMenuItem("Paste");
    ArrayList<File> recents = new ArrayList<>();

    public SimpleNotePad()
    {
        setTitle("A Simple Notepad Tool");
        fileMenu.add(newFile);
        fileMenu.addSeparator();
        fileMenu.add(openFile);
        fileMenu.addSeparator();
        fileMenu.add(saveFile);
        fileMenu.addSeparator();
        fileMenu.add(printFile);
        fileMenu.addSeparator();
        fileMenu.add(recentFile);
        //recentFile.add(recent);

        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(replace);

        newFile.addActionListener(this::actionNew);
        //actionNew.setActionCommand("new");
        saveFile.addActionListener(this::actionSave);
        //actionSave.setActionCommand("save");
        printFile.addActionListener(this::actionPrint);
        //actionPrint.setActionCommand("print");
        copy.addActionListener(this::actionCopy);
        //actionCopy.setActionCommand("copy");
        paste.addActionListener(this::actionPaste);
        //actionPaste.setActionCommand("paste");
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

    private void actionOpen(ActionEvent e){
        File fileToOpen = null;
        JFileChooser file = new JFileChooser();
        int result = file.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
            fileToOpen = file.getSelectedFile();
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(fileToOpen)));
            text.read(input, "Opening...");
            input.close();
        } catch (IOException ex) {
        }
    }

    private void actionReplace(ActionEvent e){
        JOptionPane replaceText = new JOptionPane();
        text.replaceSelection(replaceText.showInputDialog("Enter or paste text to insert: "));
    }

    private void actionRecent(){

    }

    private void actionNew(ActionEvent e){
        text.setText("");
    }

    private void actionPaste(ActionEvent e) {
        StyledDocument doc = text.getStyledDocument();
        Position position = doc.getEndPosition();
        System.out.println("offset"+position.getOffset());
        text.paste();
    }

    private void actionCopy(ActionEvent e) {
        text.copy();
    }

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
        }
    }

    /*@Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("new")) {
            text.setText("");
        }else if(e.getActionCommand().equals("save")) {
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
            }
        }else if(e.getActionCommand().equals("print")) {
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
        }else if(e.getActionCommand().equals("copy")) {
            text.copy();
        }else if(e.getActionCommand().equals("paste")) {
            StyledDocument doc = text.getStyledDocument();
            Position position = doc.getEndPosition();
            System.out.println("offset"+position.getOffset());
            text.paste();
        }
    }*/
}