package use_case.change_color;

import java.awt.Color;

/**
 * The input data for the Change Color Use Case.
 */
public class ChangeColorInputData {
    private final Color color;

    public ChangeColorInputData(Color color) {
        this.color = color;
    }

    Color getColor() {
        return color;
    }
}
