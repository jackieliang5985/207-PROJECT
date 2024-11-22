package use_case.image;

import entity.CommonImage;
import java.util.List;

public class ImageOutputData {
    private final List<CommonImage> images;
    private final String errorMessage;

    public ImageOutputData(List<CommonImage> images, String errorMessage) {
        this.images = images;
        this.errorMessage = errorMessage;
    }

    public List<CommonImage> getImages() {
        return images;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
