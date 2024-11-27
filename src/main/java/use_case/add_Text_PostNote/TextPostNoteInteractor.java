package use_case.add_Text_PostNote;

import data_access.PostNoteDAO;
import entity.MindMapEntity;
import entity.TextPostNoteEntity;

public class TextPostNoteInteractor implements TextPostNoteInputBoundary {
    private final TextPostNoteOutputBoundary outputBoundary;
    private final PostNoteDAO postNoteDAO;
    private final MindMapEntity mindMapEntity;

    // Constructor
    public TextPostNoteInteractor(TextPostNoteOutputBoundary outputBoundary, PostNoteDAO postNoteDAO, MindMapEntity mindMapEntity) {
        this.outputBoundary = outputBoundary;
        this.postNoteDAO = postNoteDAO;
        this.mindMapEntity = mindMapEntity;
    }

    @Override
    public void addTextPostNote(TextPostNoteInputData inputData) {
        // Create a TextPostNoteEntity
        TextPostNoteEntity postNoteEntity = new TextPostNoteEntity(
                inputData.getX(), inputData.getY(), inputData.getWidth(), inputData.getHeight(), inputData.getColor(), mindMapEntity
        );

        // Set the text if needed
        postNoteEntity.setText(inputData.getText());

        // Save to the DAO
        postNoteDAO.addPostNote(postNoteEntity);

        // Pass the data to the output boundary (presenter)
        outputBoundary.presentTextPostNotes(new TextPostNoteOutputData(postNoteEntity));
    }
}
