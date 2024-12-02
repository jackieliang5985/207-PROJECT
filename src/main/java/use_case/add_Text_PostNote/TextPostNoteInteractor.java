package use_case.add_Text_PostNote;

import data_access.PostNoteDAO;
import entity.MindMapEntity;
import entity.TextPostNoteEntity;

/**
 * The interactor for adding a text post-it note.
 * Implements the business logic for the `addTextPostNote` use case.
 * It processes the input data, creates a `TextPostNoteEntity`, saves it via the DAO,
 * and passes the output to the presenter through the output boundary.
 */
public class TextPostNoteInteractor implements TextPostNoteInputBoundary {
    private final TextPostNoteOutputBoundary outputBoundary;
    private final PostNoteDAO postNoteDao;
    private final MindMapEntity mindMapEntity;

    /**
     * Constructs a TextPostNoteInteractor.
     *
     * @param outputBoundary The output boundary for presenting the results of the use case.
     * @param postNoteDao    The Data Access Object (DAO) for saving post-it notes.
     * @param mindMapEntity  The MindMapEntity that the text post-it note is associated with.
     */
    public TextPostNoteInteractor(TextPostNoteOutputBoundary outputBoundary, PostNoteDAO postNoteDao,
                                  MindMapEntity mindMapEntity) {
        this.outputBoundary = outputBoundary;
        this.postNoteDao = postNoteDao;
        this.mindMapEntity = mindMapEntity;
    }

    @Override
    public void addTextPostNote(TextPostNoteInputData inputData) {
        // Create a TextPostNoteEntity
        final TextPostNoteEntity postNoteEntity = new TextPostNoteEntity(
                inputData.getX(), inputData.getY(), inputData.getWidth(), inputData.getHeight(), inputData.getColor(),
                mindMapEntity
        );

        // Set the text if needed
        postNoteEntity.setText(inputData.getText());

        // Save to the DAO
        postNoteDao.addPostNote(postNoteEntity);

        // Pass the data to the output boundary (presenter)
        outputBoundary.presentTextPostNotes(new TextPostNoteOutputData(postNoteEntity));
    }
}
