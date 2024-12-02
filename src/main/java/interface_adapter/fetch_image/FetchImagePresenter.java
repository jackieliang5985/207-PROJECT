package interface_adapter.fetch_image;

import entity.CommonImage;
import use_case.fetch_image.FetchImageOutputBoundary;
import use_case.fetch_image.FetchImageOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * The presenter responsible for formatting and passing image data or error messages to the view model.
 */
public class FetchImagePresenter implements FetchImageOutputBoundary {

    private final FetchImageViewModel fetchImageViewModel; // The view model to update with image or error data.

    /**
     * Constructs an ImagePresenter with the given view model.
     *
     * @param fetchImageViewModel the {@link FetchImageViewModel} to update with the processed data.
     */
    public FetchImagePresenter(FetchImageViewModel fetchImageViewModel) {
        this.fetchImageViewModel = fetchImageViewModel;
    }

    /**
     * Processes and passes a list of images to the view model for display.
     *
     * @param outputData the {@link FetchImageOutputData} containing the images to display.
     */
    @Override
    public void presentImages(List<CommonImage> images) {
        if (images != null && !images.isEmpty()) {
            List<FetchImageViewModel.ImageDisplayData> displayData = new ArrayList<>();
            for (CommonImage commonImage : images) {
                FetchImageViewModel.ImageDisplayData data = new FetchImageViewModel.ImageDisplayData(
                        commonImage.getUrl(),
                        commonImage.getDescription()
                );
                displayData.add(data);
            }
            fetchImageViewModel.setImages(displayData); // Update the view model with the image data.
        } else {
            fetchImageViewModel.setErrorMessage("No images found for the query.");
        }
    }

    @Override
    public void presentError(String errorMessage) {
        // Use the provided error message, avoiding redeclaration
        fetchImageViewModel.setErrorMessage(errorMessage != null ? errorMessage :
                "An unexpected error occurred while fetching images."); // Update the view model with the error message.
    }

    /**
     * Retrieves the current error message from the view model.
     *
     * @return the error message currently stored in the view model.
     */
    public String getErrorMessage() {
        return fetchImageViewModel.getErrorMessage();
    }
}
