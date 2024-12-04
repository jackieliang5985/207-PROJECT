package interface_adapter.change_title;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class LoadedInViewModel extends ViewModel<LoadedInState> {

    public LoadedInViewModel() {
        super("logged in");
        setState(new LoadedInState());
    }

    public void setName(String name) {
        LoadedInState currentState = getState();
        currentState.setName(name);
        firePropertyChanged("name");  // Fire property change to notify the view
    }
}
