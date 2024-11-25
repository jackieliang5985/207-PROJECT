package entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

/**
 * Represents an individual post-it note on the panel, with text, connections, and drag functionality.
 */
public class TextPostNote extends PostNote {
    private Color color; // Color of the post-it note
    private JTextField textField; // Text field for editing the post-it label


    public TextPostNote(int x, int y, int width, int height, Color color, MindMap panel) {
        super(x, y, width, height, color, panel);

        textField = new JTextField();
        textField.setBounds(x, y, width, height);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setVisible(false);

        // This triggers when the user uses the enter button, which is when stopEditing is called.
        textField.addActionListener(e -> stopEditing());

        // This triggers when the user clicks outside of the text label, which is when stopEditing is called.
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                stopEditing();
            }
        });
    }

    /**
     * Returns the text field for this postit.
     * @return JTextField for the postit
     */
    public JTextField getTextField() {
        return textField;
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

    /**
     * Draws the postit note by setting its label and text field positions on the panel.
     *
     * @param g Graphics object for drawing
     */
    public void draw(Graphics g) {
        // draws the label and the text field, this is called in the MindMap class
        label.setLocation(x, y);
        textField.setLocation(x, y);
    }

    /**
     * Moves the postit note to the specified position while dragging.
     * Updates the postit's x and y coordinates and redraws connections.
     *
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

    public void changeColor() {
//        final Color newColor = JColorChooser.showDialog(null, "Choose a color", this.color);
//        if (newColor != null) {
//
//            selectedNode.setColor(newColor);
//            repaint();
//        }
    }

    /**
     * Displays a context menu with options to edit, connect, or delete the postit.
     * Prevents deletion of the initial postit.
     *
     * @param e MouseEvent that triggered the menu
     */
    protected void showContextMenu(MouseEvent e) {
        JPopupMenu menu = new JPopupMenu();
        System.out.println("pop up menu");
        // calls startEditing when that option is pressed.
        final JMenuItem editPostItItem = new JMenuItem("Edit Post-it note");
        final JMenuItem changeColorItem = new JMenuItem("Change color");
        editPostItItem.addActionListener(event -> startEditing());
        changeColorItem.addActionListener(event -> changeColor());

        menu.add(editPostItItem);
        menu.show(label, e.getX(), e.getY());
    }
}
