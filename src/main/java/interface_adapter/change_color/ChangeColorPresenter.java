package interface_adapter.change_color;

import interface_adapter.add_Text_PostNote.TextPostNoteViewModel;
import use_case.add_Text_PostNote.TextPostNoteOutputData;
import use_case.change_color.ChangeColorOutputBoundary;
import use_case.change_color.ChangeColorOutputData;

public class ChangeColorPresenter implements ChangeColorOutputBoundary {
    private final TextPostNoteViewModel textPostNoteViewModel;

    public ChangeColorPresenter(TextPostNoteViewModel textPostNoteViewModel) {
        this.textPostNoteViewModel = textPostNoteViewModel;
    }

    @Override
    public void prepareSuccessView(ChangeColorOutputData changeColorOutputData) {
        // Update the view model with the new data passed in outputData
//        textPostNoteViewModel.setText(changeColorOutputData.getText());
        textPostNoteViewModel.setX(changeColorOutputData.getX());
        textPostNoteViewModel.setY(changeColorOutputData.getY());
        textPostNoteViewModel.setWidth(changeColorOutputData.getWidth());
        textPostNoteViewModel.setHeight(changeColorOutputData.getHeight());
        textPostNoteViewModel.setColor(changeColorOutputData.getColor());
    }

    @Override
    public void prepareFailView(String error) {
        System.out.println(error);
    }
}
