package use_case.change_color;

import entity.TextPostNoteEntity;

/**
 * The Change Color Interactor.
 */
public class ChangeColorInteractor implements ChangeColorInputBoundary {
    private final ChangeColorNoteDataAccessInterface noteDataAccessObject;
    private final ChangeColorOutputBoundary notePresenter;

    public ChangeColorInteractor(ChangeColorNoteDataAccessInterface changeColorNoteDataAccessInterface,
                                 ChangeColorOutputBoundary changeColorOutputBoundary) {
        this.noteDataAccessObject = changeColorNoteDataAccessInterface;
        this.notePresenter = changeColorOutputBoundary;
    }

    @Override
    public void execute(ChangeColorInputData changeColorInputData) {
        if (changeColorInputData.getColor() == null) {
            notePresenter.prepareFailView("Color was not changed.");
        }
        else {
            // Try to find the post note stored in the Data Access Object with the same position and dimensions as
            // specified by the input data, and if found, update its color to the color specified by the input data.
            final TextPostNoteEntity targetNote =
                    noteDataAccessObject.changeColorOfPostNote(changeColorInputData.getX(),
                    changeColorInputData.getY(), changeColorInputData.getWidth(), changeColorInputData.getHeight(),
                    changeColorInputData.getColor());
            if (targetNote != null) {
                System.out.println("Color was changed");
                // Output data consists of the position and dimensions of the modified note, as well as its new color
                final ChangeColorOutputData changeColorOutputData =
                        new ChangeColorOutputData(targetNote.getX(),
                                targetNote.getY(), targetNote.getWidth(),
                                targetNote.getHeight(), targetNote.getColor());
                notePresenter.prepareSuccessView(changeColorOutputData);
            }
            else {
                notePresenter.prepareFailView("Post-it note not found.");
            }
        }
    }
}
