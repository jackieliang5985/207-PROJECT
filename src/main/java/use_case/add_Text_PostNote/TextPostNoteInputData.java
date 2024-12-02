package use_case.add_Text_PostNote;

import java.awt.Color;

public class TextPostNoteInputData {
    private final String text;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Color color;

    // Constructor
    public TextPostNoteInputData(String text, int xValue, int yValue, int width, int height, Color color) {
        this.text = text;
        this.x = xValue;
        this.y = yValue;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public String getText() {
        return text;
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
