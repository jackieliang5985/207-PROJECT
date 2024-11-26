package interface_adapter.image;

import use_case.image.ImageInteractor;
import use_case.image.ImageOutputBoundary;

public class ImageController {

    private final ImageInteractor imageInteractor;

    public ImageController(ImageInteractor imageInteractor) {
        this.imageInteractor = imageInteractor;
    }

    public void fetchImages(String query, ImageOutputBoundary outputBoundary) {
        try {
            // Call searchImages from ImageInteractor, which returns a List<SimpleImage>
            imageInteractor.searchImages(query);  // This will fetch and pass images to the presenter
        } catch (Exception e) {
            outputBoundary.presentError(e.getMessage());
        }
    }
}
