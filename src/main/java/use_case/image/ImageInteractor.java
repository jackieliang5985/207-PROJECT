package use_case.image;

import entity.CommonImage;
import java.util.List;

public class ImageInteractor {
    private final ImageInputBoundary imageInputBoundary;
    private final ImageOutputBoundary outputBoundary;

    public ImageInteractor(ImageInputBoundary imageInputBoundary, ImageOutputBoundary outputBoundary) {
        this.imageInputBoundary = imageInputBoundary;
        this.outputBoundary = outputBoundary;
    }

    public void fetchImages(ImageInputData inputData) {
        try {
            final List<CommonImage> images = imageInputBoundary.searchImages(inputData.getQuery());
            // Send images to presenter
            outputBoundary.presentImages(images);
        } catch (Exception e) {

            // Send error to presenter
            outputBoundary.presentError(e.getMessage());
        }
    }
}
