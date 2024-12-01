package use_case.change_color;

import java.awt.Color;

/**
 * The input data for the Change Color Use Case.
 */
public class ChangeColorInputData {
    private int x;
    private int y;
    private int width;
    private int height;
    private final Color color;

    public ChangeColorInputData(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
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
