package interface_adapter.add_Text_PostNote;

import use_case.add_Text_PostNote.TextPostNoteOutputBoundary;
import use_case.add_Text_PostNote.TextPostNoteOutputData;

/**
 * The presenter for adding a text post-it note.
 * It updates the view model based on the output data provided by the interactor.
 */
public class TextPostNotePresenter implements TextPostNoteOutputBoundary {
    private final TextPostNoteViewModel viewModel;

    /**
     * Constructs a TextPostNotePresenter.
     *
     * @param viewModel The view model that represents the UI state.
     */
    public TextPostNotePresenter(TextPostNoteViewModel viewModel) {
        this.viewModel = viewModel;
    }

     /**
     * Updates the view model using TextPostNoteOutputData.
     * This is the preferred method for updating the view model.
     *
     * @param outputData The output data containing the processed details of the text post-it note.
     */    @Override
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
