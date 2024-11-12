package interface_adapter.create_MindMap;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class MindMapViewModel extends ViewModel<MindMapState> {

    public static final String TITLE_LABEL = "Create New MindMap View";
    public static final String NAME_LABEL = "Choose name";
    public static final String DESCRIPTION_LABEL = "Description";

    public static final String CREATE_BUTTON_LABEL = "Create New MindMap";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String TO_LOAD_BUTTON_LABEL = "Load Existing MindMap";

    public MindMapViewModel() {
        super("sign up");
        setState(new MindMapState("name is wrong"));
    }

}
