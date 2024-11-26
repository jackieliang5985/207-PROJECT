package use_case.move_post_it;

import entity.PostNoteEntity;
import interface_adapter.post_it.PostItInputBoundary;
import interface_adapter.post_it.PostItOutputBoundary;
import entity.MindMapEntity;
import use_case.create_post_it.CreatePostItInputData;
import use_case.edit_post_it.EditPostItInputData;

public class MovePostIt implements PostItInputBoundary {

    private final PostItOutputBoundary outputBoundary;
    private final MindMapEntity mindMap;  // Changed from squarePanel to mindMap

    public MovePostIt(PostItOutputBoundary outputBoundary, MindMapEntity mindMap) {
        this.outputBoundary = outputBoundary;
        this.mindMap = mindMap;  // Using MindMapEntity
    }

    @Override
    public void executeCreatePostIt(CreatePostItInputData inputData) {
        // Not needed in this use case
    }

    @Override
    public void execute(CreatePostItInputData inputData) {

    }

    @Override
    public void executeEditPostIt(EditPostItInputData inputData) {
        // Not needed in this use case
    }

    @Override
    public void executeMovePostIt(MovePostItInputData inputData) {
        // Find the post-it note by ID using MindMapEntity's getPostNoteById
        PostNoteEntity postNote = mindMap.getPostNoteById(inputData.getPostNoteId());
        if (postNote != null) {
            // Update the position of the post-it note
            postNote.setX(inputData.getNewX());
            postNote.setY(inputData.getNewY());

            // Notify the output boundary to update the view
            outputBoundary.prepareSuccessView(postNote);

            // Repaint the panel to reflect the new position
            // In case you need a visual update, use a method to trigger UI changes (likely in a view class).
        }
    }
}
