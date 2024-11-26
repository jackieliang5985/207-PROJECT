package interface_adapter.add_Image_PostNote;

import entity.ImagePostNote;
import use_case.add_Image_PostNote.ImagePostNoteInputBoundary;

import javax.swing.ImageIcon;
import java.awt.Color;

public class ImagePostNoteController {

    private final ImagePostNoteInputBoundary inputBoundary;

    public ImagePostNoteController(ImagePostNoteInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void addImagePostNote(ImageIcon imageIcon) {
        // Create a new ImagePostNote
        ImagePostNote postNote = new ImagePostNote(50, 50, Color.ORANGE, null);
        postNote.setImage(imageIcon);

        // Pass the post note to the interactor via the InputBoundary
        inputBoundary.addImagePostNote(postNote);
    }
}
