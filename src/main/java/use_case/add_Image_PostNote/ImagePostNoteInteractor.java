package use_case.add_Image_PostNote;

import data_access.PostNoteDataAccessInterface;
import entity.ImagePostNoteEntity;
import interface_adapter.add_Image_PostNote.ImagePostNoteData;
import entity.MindMapEntity;

public class ImagePostNoteInteractor implements ImagePostNoteInputBoundary {
    private final ImagePostNoteOutputBoundary outputBoundary;
    private final PostNoteDataAccessInterface postNoteDataAccessInterface;
    private final MindMapEntity mindMapEntity;

    // Constructor
    public ImagePostNoteInteractor(ImagePostNoteOutputBoundary outputBoundary,
                                   PostNoteDataAccessInterface postNoteDataAccessInterface, MindMapEntity mindMapEntity) {
        this.outputBoundary = outputBoundary;
        this.postNoteDataAccessInterface = postNoteDataAccessInterface;
        this.mindMapEntity = mindMapEntity;
    }

    @Override
    public void addImagePostNote(ImagePostNoteInputData inputData) {
        final ImagePostNoteEntity imagePostNoteEntity = new ImagePostNoteEntity(
                inputData.getX(),
                inputData.getY(),
                inputData.getWidth(),
                inputData.getHeight(),
                mindMapEntity,
                inputData.getImageUrl()
        );
        postNoteDataAccessInterface.addPostNote(imagePostNoteEntity);
        outputBoundary.presentImagePostNotes(new ImagePostNoteData(inputData.getImageUrl(),
                inputData.getX(), inputData.getY(), inputData.getColor()));
    }
}
