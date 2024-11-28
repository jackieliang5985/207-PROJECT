package interface_adapter.change_title;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class LoggedInViewModel extends ViewModel<LoggedInState> {

    public LoggedInViewModel() {
        super("logged in");
        setState(new LoggedInState());
    }

    public void setName(String name) {
        LoggedInState currentState = getState();
        currentState.setName(name);
        firePropertyChanged("name");  // This triggers the PropertyChangeListener in the view
    }
}
