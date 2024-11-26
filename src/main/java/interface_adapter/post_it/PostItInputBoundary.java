package interface_adapter.post_it;

import use_case.create_post_it.CreatePostItInputData;
import use_case.edit_post_it.EditPostItInputData;
import use_case.move_post_it.MovePostItInputData;

public interface PostItInputBoundary {
    void executeCreatePostIt(CreatePostItInputData inputData);

    void execute(CreatePostItInputData inputData);

    void executeEditPostIt(EditPostItInputData inputData);
    void executeMovePostIt(MovePostItInputData inputData);
}
