package interface_adapter.get_images;

import entity.CommonImage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel class that holds the state for the image data and provides methods to interact with it.
 * It acts as the intermediary between the presenter and the view, notifying the view of state changes.
 */
public class ImageViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // List to hold the current set of images
    private final List<CommonImage> images = new ArrayList<>();

    // Stores the error message if an error occurs during image fetching
    private String errorMessage;

    /**
     * Adds a property change listener to listen for state changes in the view model.
     * @param listener the listener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     * @param listener the listener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Retrieves the list of images.
     * @return the current list of images
     */
    public List<CommonImage> getImages() {
        return images;
    }

    /**
     * Sets the list of images and notifies listeners if the list has changed.
     * @param newImages the new list of images to set
     */
    public void setImages(List<CommonImage> newImages) {
        // Create a copy of the old images for property change comparison
        List<CommonImage> oldImages = new ArrayList<>(images);
        images.clear();
        images.addAll(newImages);
        support.firePropertyChange("images", oldImages, newImages);
    }

    /**
     * Retrieves the current error message.
     * @return the current error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message and notifies listeners of the change.
     * @param newErrorMessage the new error message
     */
    public void setErrorMessage(String newErrorMessage) {
        // Store the previous error message
        String oldMessage = errorMessage;
        errorMessage = newErrorMessage;
        support.firePropertyChange("errorMessage", oldMessage, newErrorMessage);
    }
}
