package use_case.change_color;

import entity.PostNote;

public interface ChangeColorUserDataAccessInterface {
    /**
     * Updates the system to record this user's password.
     * @param postNote the postNote whose color is to be changed
     */
    void changeColor(PostNote postNote);
}
