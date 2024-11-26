package interface_adapter.add_Image_PostNote;

import javax.swing.ImageIcon;
import java.awt.Color;

public class ImagePostNoteViewModel {
    private int x, y, width, height;
    private Color color;
    private ImageIcon imageIcon;

    // Getters and setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public ImageIcon getImageIcon() { return imageIcon; }
    public void setImageIcon(ImageIcon imageIcon) { this.imageIcon = imageIcon; }
}
