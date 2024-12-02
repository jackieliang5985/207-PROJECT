package interface_adapter.add_Text_PostNote;

import java.awt.Color;

import interface_adapter.add_Connection.PostItNoteViewModel;

/**
 * The view model for a text post-it note.
 * This class stores the state of a text post-it note for presentation in the UI.
 */
public class TextPostNoteViewModel implements PostItNoteViewModel {
    private String text;
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private String id;

    // Getters and setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public void setX(int xValue) {
        this.x = xValue;
    }

    public int getY() {
        return y;
    }

    public void setY(int yValue) {
        this.y = yValue;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
