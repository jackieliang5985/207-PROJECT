package use_case.change_color;

import entity.TextPostNoteEntity;

import java.awt.Color;

public interface ChangeColorNoteDataAccessInterface {
    /**
     * Updates the system to record this user's password.
     * @param x the x coordinate of the textPostNote whose color to change
     * @param y the y coordinate of the textPostNote whose color to change
     * @param width the width of the textPostNote whose color to change
     * @param height the height of the textPostNote whose color to change
     * @param color the color to change the textPostNote to
     */
    boolean changeColorOfPostNote(int x, int y, int width, int height, Color color);
}
