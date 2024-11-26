package use_case.create_post_it;

import entity.PostNoteEntity;
import entity.MindMapEntity;
import interface_adapter.post_it.PostItInputBoundary;
import interface_adapter.post_it.PostItOutputBoundary;
import use_case.edit_post_it.EditPostItInputData;
import use_case.move_post_it.MovePostItInputData;

public class CreatePostIt implements PostItInputBoundary {

    private final PostItOutputBoundary outputBoundary;
    private final MindMapEntity mindMap;  // Changed from squarePanel to mindMap

    public CreatePostIt(PostItOutputBoundary outputBoundary, MindMapEntity mindMap) {
        this.outputBoundary = outputBoundary;
        this.mindMap = mindMap;  // Using MindMapEntity
    }

    @Override
    public void executeCreatePostIt(CreatePostItInputData inputData) {

    }

    @Override
    public void execute(CreatePostItInputData inputData) {
        // Create a new post-it note
        PostNoteEntity postNote = new PostNoteEntity(inputData.getX(), inputData.getY(),
                inputData.getWidth(), inputData.getHeight(), mindMap);

        // Notify the output boundary to update the view
        outputBoundary.prepareSuccessView(postNote);

        // Add the new post-it note to the mind map (not directly to the panel)
        mindMap.addPostNote(postNote);  // Add the post-it note to the mind map
    }

    @Override
    public void executeEditPostIt(EditPostItInputData inputData) {
        // Not needed in this use case
    }

    @Override
    public void executeMovePostIt(MovePostItInputData inputData) {
        // Not needed in this use case
    }
}
