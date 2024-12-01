package use_case.image;

import entity.CommonImage;
import java.util.List;

public class ImageOutputData implements ImageOutputBoundary {
    private List<CommonImage> images;
    private String errorMessage;

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

    // Implement the methods of ImageOutputBoundary
    @Override
    public void presentImages(List<CommonImage> images) {
        this.images = images;
    }

    @Override
    public void presentError(String errorMessage) {
        this.errorMessage = errorMessage;  // This works now since `errorMessage` is not final
    }
}
