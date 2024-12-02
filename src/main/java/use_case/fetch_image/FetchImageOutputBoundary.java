package use_case.fetch_image;

import entity.CommonImage;
import java.util.List;

/**
 * The output boundary interface for the image search use case.
 * Defines methods to present results or errors back to the user interface.
 */
public interface FetchImageOutputBoundary {

    /**
     * Presents a list of successfully retrieved images to the user interface.
     *
     * @param images a list of {@link CommonImage} objects representing the search results.
     */
    void presentImages(List<CommonImage> images);

    /**
     * Presents an error message to the user interface when the image search fails.
     *
     * @param errorMessage a string describing the error that occurred.
     */
    void presentError(String errorMessage);
}
