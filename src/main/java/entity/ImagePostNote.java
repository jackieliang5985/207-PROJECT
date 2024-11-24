package entity;

import interface_adapter.create_MindMap.SquarePanel;
import javax.swing.*;
import java.awt.*;

public class ImagePostNote extends PostNote {
    private ImageIcon imageIcon;

    // Constructor for ImagePostNote
    public ImagePostNote(int x, int y, Color color, SquarePanel panel) {
        super(x, y, 0, 0, color, panel);  // Start with 0 width and height, we'll set those later
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
        super.draw(g);

        if (imageIcon != null) {
            // Draw the image inside the Post-it note with updated size
            g.drawImage(imageIcon.getImage(), x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
        }
    }

    @Override
    public void dragTo(Point point) {
        super.dragTo(point);

        // Adjust image position along with PostNote if needed
        if (imageIcon != null) {
            // Optionally adjust position of the image relative to the post-it note
        }
    }
}
