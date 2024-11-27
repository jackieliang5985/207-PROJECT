package use_case.add_Image_PostNote;

import data_access.PostNoteDAO;
import entity.ImagePostNoteEntity;
import entity.PostNoteEntity;
import interface_adapter.add_Image_PostNote.ImagePostNoteData;
import entity.MindMapEntity;

public class ImagePostNoteInteractor implements ImagePostNoteInputBoundary {
    private final ImagePostNoteOutputBoundary outputBoundary;
    private final PostNoteDAO postNoteDAO;
    private final MindMapEntity mindMapEntity;

    // Constructor
    public ImagePostNoteInteractor(ImagePostNoteOutputBoundary outputBoundary, PostNoteDAO postNoteDAO, MindMapEntity mindMapEntity) {
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
                mindMapEntity,         // Attach MindMapEntity
                inputData.getImageUrl() // Add the image URL (specific to ImagePostNoteEntity)
        );

        // Add the ImagePostNoteEntity to the DAO (data persistence)
        postNoteDAO.addPostNote(imagePostNoteEntity);

        // Pass data to the presenter (output boundary)
        outputBoundary.presentImagePostNotes(new ImagePostNoteData(inputData.getImageUrl(), inputData.getX(), inputData.getY(), inputData.getColor()));
    }
}
