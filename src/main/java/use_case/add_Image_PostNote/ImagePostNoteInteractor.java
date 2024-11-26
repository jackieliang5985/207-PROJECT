package use_case.add_Image_PostNote;

import interface_adapter.add_Image_PostNote.ImagePostNoteData;

public class ImagePostNoteInteractor implements ImagePostNoteInputBoundary {
    private final ImagePostNoteOutputBoundary outputBoundary;

    public ImagePostNoteInteractor(ImagePostNoteOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void addImagePostNote(ImagePostNoteInputData inputData) {
        // Perform business logic (e.g., validating, saving, etc.)

        // Prepare simplified data for the presenter
        ImagePostNoteData data = new ImagePostNoteData(inputData.getImageUrl(), inputData.getX(), inputData.getY(), inputData.getColor());

        // Pass the data to the presenter
        outputBoundary.presentImagePostNotes(data);
    }
}

