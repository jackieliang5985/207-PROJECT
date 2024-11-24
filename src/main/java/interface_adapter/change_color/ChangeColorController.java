package interface_adapter.change_color;

import java.awt.Color;

import use_case.change_color.ChangeColorInputBoundary;
import use_case.change_color.ChangeColorInputData;

public class ChangeColorController {
    private final ChangeColorInputBoundary userChangePasswordUseCaseInteractor;

    public ChangeColorController(ChangeColorInputBoundary userChangePasswordUseCaseInteractor) {
        this.userChangePasswordUseCaseInteractor = userChangePasswordUseCaseInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     * @param color the color to change the selected note to
     */
    public void execute(Color color) {
        final ChangeColorInputData changePasswordInputData = new ChangeColorInputData(color);

        userChangePasswordUseCaseInteractor.execute(changePasswordInputData);
    }
}
