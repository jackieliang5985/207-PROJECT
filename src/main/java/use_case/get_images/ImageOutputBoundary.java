package use_case.get_images;

import entity.CommonImage;
import java.util.List;

public interface ImageOutputBoundary {
    void presentImages(List<CommonImage> images);
    void presentError(String errorMessage);
}

