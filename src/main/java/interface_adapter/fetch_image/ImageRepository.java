package interface_adapter.fetch_image;

import entity.CommonImage;  // Use CommonImage instead of SimpleImage
import java.util.List;

public interface ImageRepository {
    List<CommonImage> fetchImages(String query) throws Exception;  // Change return type to List<CommonImage>
}
