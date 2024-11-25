package use_case.get_images;

import entity.CommonImage;
import java.util.List;

public interface ImageDataAccessInterface {
    List<CommonImage> fetchImages(String query) throws Exception;
}
