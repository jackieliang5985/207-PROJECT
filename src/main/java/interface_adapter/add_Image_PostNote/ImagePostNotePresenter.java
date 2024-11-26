package interface_adapter.add_Image_PostNote;

import use_case.add_Image_PostNote.ImagePostNoteOutputBoundary;

import javax.swing.*;

public class ImagePostNotePresenter implements ImagePostNoteOutputBoundary {

    private final ImagePostNoteViewModel viewModel;

    public ImagePostNotePresenter(ImagePostNoteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentImagePostNotes(ImagePostNoteData data) {
        // Convert the data into a format the view model can use
        viewModel.setImageUrl(data.getImageUrl());
        viewModel.setX(data.getX());
        viewModel.setY(data.getY());
        viewModel.setColor(data.getColor());

        // You can now pass this updated viewModel to the view for display
    }

    public void presentError(String errorMessage) {
        // Handle error if needed
    }
}
