package interface_adapter.add_Text_PostNote;

import interface_adapter.add_Connection.PostItNoteViewModel;

import java.awt.Color;

public class TextPostNoteViewModel implements PostItNoteViewModel {
    private String text;
    private int x, y, width, height;
    private Color color;

    // Getters and setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

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

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
