package interface_adapter.change_color;

import java.awt.Color;

import use_case.change_color.ChangeColorInputBoundary;
import use_case.change_color.ChangeColorInputData;

public class ChangeColorController {
    private final ChangeColorInputBoundary changeColorUseCaseInteractor;

    public ChangeColorController(ChangeColorInputBoundary userChangeColorUseCaseInteractor) {
        this.changeColorUseCaseInteractor = userChangeColorUseCaseInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     *
     * @param color the color to change the selected note to
     */
    public void execute(int x, int y, int width, int height, Color color) {
        final ChangeColorInputData changeColorInputData = new ChangeColorInputData(x, y, width, height, color);

        changeColorUseCaseInteractor.execute(changeColorInputData);
    }
}