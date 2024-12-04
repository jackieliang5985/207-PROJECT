package interface_adapter.delete_note;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The View Model for the DeleteNote View.
 */
public class DeletePostNoteViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private DeletePostNoteController controller;
    private boolean isDeletedSuccessfully;
    private String errorMessage;

    public DeletePostNoteViewModel(DeletePostNoteController controller) {
        this.controller = controller;
    }

    public boolean isDeletedSuccessfully() {
        return isDeletedSuccessfully;
    }

    /**
     * Sets the status of whether a note was successfully deleted.
     *
     * @param deletedSuccessfully true if the note was deleted successfully, false otherwise
     */
    public void setDeletedSuccessfully(boolean deletedSuccessfully) {
        final boolean oldState = this.isDeletedSuccessfully;
        this.isDeletedSuccessfully = deletedSuccessfully;
        support.firePropertyChange("isDeletedSuccessfully", oldState, isDeletedSuccessfully);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message associated with a failed operation.
     *
     * @param errorMessage the error message to set, or null if there is no error
     */
    public void setErrorMessage(String errorMessage) {
        final String oldErrorMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldErrorMessage, errorMessage);
    }

    /**
     * Handles the event where a note has been successfully deleted.
     */
    public void onPostNoteDeleted() {
        setDeletedSuccessfully(true);
        setErrorMessage(null);
    }

    /**
     * Handles the event where deleting a note has failed.
     *
     * @param error the error message explaining why the deletion failed
     */
    public void onDeleteFailed(String error) {
        setDeletedSuccessfully(false);
        setErrorMessage(error);
    }

    /**
     * Adds a {@link PropertyChangeListener} to the listener list.
     *
     * @param listener the PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a {@link PropertyChangeListener} from the listener list.
     *
     * @param listener the PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
