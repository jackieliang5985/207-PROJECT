package interface_adapter.add_Image_PostNote;

import use_case.add_Image_PostNote.ImagePostNoteInputBoundary;
import use_case.add_Image_PostNote.ImagePostNoteInputData;

import java.awt.*;

public class ImagePostNoteController {
    private final ImagePostNoteInputBoundary inputBoundary;
    private final ImagePostNoteViewModel viewModel;

    // Constructor
    public ImagePostNoteController(ImagePostNoteInputBoundary inputBoundary, ImagePostNoteViewModel viewModel) {
        this.inputBoundary = inputBoundary;
        this.viewModel = viewModel;
    }

    /**
     * Handles adding a new image post-it note.
     */
    public void addImagePostNote(String imageUrl, int x, int y, int width, int height, Color color) {
        // Update the view model with the image data
        System.out.println("size of post note is, " + width + "by" + height);

        viewModel.setImageUrl(imageUrl);
        viewModel.setX(x);
        viewModel.setY(y);
        viewModel.setWidth(width);
        viewModel.setHeight(height);
        viewModel.setColor(color);

        // Pass data to the interactor
        inputBoundary.addImagePostNote(new ImagePostNoteInputData(imageUrl, x, y, width, height, color));
    }
}
