package entity;

import view.NotePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class PostNote {
    protected boolean dragging = false; // boolean that checks if post-it note is being moved around
    protected Point relativePos; // variable to store position of the mouse cursor relative to the postitnote
    int x;
    int y;
    int width;
    int height; // initiazlies the position and size of the postit note
    Color color;
    JLabel label; // Display label for the post-it note
    NotePanel panel; // Reference to the panel so we can repaint

    public PostNote(int x, int y, int width, int height, Color color, NotePanel panel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        label = new JLabel("", SwingConstants.CENTER);
        this.panel = panel;

        label.setBounds(x, y, width, height);
        label.setOpaque(true);
        label.setBackground(color);
        // I thought this looked good but we can remove it
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // A mouse listener to track if either buttons on the mouse is clicked.
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // If the right button is clicked, the context menu pops up.
                // If the left button is clicked, dragging is set to true and an offset point is created at the
                // mouse position.
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextMenu(e);
                }
                else if (SwingUtilities.isLeftMouseButton(e)) {
                    dragging = true;
                    movingResize();
                    relativePos = new Point(e.getX(), e.getY());
                }
            }

            // When left button is let go, dragging is set to false.
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    dragging = false;
                    movingResize();
                }
            }

        });

        // This mouse motion listener takes in the mouse motion and sees if component is being dragged, if so:
        // dragTo is called with the point on
        label.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    dragTo(new Point(e.getXOnScreen(), e.getYOnScreen()));
                }
            }
        });
    }

    /**
     * Draws the postit note by setting its label and text field positions on the panel.
     *
     * @param g Graphics object for drawing
     */
    public abstract void draw(Graphics g);

    /**
     * Moves the postit note to the specified position while dragging.
     * Updates the postit's x and y coordinates and redraws connections.
     *
     * @param point New location of the postit
     */
    public abstract void dragTo(Point point);

    /**
     * Returns the label for this postit.
     *
     * @return JLabel for the postit
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Displays a context menu with options to edit, connect, or delete the postit.
     * Prevents deletion of the initial postit.
     *
     * @param e MouseEvent that triggered the menu
     */
    protected abstract void showContextMenu(MouseEvent e);

    protected void movingResize() {
        int sizeOffset = 10;
        if (dragging) {
            this.width += sizeOffset;
            this.height += sizeOffset;
        } else {
            this.width -= sizeOffset;
            this.height -= sizeOffset;
        }
        label.setSize(width, height);
    }
}
