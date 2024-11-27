package entity;

import java.awt.Color;

/**
 * Represents an individual text post-it note on the panel.
 */
public class TextPostNoteEntity extends PostNoteEntity {
    private Color color;
    private String text;

    public TextPostNoteEntity(int x, int y, int width, int height, Color color, MindMapEntity mindMap) {
        super(x, y, width, height, mindMap);
        this.color = color;
        this.text = "";
    }

    public Color getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
