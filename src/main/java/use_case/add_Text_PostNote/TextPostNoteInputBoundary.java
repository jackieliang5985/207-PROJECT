package use_case.add_Text_PostNote;

/**
 * Input boundary for the use case of adding a text post-it note.
 * Defines the contract for the interactor to process the input data.
 */
public interface TextPostNoteInputBoundary {

    /**
     * Adds a new text post-it note based on the provided input data.
     *
     * @param inputData The input data containing the details of the text post-it note,
     *                  such as text content, position, dimensions, and color.
     */
    void addTextPostNote(TextPostNoteInputData inputData);
}
