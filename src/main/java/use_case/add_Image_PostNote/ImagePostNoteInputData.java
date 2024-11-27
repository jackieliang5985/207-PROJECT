package use_case.add_Image_PostNote;

import java.awt.Color;

public class ImagePostNoteInputData {
    private final String imageUrl;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Color color;

    public ImagePostNoteInputData(String imageUrl, int x, int y, int width, int height, Color color) {
        this.imageUrl = imageUrl;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }
}
