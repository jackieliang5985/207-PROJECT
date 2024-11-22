package use_case.image;

import entity.Image;
import java.util.List;

public interface ImageRepository {
    List<Image> searchImages(String query) throws Exception;
}
