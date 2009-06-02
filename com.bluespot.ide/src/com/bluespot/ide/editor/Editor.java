package com.bluespot.ide.editor;


import com.bluespot.swing.Dialogs;
import com.bluespot.swing.Dialogs.CancelledException;

import java.awt.Font;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class Editor extends JTextPane {

    // TODO Implement undo/redo functionality
    // TODO Implement cut/copy/paste functionality

    private File file;

    protected DocumentListener documentListener = new DocumentListener() {

        public void changedUpdate(DocumentEvent e) {
            Editor.this.dirty();
        }

        public void insertUpdate(DocumentEvent e) {
            Editor.this.dirty();
        }

        public void removeUpdate(DocumentEvent e) {
            Editor.this.dirty();
        }

    };

    private boolean dirty;

    public Editor() {
        this(null);
    }

    public Editor(File file) {
        this.setFile(file);
        this.addPropertyChangeListener("document", new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    Editor.this.getDocument().addDocumentListener(Editor.this.documentListener);
                }

            });
        this.setFont(new Font("consolas", Font.PLAIN, 14));
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void dirty() {
        this.dirty = true;
    }

    protected void clearDirty() {
        this.dirty = false;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.dirty();
        this.file = file;
        if (this.file != null) {
            this.setName(this.file.getName());
        }
    }

    public boolean trySave() {
        try {
            this.save();
            return true;
        } catch (IOException ex) {
            Dialogs.error("The file could not be saved. (Error: " + ex.getLocalizedMessage() + ")",
                          "File I/O Exception");
        } catch (CancelledException e) {
            // Do nothing, fall-through.
        }
        return false;
    }

    public void save() throws IOException, CancelledException {
        if (this.getFile() != null && this.isDirty() == false)
            return;
        this.save(this.getFile());
    }

    public void save(File destinationFile) throws IOException, CancelledException {
        if (destinationFile == null) {
            destinationFile = Dialogs.saveFile();
        }
        this.setFile(destinationFile);
        Writer writer = new FileWriter(this.getFile());
        try {
            this.write(writer);
            this.clearDirty();
        } finally {
            writer.close();
        }

    }

    public void open() throws IOException {
        this.open(this.getFile());
    }

    public void open(File openedFile) throws IOException {
        this.setFile(openedFile);
        Reader reader = new FileReader(openedFile);
        try {
            this.setDocument(this.getEditorKit().createDefaultDocument());
            this.read(reader, openedFile);
            SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        Editor.this.clearDirty();
                    }

                });
        } finally {
            reader.close();
        }
    }

    public static Editor openFile(File file) throws IOException {
        Editor editor = new Editor(file);
        editor.open();
        return editor;
    }
}
