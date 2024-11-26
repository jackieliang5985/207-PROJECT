package use_case.add_Image_PostNote;

import entity.ImagePostNote;

public class ImagePostNoteInteractor implements ImagePostNoteInputBoundary {

    private final ImagePostNoteOutputBoundary outputBoundary;

    public ImagePostNoteInteractor(ImagePostNoteOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void addImagePostNote(ImagePostNote postNote) {
        // Perform business logic, if needed (e.g., validation)
        // Notify the presenter through the OutputBoundary
        outputBoundary.presentImagePostNote(postNote);
    }
}
