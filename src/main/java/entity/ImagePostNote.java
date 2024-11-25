package entity;

import view.NotePanel;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class ImagePostNote extends PostNote {
    private ImageIcon imageIcon;

    // Constructor for ImagePostNote
    public ImagePostNote(int x, int y, NotePanel panel) {
        super(x, y, 0, 0, Color.WHITE, panel);  // Start with 0 width and height, we'll set those later
    }

    // Image inside post it note
    public void setImage(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;

        // Update the size of the PostNote based on the image size
        this.width = imageIcon.getIconWidth();  // Set the width of the PostNote to the image width
        this.height = imageIcon.getIconHeight();  // Set the height of the PostNote to the image height

        if (this.label != null) {
            this.label.setIcon(imageIcon);
            this.label.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        }

        // Optionally, adjust the position of the text field or other elements if needed
    }

    // Override the draw method to handle custom image drawing inside the Post-it note
    @Override
    public void draw(Graphics g) {
        label.setLocation(x, y);
        if (imageIcon != null) {
            // Draw the image inside the Post-it note with updated size
            g.drawImage(imageIcon.getImage(), x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
        }
    }

    @Override
    public void dragTo(Point point) {
        x = point.x - relativePos.x;
        y = point.y - relativePos.y - 50;
        label.setLocation(x, y);
        panel.repaint();

        // Adjust image position along with PostNote if needed
        if (imageIcon != null) {
            // Optionally adjust position of the image relative to the post-it note
        }
    }

    /**
     * Displays a context menu with options to edit, connect, or delete the postit.
     * Prevents deletion of the initial postit.
     *
     * @param e MouseEvent that triggered the menu
     */
    @Override
    protected void showContextMenu(MouseEvent e) {
        JPopupMenu menu = new JPopupMenu();
        System.out.println("pop up menu");
        // calls startEditing when that option is pressed.
        JMenuItem editPostItItem = new JMenuItem("Edit Post-it note");
        // editPostItItem.addActionListener(event -> startEditing());

        menu.add(editPostItItem);
        menu.show(label, e.getX(), e.getY());
    }
}
