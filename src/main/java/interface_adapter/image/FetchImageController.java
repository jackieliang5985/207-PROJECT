package interface_adapter.image;

import use_case.fetch_image.FetchImageInputData;
import use_case.fetch_image.FetchImageInteractor;

/**
 * The controller for handling image-related actions.
 * Bridges user input (e.g., search queries) and the interactor to process business logic.
 */
public class FetchImageController {

    private final FetchImageInteractor imageInteractor;

    /**
     * Constructs an ImageController with the given interactor.
     *
     * @param imageInteractor the {@link FetchImageInteractor} that handles image-related operations.
     */
    public FetchImageController(FetchImageInteractor imageInteractor) {
        this.imageInteractor = imageInteractor;
    }

    /**
     * Fetches images based on a given query.
     *
     * @param query the search query string entered by the user.
     */
    public void fetchImages(String query) {
        try {
            FetchImageInputData inputData = new FetchImageInputData(query);
            imageInteractor.searchImages(inputData);
        } catch (Exception e) {
            // Honestly, presenter deals with this
        }
    }
}

