package interface_adapter.fetch_image;

import use_case.fetch_image.FetchImageInputBoundary;
import use_case.fetch_image.FetchImageInputData;
import use_case.fetch_image.FetchImageInteractor;

/**
 * The controller for handling image-related actions.
 * Bridges user input (e.g., search queries) and the interactor to process business logic.
 */
public class FetchImageController {

    private final FetchImageInputBoundary imageInputBoundary;

    /**
     * Constructs an ImageController with the given interactor.
     *
     * @param imageInteractor the {@link FetchImageInteractor} that handles image-related operations.
     */
    public FetchImageController(FetchImageInteractor imageInputBoundary) {
        this.imageInputBoundary = imageInputBoundary;
    }

    /**
     * Fetches images based on a given query.
     *
     * @param query the search query string entered by the user.
     */
    public void fetchImages(String query) {
        try {
            FetchImageInputData inputData = new FetchImageInputData(query);
            imageInputBoundary.execute(inputData);
        } catch (Exception e) {
            // Honestly, presenter deals with this
        }
    }
}

