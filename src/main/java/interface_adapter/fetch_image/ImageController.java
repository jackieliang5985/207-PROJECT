package interface_adapter.fetch_image;

import use_case.image.ImageInteractor;

public class ImageController {
    private final ImageInteractor imageInteractor;

    public ImageController(ImageInteractor imageInteractor) {
        this.imageInteractor = imageInteractor;
    }

    public void fetchImages(String query) {
        try {
            // Pass the query to the interactor; errors are handled within the interactor
            imageInteractor.searchImages(query);
        }
        catch (Exception e) {
            // If the interactor throws an exception, it's already presented via the presenter.
            // But if you want to display additional error handling here, you can.
        }
    }
}
