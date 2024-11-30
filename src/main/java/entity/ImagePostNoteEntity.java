package entity;

import java.net.URL;

import javax.swing.ImageIcon;

public class ImagePostNoteEntity extends PostNoteEntity {
    private String imageUrl;

    // Constructor for the ImagePostNoteEntity
    public ImagePostNoteEntity(int x, int y, int width, int height, MindMapEntity mindMap, String imageUrl) {
        super(x, y, width, height, mindMap);
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates cannot be negative.");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive values.");
        }
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty.");
        }
        this.imageUrl = imageUrl;
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
                final ImageIcon imageIcon = new ImageIcon(new URL(imageUrl));
                return imageIcon;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
