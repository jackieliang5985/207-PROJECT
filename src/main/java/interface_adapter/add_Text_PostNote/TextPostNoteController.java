package interface_adapter.add_Text_PostNote;

import java.awt.Color;

import use_case.add_Text_PostNote.TextPostNoteInputBoundary;
import use_case.add_Text_PostNote.TextPostNoteInputData;

public class TextPostNoteController {
    private final TextPostNoteInputBoundary inputBoundary;
    private final TextPostNoteViewModel viewModel;

    // Constructor
    public TextPostNoteController(TextPostNoteInputBoundary inputBoundary, TextPostNoteViewModel viewModel) {
        this.inputBoundary = inputBoundary;
        this.viewModel = viewModel;
    }

    /**
     * Handles the process of adding a text post-it note.
     */
    public void addTextPostNote(String text, int xValue, int yValue, int width, int height, Color color) {
        // Create the input data object for adding a new TextPostNote
        final TextPostNoteInputData inputData = new TextPostNoteInputData(text, xValue, yValue, width, height, color);

        // Pass the input data to the interactor to process it
        inputBoundary.addTextPostNote(inputData);

        // After adding, the interactor will update the ViewModel through the presenter
    }
}
