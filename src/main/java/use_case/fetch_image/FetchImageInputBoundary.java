package use_case.fetch_image;

import entity.CommonImage;
import java.util.List;

/**
 * Interface defining the input boundary for the image search use case.
 * This interface is part of the application layer and provides an abstraction
 * for searching images based on a given query encapsulated in {@link FetchImageInputData}.
 */
public interface FetchImageInputBoundary {

    /**
     * Searches for images based on the provided input data.
     *
     * @param inputData an {@link FetchImageInputData} object containing the query for the image search.
     * @return a list of {@link CommonImage} objects representing the search results.
     * @throws Exception if an error occurs during the search operation.
     */
    List<CommonImage> searchImages(FetchImageInputData inputData) throws Exception;
}
