package use_case.change_color;

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

    }
}
