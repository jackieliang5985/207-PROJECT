package interface_adapter.change_color;

import interface_adapter.add_Text_PostNote.TextPostNoteViewModel;
import use_case.change_color.ChangeColorOutputBoundary;
import use_case.change_color.ChangeColorOutputData;
import use_case.change_password.ChangePasswordOutputData;

public class ChangeColorPresenter implements ChangeColorOutputBoundary {
    private final TextPostNoteViewModel textPostNoteViewModel;

    public ChangeColorPresenter(TextPostNoteViewModel textPostNoteViewModel) {
        this.textPostNoteViewModel = textPostNoteViewModel;
    }

    @Override
    public void prepareSuccessView(ChangePasswordOutputData outputData) {
        // currently there isn't anything to change based on the output data,
        // since the output data only contains the username, which remains the same.
        // We still fire the property changed event, but just to let the view know that
        // it can alert the user that their password was changed successfully..
        textPostNoteViewModel.firePropertyChanged("password");

    }

    @Override
    public void prepareFailView(String error) {
        // note: this use case currently can't fail
    }
}
