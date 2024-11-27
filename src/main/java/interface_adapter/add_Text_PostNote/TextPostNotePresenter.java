package interface_adapter.add_Text_PostNote;

import entity.TextPostNoteEntity;
import use_case.add_Text_PostNote.TextPostNoteOutputBoundary;
import use_case.add_Text_PostNote.TextPostNoteOutputData;

public class TextPostNotePresenter implements TextPostNoteOutputBoundary {
    private final TextPostNoteViewModel viewModel;

    // Constructor
    public TextPostNotePresenter(TextPostNoteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    // Method to update view model using TextPostNoteEntity (if needed for legacy reasons)
    public void presentTextPostNotes(TextPostNoteEntity postNote) {
        // Update the view model with the new post note data
        viewModel.setText(postNote.getText());
        viewModel.setX(postNote.getX());
        viewModel.setY(postNote.getY());
        viewModel.setColor(postNote.getColor());
    }

    // Method to update view model using TextPostNoteOutputData (the correct data format)
    @Override
    public void presentTextPostNotes(TextPostNoteOutputData outputData) {
        // Update the view model with the new data passed in outputData
        viewModel.setText(outputData.getText());
        viewModel.setX(outputData.getX());
        viewModel.setY(outputData.getY());
        viewModel.setWidth(outputData.getWidth());
        viewModel.setHeight(outputData.getHeight());
        viewModel.setColor(outputData.getColor());
    }
}
