package entity;

import java.awt.*;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImagePostNoteEntity extends PostNoteEntity {
    private String imageUrl;  // Field for the image URL

    // Constructor for the ImagePostNoteEntity
    public ImagePostNoteEntity(int x, int y, int width, int height, MindMapEntity mindMap) {
        super(x, y, width, height, mindMap);
    }

    // Getter for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    // Setter for imageUrl
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Optional: Method to return the ImageIcon from the image URL if needed
    public ImageIcon getImageIcon() {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(new URL(imageUrl));
                return imageIcon;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}