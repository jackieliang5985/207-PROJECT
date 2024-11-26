package use_case.edit_post_it;

import entity.TextPostNoteEntity;
import entity.PostNoteEntity;
import interface_adapter.post_it.PostItInputBoundary;
import interface_adapter.post_it.PostItOutputBoundary;
import entity.MindMapEntity;
import use_case.create_post_it.CreatePostItInputData;
import use_case.move_post_it.MovePostItInputData;

public class EditPostIt implements PostItInputBoundary {

    private final PostItOutputBoundary outputBoundary;
    private final MindMapEntity mindMap;  // Changed from squarePanel to mindMap

    public EditPostIt(PostItOutputBoundary outputBoundary, MindMapEntity mindMap) {
        this.outputBoundary = outputBoundary;
        this.mindMap = mindMap;  // Using MindMapEntity
    }

    @Override
    public void executeCreatePostIt(CreatePostItInputData inputData) {

    }

    @Override
    public void execute(CreatePostItInputData inputData) {
        // Not needed in this use case
    }

    @Override
    public void executeEditPostIt(EditPostItInputData inputData) {
        // Find the post-it note by ID using MindMapEntity's getPostNoteById
        PostNoteEntity postNote = mindMap.getPostNoteById(inputData.getPostNoteId());
        if (postNote != null) {
            // Assuming setText is a method of TextPostNoteEntity
            ((TextPostNoteEntity) postNote).setText(inputData.getNewText());
            outputBoundary.prepareSuccessView(postNote);
        }
    }

    @Override
    public void executeMovePostIt(MovePostItInputData inputData) {
        // Not needed in this use case
    }
}
