package use_case.add_Image_PostNote;

import data_access.PostNoteDataAccessInterface;
import entity.ImagePostNoteEntity;
import interface_adapter.add_Image_PostNote.ImagePostNoteData;
import entity.MindMapEntity;

public class ImagePostNoteInteractor implements ImagePostNoteInputBoundary {
    private final ImagePostNoteOutputBoundary outputBoundary;
    private final PostNoteDataAccessInterface postNoteDAO;
    private final MindMapEntity mindMapEntity;

    // Constructor
    public ImagePostNoteInteractor(ImagePostNoteOutputBoundary outputBoundary,
                                   PostNoteDataAccessInterface postNoteDAO, MindMapEntity mindMapEntity) {
        this.outputBoundary = outputBoundary;
        this.postNoteDAO = postNoteDAO;
        this.mindMapEntity = mindMapEntity;
    }

    @Override
    public void addImagePostNote(ImagePostNoteInputData inputData) {
        // Create an ImagePostNoteEntity instead of a PostNoteEntity
        ImagePostNoteEntity imagePostNoteEntity = new ImagePostNoteEntity(
                inputData.getX(),
                inputData.getY(),
                inputData.getWidth(),
                inputData.getHeight(),
                mindMapEntity,
                inputData.getImageUrl()
        );

        // Add the ImagePostNoteEntity to the DAO (data persistence)
        postNoteDAO.addPostNote(imagePostNoteEntity);

        // Pass data to the presenter (output boundary)
        outputBoundary.presentImagePostNotes(new ImagePostNoteData(inputData.getImageUrl(),
                inputData.getX(), inputData.getY(), inputData.getColor()));
    }
}
