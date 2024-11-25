package interface_adapter.create_MindMap;

import entity.ImagePostNote;
import entity.PostNote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * A child class that inherits JPanel, it will hold all the post-it notes and take care of adding/removing/editing
 */
public class SquarePanel extends JPanel {
    private ArrayList<PostNote> postNotes; // List to hold all post-it notes on the panel

    public SquarePanel(ArrayList<PostNote> postNotes) {
        this.postNotes = postNotes;
        setLayout(null); // Disable layout manager for manual positioning
        setBackground(Color.WHITE); // Set background color of the panel
        setFocusable(true);

        // Add a mouse listener to detect right-clicks on the panel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextMenu(e.getPoint());
                }
            }
        });
    }

    /**
     * Displays a context menu at the clicked position with an option to create a new post-it note.
     * @param point The position where the context menu should be displayed
     */
    private void showContextMenu(Point point) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem createNoteItem = new JMenuItem("Create New Post-it Note");
        createNoteItem.addActionListener(event -> createPostNote());

        menu.add(createNoteItem);
        menu.show(this, point.x, point.y);
    }

    /**
     * Creates a new post-it note at the specified position.
     */
    public void createPostNote() {
        PostNote newPostNote = new PostNote(100, 100, 100, 100, Color.ORANGE, this);
        add(newPostNote.getLabel());
        add(newPostNote.getTextField());
        repaint();
        postNotes.add(newPostNote);
    }

    /**
     * Creates a new ImagePostNote at the specified position.
     */
    public void createPostNote(ImagePostNote imagePostNote) {
        add(imagePostNote.getLabel());
        repaint();
        postNotes.add(imagePostNote);
    }
}
