package use_case.add_Text_PostNote;

import java.awt.Color;

/**
 * A data transfer object that encapsulates the input data required to add a text post-it note.
 */
public class TextPostNoteInputData {
    private final String text;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Color color;

    /**
     * Constructs a new TextPostNoteInputData object.
     *
     * @param text   The text content of the post-it note.
     * @param xValue The x-coordinate of the post-it note's position.
     * @param yValue The y-coordinate of the post-it note's position.
     * @param width  The width of the post-it note.
     * @param height The height of the post-it note.
     * @param color  The background color of the post-it note.
     */
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
