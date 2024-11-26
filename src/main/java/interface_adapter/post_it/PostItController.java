package interface_adapter.post_it;

import use_case.create_post_it.CreatePostIt;
import use_case.edit_post_it.EditPostIt;
import use_case.move_post_it.MovePostIt;
import use_case.create_post_it.CreatePostItInputData;
import use_case.edit_post_it.EditPostItInputData;
import use_case.move_post_it.MovePostItInputData;

public class PostItController {

    private final CreatePostIt createPostIt;
    private final EditPostIt editPostIt;
    private final MovePostIt movePostIt;

    public PostItController(CreatePostIt createPostIt, EditPostIt editPostIt, MovePostIt movePostIt) {
        this.createPostIt = createPostIt;
        this.editPostIt = editPostIt;
        this.movePostIt = movePostIt;
    }

    public void createPostIt(CreatePostItInputData inputData) {
        createPostIt.execute(inputData);  // Correct method
    }

    public void editPostIt(EditPostItInputData inputData) {
        editPostIt.executeEditPostIt(inputData);  // Correct method
    }

    public void movePostIt(MovePostItInputData inputData) {
        movePostIt.executeMovePostIt(inputData);  // Correct method
    }
}
