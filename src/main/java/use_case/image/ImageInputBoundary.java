package use_case.image;

import entity.CommonImage;
import java.util.List;

public interface ImageInputBoundary {
    List<CommonImage> searchImages(String query) throws Exception;
}
