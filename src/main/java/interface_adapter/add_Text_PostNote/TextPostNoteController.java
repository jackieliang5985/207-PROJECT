package interface_adapter.add_Text_PostNote;

import use_case.add_Text_PostNote.TextPostNoteInputBoundary;
import use_case.add_Text_PostNote.TextPostNoteInputData;

import java.awt.*;

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
    public void addTextPostNote(String text, int x, int y, int width, int height, Color color) {
        // Create the input data object for adding a new TextPostNote
        TextPostNoteInputData inputData = new TextPostNoteInputData(text, x, y, width, height, color);

        // Pass the input data to the interactor to process it
        inputBoundary.addTextPostNote(inputData);

        // After adding, the interactor will update the ViewModel through the presenter
    }
}
