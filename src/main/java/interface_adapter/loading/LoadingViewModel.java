package interface_adapter.loading;

import interface_adapter.ViewModel;

/**
 * The View Model for the Login View.
 */
public class LoadingViewModel extends ViewModel<LoadingState> {

    public LoadingViewModel() {
        super("loading");
        setState(new LoadingState());
    }

}
