package interface_adapter.image;

import java.util.List;

import entity.CommonImage;
import use_case.image.ImageOutputBoundary;

/**
 * Presenter for the Image Fetching Use Case.
 * This class implements the output boundary for the use case.
 * It receives data (success or error) from the interactor and updates the view model accordingly.
 */
public class ImagePresenter implements ImageOutputBoundary {
    private final ImageViewModel imageViewModel;

    /**
     * Constructs an ImagePresenter with the specified view model.
     * @param imageViewModel the view model that holds image-related data and errors
     */
    public ImagePresenter(ImageViewModel imageViewModel) {
        this.imageViewModel = imageViewModel;
    }

    /**
     * Handles the presentation of fetched images.
     * Updates the view model with the list of images fetched by the interactor.
     * @param images the list of images retrieved from the use case interactor
     */
    @Override
    public void presentImages(List<CommonImage> images) {
        imageViewModel.setImages(images);
    }

    /**
     * Handles the presentation of an error.
     * Updates the view model with the error message in case the use case fails.
     * @param errorMessage the error message describing the failure
     */
    @Override
    public void presentError(String errorMessage) {
        imageViewModel.setErrorMessage(errorMessage);
    }
}
