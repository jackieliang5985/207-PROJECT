package use_case.add_Text_PostNote;

/**
 * Output boundary for the use case of adding a text post-it note.
 * Defines the contract for presenting the results of the use case to the UI layer.
 */
public interface TextPostNoteOutputBoundary {
    /**
     * Presents the results of adding a text post-it note.
     * This method updates the view model or passes data to the presenter for UI display.
     *
     * @param outputData The output data containing the details of the added text post-it note,
     *                   such as text content, position, dimensions, and color.
     */
    void presentTextPostNotes(TextPostNoteOutputData outputData);
}
