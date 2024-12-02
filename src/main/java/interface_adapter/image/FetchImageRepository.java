package interface_adapter.image;

import entity.CommonImage;
import java.util.List;

/**
 * The interface that defines the contract for fetching images from a data source.
 * Implementing classes should provide the mechanism to fetch images based on a search query.
 */
public interface FetchImageRepository {

    /**
     * Fetches a list of images from the data source based on the provided query.
     *
     * @param query the search query to filter images.
     * @return a list of {@link CommonImage} objects that match the search query.
     * @throws Exception if an error occurs during the image retrieval process.
     */
    List<CommonImage> fetchImages(String query) throws Exception;
}
