package entity;

import java.awt.Color;

/**
 * Represents an individual text post-it note on the panel.
 */
public class TextPostNoteEntity extends PostNoteEntity {
    private Color color; // Color of the post-it note
    private String text; // Text field for editing the post-it label

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

    public void setText(String newText) {
        this.text = newText;
    }
}
