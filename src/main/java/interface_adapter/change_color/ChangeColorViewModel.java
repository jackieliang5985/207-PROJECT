package interface_adapter.change_color;

import interface_adapter.delete_note.DeletePostNoteController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChangeColorViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private ChangeColorController controller;
    private boolean colorChangedSuccessfully;
    private String errorMessage;

    public ChangeColorViewModel() {
    }

    // Getter for the delete success state
    public boolean colorChangedSuccessfully() {
        return colorChangedSuccessfully;
    }

    // Setter for isDeletedSuccessfully with property change support
    public void setColorChangedSuccessfully(boolean deletedSuccessfully) {
        boolean oldState = this.colorChangedSuccessfully;
        this.colorChangedSuccessfully = colorChangedSuccessfully;
        support.firePropertyChange("colorChangedSuccessfully", oldState, colorChangedSuccessfully);
    }

    // Getter for error messages
    public String getErrorMessage() {
        return errorMessage;
    }

    // Setter for errorMessage with property change support
    public void setErrorMessage(String errorMessage) {
        String oldErrorMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldErrorMessage, errorMessage);
    }

    // Update state when the color change succeeds
    public void onColorChanged() {
        setColorChangedSuccessfully(true);
        setErrorMessage(null); // Clear any previous error message
    }

    // Update state when the color change fails
    public void onFail(String errorMessage) {
        setColorChangedSuccessfully(false);
        setErrorMessage(errorMessage);
    }

    // Method to add a property change listener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Method to remove a property change listener
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
