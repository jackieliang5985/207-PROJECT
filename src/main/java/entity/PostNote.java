package entity;

import interface_adapter.create_MindMap.SquarePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents an individual post-it note on the panel, with text, connections, and drag functionality.
 */
public class PostNote {
    int x, y, width, height; // initiazlies the position and size of the postit note
    Color color; // Color of the post-it note
    JLabel label; // Display label for the post-it note
    JTextField textField; // Text field for editing the post-it label
    SquarePanel panel; // Reference to the panel so we can repaint
    private boolean dragging = false; // boolean that checks if post-it note is being moved around
    private Point relativePos; // variable to store position of the mouse cursor relative to the postitnote


    public PostNote(int x, int y, int width, int height, Color color, SquarePanel panel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.panel = panel;

        label = new JLabel("", SwingConstants.CENTER);
        label.setBounds(x, y, width, height);
        label.setOpaque(true);
        label.setBackground(color);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // I thought this looked good but we can remove it

        textField = new JTextField();
        textField.setBounds(x, y, width, height);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setVisible(false);

        // This triggers when the user uses the enter button, which is when stopEdting is called.
        textField.addActionListener(e -> stopEditing());

        // This triggers when the user clicks outside of the text label, which is when stopEditing is called.
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                stopEditing();
            }
        });

        // A mouse listener to track if either buttons on the mouse is clicked.
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // If the right button is clicked, the context menu pops up.
                // If the left button is clicked, dragging is set to true and an offset point is created at the
                // mouse position.
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextMenu(e);
                } else if (SwingUtilities.isLeftMouseButton(e)) {
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
     * @param g Graphics object for drawing
     */
    public void draw(Graphics g) {
        // draws the label and the text field, this is called in the SquarePanel class
        label.setLocation(x, y);
        textField.setLocation(x, y);
    }

    /**
     * Moves the postit note to the specified position while dragging.
     * Updates the postit's x and y coordinates and redraws connections.
     * @param point New location of the postit
     */
    public void dragTo(Point point) {
        // point.x/y is set as the new position of the post it note
        // relativePos.x/y makes sure that the post-it note does not teleport the post it to the cursor
        x = point.x - relativePos.x;
        y = point.y - relativePos.y - 50;
        label.setLocation(x, y);
        textField.setLocation(x, y);
        panel.repaint();
    }

    /**
     * Returns the label for this postit.
     * @return JLabel for the postit
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Returns the text field for this postit.
     * @return JTextField for the postit
     */
    public JTextField getTextField() {
        return textField;
    }

    /**
     * Displays a context menu with options to edit, connect, or delete the postit.
     * Prevents deletion of the initial postit.
     * @param e MouseEvent that triggered the menu
     */
    private void showContextMenu(MouseEvent e) {
        JPopupMenu menu = new JPopupMenu();
        System.out.println("pop up menu");
        // calls startEditing when that option is pressed.
        JMenuItem editPostItItem = new JMenuItem("Edit Post-it note");
        editPostItItem.addActionListener(event -> startEditing());

        menu.add(editPostItItem);
        menu.show(label, e.getX(), e.getY());
    }

    /**
     * Starts editing the postit label by displaying the text field.
     */
    public void startEditing() {
        textField.setText(label.getText());
        label.setVisible(false);
        textField.setVisible(true);
        textField.requestFocus();
        textField.selectAll();
    }

    /**
     * Stops editing the postit label by hiding the text field and updating the label.
     */
    public void stopEditing() {
        label.setText(textField.getText());
        textField.setVisible(false);
        label.setVisible(true);
    }

    private void movingResize() {
        int sizeOffset = 10;
        if (dragging) {
            this.width += sizeOffset;
            this.height += sizeOffset;
        }
        else {
            this.width -= sizeOffset;
            this.height -= sizeOffset;
        }
        label.setSize(width, height);
    }
}
