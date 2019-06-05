// Nick Brouwer
// CS 410
// Assignment 3

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Position;
import javax.swing.text.StyledDocument;

public class SimpleNotePad extends JFrame implements ActionListener{
    JMenuBar menu = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    JTextPane text = new JTextPane();
    JMenuItem newFile = new JMenuItem("New File");
    JMenuItem saveFile = new JMenuItem("Save File");
    JMenuItem printFile = new JMenuItem("Print File");
    JMenuItem undo = new JMenuItem("Undo");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem paste = new JMenuItem("Paste");

    public SimpleNotePad() {
        setTitle("A Simple Notepad Tool");
        fileMenu.add(newFile);
        fileMenu.addSeparator();
        fileMenu.add(saveFile);
        fileMenu.addSeparator();
        fileMenu.add(printFile);
        editMenu.add(undo);
        editMenu.add(copy);
        editMenu.add(paste);
        newFile.addActionListener(this);
        newFile.setActionCommand("new");
        saveFile.addActionListener(this);
        saveFile.setActionCommand("save");
        printFile.addActionListener(this);
        printFile.setActionCommand("print");
        copy.addActionListener(this);
        copy.setActionCommand("copy");
        paste.addActionListener(this);
        paste.setActionCommand("paste");
        undo.addActionListener(this);
        undo.setActionCommand("undo");
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

    @Override
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
        }else if(e.getActionCommand().equals("undo")) {
//TODO: implement undo operation
        }
    }
}