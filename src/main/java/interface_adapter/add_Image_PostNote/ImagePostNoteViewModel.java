package interface_adapter.add_Image_PostNote;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.net.URL;

public class ImagePostNoteViewModel {
    private String imageUrl;
    private int x, y;
    private int width, height;  // Store width and height of the image
    private Color color;

    // Getters and setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // This method can be used to load the image and set width/height
    public void loadImage() {
        try {
            ImageIcon icon = new ImageIcon(new URL(imageUrl));
            setWidth(icon.getIconWidth());
            setHeight(icon.getIconHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
