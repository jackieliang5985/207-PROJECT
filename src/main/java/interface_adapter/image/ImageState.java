package interface_adapter.image;

import entity.Image;  // Interface representing an image entity
import java.util.List;

/**
 * Represents the state of the Image fetching process.
 * This class is used to store and manage the list of images and any associated error messages.
 * It acts as a model within the interface adapter layer to support the ImageViewModel.
 */
public class ImageState {

    // List of images fetched from the repository
    private List<Image> images;
    private String errorMessage;

    /**
     * Retrieves the current list of images.
     * @return the list of images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * Updates the list of images.
     * @param images the list of images to set
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * Retrieves the current error message.
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Updates the error message.
     * @param errorMessage the error message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
