import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.tree.DefaultMutableTreeNode;


public class Notepad implements ActionListener {
    JFrame f;
    JMenuBar mb;
    JMenu file, edit, view;

    JMenuItem New, open, save, saveas, print, exit;
    JMenuItem undo, redo, cut, copy, paste, find, replace, selectall, font;
    JMenuItem zoomIn, zoomOut, wordwrap;


    JTextArea ta;
    JTree styleTree;
    JPopupMenu popupMenu;
    private final UndoManager undoManager = new UndoManager();
    private File currentFile;
    private int fontSize = 16;
    private String fontFamily = Font.MONOSPACED;

    Notepad() {
        f = new JFrame("Note Editor");

        New = new JMenuItem("New");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        saveas = new JMenuItem("Save As");
        print = new JMenuItem("Print");
        exit = new JMenuItem("Exit");

        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        find = new JMenuItem("Find");
        replace = new JMenuItem("Replace");
        selectall = new JMenuItem("Select All");
        font = new JMenuItem("Font");

        zoomIn = new JMenuItem("Zoom In");
        zoomOut = new JMenuItem("Zoom Out");
        wordwrap = new JMenuItem("Word Wrap");

        New.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        saveas.addActionListener(this);
        print.addActionListener(this);
        exit.addActionListener(this);

        undo.addActionListener(this);
        redo.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        find.addActionListener(this);
        replace.addActionListener(this);
        selectall.addActionListener(this);
        font.addActionListener(this);

        zoomIn.addActionListener(this);
        zoomOut.addActionListener(this);
        wordwrap.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        view = new JMenu("View");

        file.add(New);
        file.add(open);
        file.add(save);
        file.add(saveas);
        file.add(print);
        file.add(exit);

        edit.add(undo);
        edit.add(redo);
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(find);
        edit.add(replace);
        edit.add(selectall);
        edit.add(font);

        view.add(zoomIn);
        view.add(zoomOut);
        view.add(wordwrap);

        mb.add(file);
        mb.add(edit);
        mb.add(view);

        ta = new JTextArea();
        ta.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
        ta.getDocument().addUndoableEditListener(undoManager);
        ta.setLineWrap(false);
        ta.setWrapStyleWord(true);

        setupPopupMenu();
        styleTree = createStyleTree();

        JTabbedPane tp = new JTabbedPane();
        tp.addTab("Editor", new JScrollPane(ta));
        tp.addTab("Style", new JScrollPane(styleTree));
        tp.addTab("Help", createHelpArea());

        f.setJMenuBar(mb);
        f.add(tp, BorderLayout.CENTER);
        f.setSize(700, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == exit) {
            System.exit(0);
        } else if (source == New) {
            ta.setText("");
            currentFile = null;
            f.setTitle("Note Editor");
            undoManager.discardAllEdits();
        } else if (source == open) {
            openFile();
        } else if (source == save) {
            saveFile(false);
        } else if (source == saveas) {
            saveFile(true);
        } else if (source == print) {
            printFile();
        } else if (source == undo) {
            undoText();
        } else if (source == redo) {
            redoText();
        } else if (source == cut) {
            ta.cut();
        } else if (source == copy) {
            ta.copy();
        } else if (source == paste) {
            ta.paste();
        } else if (source == find) {
            findText();
        } else if (source == replace) {
            replaceText();
        } else if (source == selectall) {
            ta.selectAll();
        } else if (source == font) {
            changeFontSize();
        } else if (source == zoomIn) {
            updateFontSize(fontSize + 2);
        } else if (source == zoomOut) {
            updateFontSize(fontSize - 2);
        } else if (source == wordwrap) {
            boolean wrap = !ta.getLineWrap();
            ta.setLineWrap(wrap);
            ta.setWrapStyleWord(wrap);
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Documents");
        int userSelection = fileChooser.showOpenDialog(f);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                ta.read(reader, null);
                currentFile = selectedFile;
                f.setTitle("Note Editor - " + currentFile.getName());
                undoManager.discardAllEdits();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(f, "Error opening file: " + ex.getMessage());
            }
        }
    }

    private void saveFile(boolean forceChooseLocation) {
        File fileToSave = currentFile;

        if (forceChooseLocation || fileToSave == null) {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Documents");
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(f);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return;
            }
            fileToSave = fileChooser.getSelectedFile();
        }

        try (FileWriter fw = new FileWriter(fileToSave)) {
            fw.write(ta.getText());
            currentFile = fileToSave;
            f.setTitle("Note Editor - " + currentFile.getName());
            JOptionPane.showMessageDialog(f, "File saved successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(f, "Error saving file: " + ex.getMessage());
        }
    }

    private void printFile() {
        try {
            ta.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(f, "Error printing file: " + ex.getMessage());
        }
    }

    private void undoText() {
        try {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        } catch (CannotUndoException ex) {
            JOptionPane.showMessageDialog(f, "Nothing to undo.");
        }
    }

    private void redoText() {
        try {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        } catch (CannotRedoException ex) {
            JOptionPane.showMessageDialog(f, "Nothing to redo.");
        }
    }

    private void findText() {
        String textToFind = JOptionPane.showInputDialog(f, "Enter text to find:");
        if (textToFind == null || textToFind.isEmpty()) {
            return;
        }

        String content = ta.getText();
        int index = content.indexOf(textToFind, ta.getCaretPosition());
        if (index == -1) {
            index = content.indexOf(textToFind);
        }

        if (index >= 0) {
            ta.requestFocus();
            ta.select(index, index + textToFind.length());
        } else {
            JOptionPane.showMessageDialog(f, "Text not found.");
        }
    }

    private void replaceText() {
        String textToFind = JOptionPane.showInputDialog(f, "Text to replace:");
        if (textToFind == null || textToFind.isEmpty()) {
            return;
        }

        String replacement = JOptionPane.showInputDialog(f, "Replace with:");
        if (replacement == null) {
            return;
        }

        String updatedText = ta.getText().replace(textToFind, replacement);
        ta.setText(updatedText);
    }

    private void changeFontSize() {
        String input = JOptionPane.showInputDialog(f, "Enter font size:", fontSize);
        if (input == null || input.isEmpty()) {
            return;
        }

        try {
            updateFontSize(Integer.parseInt(input));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(f, "Enter a valid number.");
        }
    }

    private void updateFontSize(int newSize) {
        if (newSize < 8 || newSize > 72) {
            JOptionPane.showMessageDialog(f, "Font size should be between 8 and 72.");
            return;
        }

        fontSize = newSize;
        applyEditorFont();
    }

    private void applyEditorFont() {
        ta.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
    }

    private void setupPopupMenu() {
        popupMenu = new JPopupMenu("Edit");

        JMenuItem popupCut = new JMenuItem("Cut");
        JMenuItem popupCopy = new JMenuItem("Copy");
        JMenuItem popupPaste = new JMenuItem("Paste");

        popupCut.addActionListener(event -> ta.cut());
        popupCopy.addActionListener(event -> ta.copy());
        popupPaste.addActionListener(event -> ta.paste());

        popupMenu.add(popupCut);
        popupMenu.add(popupCopy);
        popupMenu.add(popupPaste);

        ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupIfTriggered(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupIfTriggered(e);
            }
        });
    }

    private void showPopupIfTriggered(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private JTree createStyleTree() {
        DefaultMutableTreeNode style = new DefaultMutableTreeNode("Style");
        DefaultMutableTreeNode color = new DefaultMutableTreeNode("Color");
        DefaultMutableTreeNode font = new DefaultMutableTreeNode("Font");

        style.add(color);
        style.add(font);

        color.add(new DefaultMutableTreeNode("Black"));
        color.add(new DefaultMutableTreeNode("Blue"));
        color.add(new DefaultMutableTreeNode("Green"));
        color.add(new DefaultMutableTreeNode("Red"));

        font.add(new DefaultMutableTreeNode(Font.MONOSPACED));
        font.add(new DefaultMutableTreeNode(Font.SANS_SERIF));
        font.add(new DefaultMutableTreeNode(Font.SERIF));
        font.add(new DefaultMutableTreeNode(Font.DIALOG));

        JTree tree = new JTree(style);
        tree.addTreeSelectionListener(event -> applyTreeSelection(tree.getLastSelectedPathComponent()));
        return tree;
    }

    private void applyTreeSelection(Object selectedNode) {
        if (!(selectedNode instanceof DefaultMutableTreeNode)) {
            return;
        }

        String value = selectedNode.toString();
        switch (value) {
            case "Black":
                ta.setForeground(Color.BLACK);
                break;
            case "Blue":
                ta.setForeground(Color.BLUE);
                break;
            case "Green":
                ta.setForeground(new Color(0, 128, 0));
                break;
            case "Red":
                ta.setForeground(Color.RED);
                break;
            case Font.MONOSPACED:
            case Font.SANS_SERIF:
            case Font.SERIF:
            case Font.DIALOG:
                fontFamily = value;
                applyEditorFont();
                break;
            default:
                break;
        }
    }

    private JScrollPane createHelpArea() {
        JTextArea helpArea = new JTextArea(
                "Right-click inside the editor to open the popup menu.\n"
                        + "Use the Style tab tree to change the text color or font family.");
        helpArea.setEditable(false);
        helpArea.setLineWrap(true);
        helpArea.setWrapStyleWord(true);
        helpArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        helpArea.setBackground(UIManager.getColor("Panel.background"));
        return new JScrollPane(helpArea);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // Fall back to the default look and feel if the system one is unavailable.
            }
            new Notepad();
        });
    }
}
