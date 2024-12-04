package interface_adapter.change_color;

import java.awt.Color;

import use_case.change_color.ChangeColorInputBoundary;
import use_case.change_color.ChangeColorInputData;

/**
 * The controller for the Change Color Use Case.
 */
public class ChangeColorController {
    private final ChangeColorInputBoundary changeColorUseCaseInteractor;

    public ChangeColorController(ChangeColorInputBoundary userChangeColorUseCaseInteractor) {
        this.changeColorUseCaseInteractor = userChangeColorUseCaseInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     * @param positionX the x coordinate of the textPostNote whose color to change
     * @param positionY the y coordinate of the textPostNote whose color to change
     * @param width the width of the textPostNote whose color to change
     * @param height the height of the textPostNote whose color to change
     * @param color the color to change the selected textPostNote to
     */
    public void execute(int positionX, int positionY, int width, int height, Color color) {
        final ChangeColorInputData changeColorInputData =
                new ChangeColorInputData(positionX, positionY, width, height, color);

        changeColorUseCaseInteractor.execute(changeColorInputData);
    }
}
