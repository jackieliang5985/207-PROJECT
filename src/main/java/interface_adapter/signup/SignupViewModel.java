package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public static final String TITLE_LABEL = "Create New MindMap View";
    public static final String NAME_LABEL = "Choose name";
    public static final String DESCRIPTION_LABEL = "Description";

    public static final String CREATE_BUTTON_LABEL = "Create New MindMap";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String TO_LOAD_BUTTON_LABEL = "Load Existing MindMap";

    public SignupViewModel() {
        super("sign up");
        setState(new SignupState("name is wrong"));
    }

}
