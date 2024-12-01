package use_case.add_Text_PostNote;

import java.awt.*;

import entity.TextPostNoteEntity;

public class TextPostNoteOutputData {
    private final String text;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Color color;

    public TextPostNoteOutputData(TextPostNoteEntity entity) {
        this.text = entity.getText();
        this.x = entity.getX();
        this.y = entity.getY();
        this.width = entity.getWidth();
        this.height = entity.getHeight();
        this.color = entity.getColor();
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
