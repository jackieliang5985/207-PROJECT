package interface_adapter.image;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel class that represents the image data to be displayed.
 * It maintains the list of images and error messages, and supports property change listeners.
 */
public class FetchImageViewModel {

    // PropertyChangeSupport for handling property change events
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // List of ImageDisplayData objects representing the images to be displayed
    private final List<ImageDisplayData> images = new ArrayList<>();

    // Error message to be displayed, if any
    private String errorMessage;

    /**
     * Gets the list of images to be displayed.
     *
     * @return a list of {@link ImageDisplayData} objects representing the images.
     */
    public List<ImageDisplayData> getImages() {
        return images;
    }

    /**
     * Sets the list of images to be displayed and fires a property change event.
     *
     * @param images the new list of {@link ImageDisplayData} objects representing the images.
     */
    public void setImages(List<ImageDisplayData> images) {
        List<ImageDisplayData> oldImages = new ArrayList<>(this.images);
        this.images.clear();
        this.images.addAll(images);
        // Fire property change event
        support.firePropertyChange("images", oldImages, images);
    }

    /**
     * Gets the current error message.
     *
     * @return the error message if any.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message and fires a property change event.
     *
     * @param errorMessage the error message to be set.
     */
    public void setErrorMessage(String errorMessage) {
        String oldErrorMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        // Fire property change event
        support.firePropertyChange("errorMessage", oldErrorMessage, errorMessage);
    }

    /**
     * Adds a listener to receive property change notifications.
     *
     * @param listener the listener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     *
     * @param listener the listener to be removed.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Inner class representing image data to be displayed in the UI.
     * Contains the image URL and description.
     */
    public static class ImageDisplayData {
        private final String url;
        private final String description;

        /**
         * Constructor for the ImageDisplayData.
         *
         * @param url the URL of the image.
         * @param description the description of the image.
         */
        public ImageDisplayData(String url, String description) {
            this.url = url;
            this.description = description;
        }

        /**
         * Gets the URL of the image.
         *
         * @return the image URL.
         */
        public String getUrl() {
            return url;
        }

        /**
         * Gets the description of the image.
         *
         * @return the image description.
         */
        public String getDescription() {
            return description;
        }
    }
}
