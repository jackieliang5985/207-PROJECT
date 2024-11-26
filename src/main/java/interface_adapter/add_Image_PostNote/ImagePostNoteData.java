package interface_adapter.add_Image_PostNote;

import java.awt.Color;

public class ImagePostNoteData {
    private final String imageUrl;
    private final int x;
    private final int y;
    private final Color color;

    public ImagePostNoteData(String imageUrl, int x, int y, Color color) {
        this.imageUrl = imageUrl;
        this.x = x;
        this.y = y;
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

    public Color getColor() {
        return color;
    }
}
