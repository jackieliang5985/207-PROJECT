package use_case.change_color;

import java.awt.Color;

/**
 * The input data for the Change Color Use Case.
 */
public class ChangeColorInputData {
    private int positionX;
    private int positionY;
    private int width;
    private int height;
    private final Color color;

    public ChangeColorInputData(int positionX, int positionY, int width, int height, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.color = color;
    }

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
