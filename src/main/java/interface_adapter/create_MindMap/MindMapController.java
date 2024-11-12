package interface_adapter.create_MindMap;

import use_case.create_MindMap.MindMapInputBoundary;
import use_case.create_MindMap.MindMapInputData;

/**
 * Controller for the Signup Use Case.
 */
public class MindMapController {

    private final MindMapInputBoundary userSignupUseCaseInteractor;

    public MindMapController(MindMapInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    /**
     * Executes the Mindmap Creation/loading Use Case.
     * @param name the MindMap
     * @param description the description
     */
    public void execute(String name, String description) {
        final MindMapInputData mindMapInputData = new MindMapInputData(
                name, description);

        userSignupUseCaseInteractor.execute(mindMapInputData);
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToLoginView() {
        userSignupUseCaseInteractor.switchToLoginView();
    }
}
