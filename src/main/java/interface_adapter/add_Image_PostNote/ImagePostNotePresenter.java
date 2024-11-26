package interface_adapter.add_Image_PostNote;

import use_case.add_Image_PostNote.ImagePostNoteOutputBoundary;

import java.util.List;

public class ImagePostNotePresenter implements ImagePostNoteOutputBoundary {
    private final ImagePostNoteViewModel viewModel;

    public ImagePostNotePresenter(ImagePostNoteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Present a list of image post notes to the view model.
     *
     * @param postNotes the list of ImagePostNoteData to present to the view.
     */
    public void presentImagePostNotes(List<ImagePostNoteData> postNotes) {
        // Iterate through the list of ImagePostNoteData and update the view model with each one
        for (ImagePostNoteData postNoteData : postNotes) {
            viewModel.setImageUrl(postNoteData.getImageUrl());
            viewModel.setX(postNoteData.getX());
            viewModel.setY(postNoteData.getY());
            viewModel.setColor(postNoteData.getColor());
        }
    }

    /**
     * Present a single image post note to the view model.
     *
     * @param data the single ImagePostNoteData to present.
     */
    public void presentImagePostNotes(ImagePostNoteData data) {
        // Update the view model with the provided single ImagePostNoteData
        viewModel.setImageUrl(data.getImageUrl());
        viewModel.setX(data.getX());
        viewModel.setY(data.getY());
        viewModel.setColor(data.getColor());
    }

    /**
     * Handle error messages by setting the error in the view model (if necessary).
     *
     * @param errorMessage the error message to present.
     */
    @Override
    public void presentError(String errorMessage) {
        // For simplicity, assume we set the error message on the view model.
        // You can use this method to display the error in the UI.
        System.out.println("Error: " + errorMessage);
    }
}
