package use_case.change_color;

import entity.TextPostNote;

public interface ChangeColorNoteDataAccessInterface {
    /**
     * Updates the system to record this user's password.
     * @param textPostNote the textPostNote whose color to change
     */
    void changeColor(TextPostNote textPostNote);
}
