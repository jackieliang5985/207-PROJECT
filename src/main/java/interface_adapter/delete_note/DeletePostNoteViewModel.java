package interface_adapter.delete_note;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DeletePostNoteViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private DeletePostNoteController controller;
    private boolean isDeletedSuccessfully;
    private String errorMessage;

    public DeletePostNoteViewModel(DeletePostNoteController controller) {
        this.controller = controller;
    }

    // Getter for the delete success state
    public boolean isDeletedSuccessfully() {
        return isDeletedSuccessfully;
    }

    // Setter for isDeletedSuccessfully with property change support
    public void setDeletedSuccessfully(boolean deletedSuccessfully) {
        boolean oldState = this.isDeletedSuccessfully;
        this.isDeletedSuccessfully = deletedSuccessfully;
        support.firePropertyChange("isDeletedSuccessfully", oldState, isDeletedSuccessfully);
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

    // Update state when the deletion succeeds
    public void onPostNoteDeleted() {
        setDeletedSuccessfully(true);
        setErrorMessage(null); // Clear any previous error message
    }

    // Update state when the deletion fails
    public void onDeleteFailed(String errorMessage) {
        setDeletedSuccessfully(false);
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
