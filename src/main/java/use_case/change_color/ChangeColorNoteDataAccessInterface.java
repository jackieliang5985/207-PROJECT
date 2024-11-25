package use_case.change_color;

import entity.TextPostNoteEntity;

public interface ChangeColorNoteDataAccessInterface {
    /**
     * Updates the system to record this user's password.
     * @param textPostNote the textPostNote whose color to change
     */
    void changeColor(TextPostNoteEntity textPostNote);
}
