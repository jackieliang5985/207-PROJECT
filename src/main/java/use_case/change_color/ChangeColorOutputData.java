package use_case.change_color;

import java.awt.Color;

/**
 * Output data for the Change Color Use Case.
 */
public class ChangeColorOutputData {
    // private final String text;
    private final int positionX;
    private final int positionY;
    private final int width;
    private final int height;
    private final Color color;

    public ChangeColorOutputData(int positionX, int positionY, int width, int height, Color color) {
        // this.text = text;
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    // public String getText() {
    //     return text;
    // }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
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
