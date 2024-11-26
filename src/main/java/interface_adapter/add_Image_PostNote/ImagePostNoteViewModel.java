package interface_adapter.add_Image_PostNote;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.InputStream;
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
            URL url = new URL(imageUrl);
            Image image = ImageIO.read(url);  // Load image using ImageIO
            if (image != null) {
                setWidth(image.getWidth(null));  // Set width
                setHeight(image.getHeight(null));  // Set height
                System.out.println("Image loaded successfully: " + imageUrl);
            } else {
                System.out.println("Error: Image not loaded from URL: " + imageUrl);
            }
        } catch (Exception e) {
            System.out.println("Error: Failed to load image from URL: " + imageUrl);
            e.printStackTrace();
        }
    }

}