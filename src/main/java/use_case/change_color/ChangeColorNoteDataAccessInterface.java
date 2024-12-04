package use_case.change_color;

import java.awt.Color;

import entity.TextPostNoteEntity;

/**
 * DAO for the Change Color Use Case.
 */
public interface ChangeColorNoteDataAccessInterface {
    /**
     * Updates the system to record this user's password.
     * @param positionX the x coordinate of the textPostNote whose color to change
     * @param positionY the y coordinate of the textPostNote whose color to change
     * @param width the width of the textPostNote whose color to change
     * @param height the height of the textPostNote whose color to change
     * @param color the color to change the textPostNote to
     * @return the textPostNote which had its color changed; return null if there does not exist such textPostNote
     */
    TextPostNoteEntity changeColorOfPostNote(int positionX, int positionY, int width, int height, Color color);
}
