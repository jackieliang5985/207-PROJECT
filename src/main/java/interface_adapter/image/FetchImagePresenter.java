package interface_adapter.image;

import entity.CommonImage;
import use_case.fetch_image.FetchImageOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * The presenter responsible for formatting and passing image data or error messages to the view model.
 */
public class FetchImagePresenter {

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
    public void presentImages(FetchImageOutputData outputData) {
        if (outputData.getImages() != null) {
            List<FetchImageViewModel.ImageDisplayData> displayData = new ArrayList<>();
            for (CommonImage commonImage : outputData.getImages()) {
                FetchImageViewModel.ImageDisplayData data = new FetchImageViewModel.ImageDisplayData(
                        commonImage.getUrl(),
                        commonImage.getDescription()
                );
                displayData.add(data);
            }
            fetchImageViewModel.setImages(displayData); // Update the view model with the image data.
        }
    }

    /**
     * Passes an error message to the view model for display.
     *
     * @param outputData the {@link FetchImageOutputData} containing the error message to display.
     */
    public void presentError(FetchImageOutputData outputData) {
        fetchImageViewModel.setErrorMessage(outputData.getErrorMessage()); // Update the view model with the error message.
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
