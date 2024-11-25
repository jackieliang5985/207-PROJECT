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
            List<CommonImage> images = imageInputBoundary.searchImages(inputData.getQuery());
            outputBoundary.presentImages(images); // Send images to presenter
        } catch (Exception e) {
            outputBoundary.presentError(e.getMessage()); // Send error to presenter
        }
    }
}
