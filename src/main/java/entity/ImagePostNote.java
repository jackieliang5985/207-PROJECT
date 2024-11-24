package entity;

import interface_adapter.create_MindMap.SquarePanel;
import javax.swing.*;
import java.awt.*;

public class ImagePostNote extends PostNote {
    private ImageIcon imageIcon;  // Store the image icon

    // Constructor for ImagePostNote
    public ImagePostNote(int x, int y, int width, int height, Color color, SquarePanel panel) {
        super(x, y, width, height, color, panel);  // Call the parent constructor
    }

    // Set the image for the ImagePostNote
    public void setImage(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        if (this.label != null) {
            this.label.setIcon(imageIcon);  // Set the image in the label
            this.label.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());  // Adjust the label size
        }
    }

    // Override the draw method to handle custom image drawing inside the Post-it note
    @Override
    public void draw(Graphics g) {
        super.draw(g);  // Draw the standard post-it note first

        if (imageIcon != null) {
            // Draw the image inside the Post-it note
            g.drawImage(imageIcon.getImage(), x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
        }
    }

    // Override dragTo to move both the post-it note and the image
    @Override
    public void dragTo(Point point) {
        super.dragTo(point);  // Move the post-it note itself
        // If you want to move the image relative to the note, update the image position too.
        if (imageIcon != null) {
            // Optionally, adjust the image position here if needed
        }
    }
}
