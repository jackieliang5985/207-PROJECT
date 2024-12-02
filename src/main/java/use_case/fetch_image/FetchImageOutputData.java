package use_case.fetch_image;

import entity.CommonImage;
import java.util.List;

/**
 * Represents the output data for the image search use case.
 * Implements the {@link FetchImageOutputBoundary} to manage image results or error messages.
 */
public class FetchImageOutputData implements FetchImageOutputBoundary {

    private List<CommonImage> images; // List of images retrieved from the search
    private String errorMessage;     // Error message, if any, during the search process

    /**
     * Constructs an instance of ImageOutputData.
     *
     * @param images       the list of {@link CommonImage} objects representing search results.
     * @param errorMessage the error message, if any, encountered during the search.
     */
    public FetchImageOutputData(List<CommonImage> images, String errorMessage) {
        this.images = images;
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the list of images retrieved from the search.
     *
     * @return a list of {@link CommonImage} objects.
     */
    public List<CommonImage> getImages() {
        return images;
    }

    /**
     * Gets the error message, if any, from the search process.
     *
     * @return a string describing the error.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Updates the list of images for successful image retrieval.
     *
     * @param images a list of {@link CommonImage} objects representing the search results.
     */
    @Override
    public void presentImages(List<CommonImage> images) {
        this.images = images;
    }

    /**
     * Updates the error message in case of a search failure.
     *
     * @param errorMessage a string describing the error encountered.
     */
    @Override
    public void presentError(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
