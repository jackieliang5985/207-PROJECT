package interface_adapter.get_images;

import use_case.get_images.ImageInteractor;
import use_case.get_images.ImageInputData;
import use_case.get_images.ImageOutputBoundary;

/**
 * Controller for the Image Fetching Use Case.
 * This controller bridges the gap between the UI and the image-fetching use case interactor.
 * It processes user inputs (e.g., search queries) and invokes the interactor.
 */
public class ImageController {

    // The interactor that handles the business logic for image fetching.
    private final ImageInteractor imageInteractor;

    /**
     * Constructs an ImageController with the specified interactor.
     * @param imageInteractor the interactor responsible for image fetching logic
     */
    public ImageController(ImageInteractor imageInteractor) {
        this.imageInteractor = imageInteractor;
    }

    /**
     * Fetches images based on a user-provided query.
     * Translates the query into input data and invokes the interactor.
     * @param query the search query for fetching images
     * @param outputBoundary the presenter/output boundary for handling fetched images or errors
     */
    public void fetchImages(String query, ImageOutputBoundary outputBoundary) {
        ImageInputData inputData = new ImageInputData(query);
        imageInteractor.fetchImages(inputData);

        // Note: The interactor directly communicates with the presenter (output boundary).
        // The output boundary will handle presenting the images or errors to the view.
    }
}
