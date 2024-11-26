package interface_adapter.image;

import data_access.SimpleImage;
import java.util.List;

/**
 * Interface for fetching images from external sources like Unsplash API.
 */
public interface ImageRepository {
    List<SimpleImage> fetchImages(String query) throws Exception;
}
