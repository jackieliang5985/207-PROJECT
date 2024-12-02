package interface_adapter.add_Text_PostNote;

import java.awt.Color;

import use_case.add_Text_PostNote.TextPostNoteInputBoundary;
import use_case.add_Text_PostNote.TextPostNoteInputData;

/**
 * The controller for adding a text post-it note.
 * It serves as an intermediary between the user interface and the interactor.
 */
public class TextPostNoteController {
    private final TextPostNoteInputBoundary inputBoundary;
    private final TextPostNoteViewModel viewModel;

    /**
     * Constructs a TextPostNoteController.
     *
     * @param inputBoundary The input boundary for the use case, which processes the input data.
     * @param viewModel The view model that represents the state of the UI.
     */
    public TextPostNoteController(TextPostNoteInputBoundary inputBoundary, TextPostNoteViewModel viewModel) {
        this.inputBoundary = inputBoundary;
        this.viewModel = viewModel;
    }

    /**
     * Handles the addition of a text post-it note by passing the data to the interactor.
     *
     * @param text The text content of the post-it note.
     * @param xValue The x-coordinate for the position of the post-it note.
     * @param yValue The y-coordinate for the position of the post-it note.
     * @param width The width of the post-it note.
     * @param height The height of the post-it note.
     * @param color The background color of the post-it note.
     */
    public void addTextPostNote(String text, int xValue, int yValue, int width, int height, Color color) {
        // Create the input data object for adding a new TextPostNote
        final TextPostNoteInputData inputData = new TextPostNoteInputData(text, xValue, yValue, width, height, color);

        // Pass the input data to the interactor to process it
        inputBoundary.addTextPostNote(inputData);

        // After adding, the interactor will update the ViewModel through the presenter
    }
}
