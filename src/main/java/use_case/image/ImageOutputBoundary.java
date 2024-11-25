package use_case.image;

import entity.CommonImage;
import java.util.List;

public interface ImageOutputBoundary {
    void presentImages(List<CommonImage> images);
    void presentError(String errorMessage);
}

