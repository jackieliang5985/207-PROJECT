//package interface_adapter.create_MindMap;
//
//import entity.ImagePostNote;
//import entity.PostNoteEntity;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.ArrayList;
//
///**
// * A child class that inherits JPanel, it will hold all the post-it notes and take care of adding/removing/editing
// */
//public class SquarePanel extends JPanel {
//    private ArrayList<PostNoteEntity> postNotes; // List to hold all post-it notes on the panel
//
//    public SquarePanel(ArrayList<PostNoteEntity> postNotes) {
//        this.postNotes = postNotes;
//        setLayout(null); // Disable layout manager for manual positioning
//        setBackground(Color.WHITE); // Set background color of the panel
//        setFocusable(true);
//
//        // Add a mouse listener to detect right-clicks on the panel
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (SwingUtilities.isRightMouseButton(e)) {
//                    showContextMenu(e.getPoint());
//                }
//            }
//        });
//    }
//
//    /**
//     * Displays a context menu at the clicked position with an option to create a new post-it note.
//     * @param point The position where the context menu should be displayed
//     */
//    private void showContextMenu(Point point) {
//        JPopupMenu menu = new JPopupMenu();
//
//        // Option to create a new Post-it note
//        JMenuItem createNoteItem = new JMenuItem("Create New Post-it Note");
//        createNoteItem.addActionListener(event -> createPostNote(point));
//
//        menu.add(createNoteItem);
//        menu.show(this, point.x, point.y);
//    }
//
//    /**
//     * Creates a new post-it note at the specified position.
//     * The position is passed from the context menu click.
//     */
//    public void createPostNote(Point position) {
//        // Default size and color for new post-it notes
//        int width = 100;
//        int height = 100;
//        Color color = Color.YELLOW;
//
//        // Create a new post-it note with the position
//        PostNoteEntity newPostNote = new PostNoteEntity(position.x, position.y, width, height, color, this);
//
//        // Add the label and text field to the panel
//        add(newPostNote.getLabel());
//        add(newPostNote.getTextField());
//
//        // Add the new post-it note to the list
//        postNotes.add(newPostNote);
//
//        // Repaint the panel to show the new post-it note
//        repaint();
//    }
//
//    /**
//     * Creates a new ImagePostNote at the specified position.
//     * This method is for creating Image-based post-it notes.
//     */
//    public void createPostNote(ImagePostNote imagePostNote) {
//        // Add the label for the image post-it note to the panel
//        add(imagePostNote.getLabel());
//
//        // Add the image post-it note to the list
//        postNotes.add(imagePostNote);
//
//        // Repaint the panel to show the new image post-it note
//        repaint();
//    }
//
//    // Override the paintComponent method to ensure custom drawing if needed
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        // Loop through all post-notes and draw them
//        for (PostNoteEntity postNote : postNotes) {
//            postNote.draw(g); // Draw each post-it note on the panel
//        }
//    }
//}
