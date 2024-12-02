package use_case.add_Text_PostNote;

import java.awt.Color;

import entity.TextPostNoteEntity;

/**
 * A data transfer object that encapsulates the output data for adding a text post-it note.
 * This class is used to pass the results of the use case from the interactor to the presenter.
 */
public class TextPostNoteOutputData {
    private final String text;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Color color;

    /**
     * Constructs a new TextPostNoteOutputData object from the given TextPostNoteEntity.
     *
     * @param entity The TextPostNoteEntity containing the details of the added text post-it note.
     */
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
