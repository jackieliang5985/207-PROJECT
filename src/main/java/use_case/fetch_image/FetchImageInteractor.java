package use_case.fetch_image;

import entity.CommonImage;
import interface_adapter.image.FetchImageRepository;
import interface_adapter.image.FetchImagePresenter;

import java.util.List;

public class FetchImageInteractor implements FetchImageInputBoundary {
    private final FetchImageRepository fetchImageRepository;  // This is an abstraction (interface) for data access
    private final FetchImagePresenter fetchImagePresenter;

    public FetchImageInteractor(FetchImageRepository fetchImageRepository, FetchImagePresenter fetchImagePresenter) {
        this.fetchImageRepository = fetchImageRepository;
        this.fetchImagePresenter = fetchImagePresenter;
    }

    @Override
    public List<CommonImage> searchImages(FetchImageInputData inputData) throws Exception {
        String query = inputData.getQuery();
        // Simulate an error for a specific query, for example, "error"
        if ("error".equals(query)) {
            FetchImageOutputData outputData = new FetchImageOutputData(null, "Error fetching images");
            fetchImagePresenter.presentError(outputData);  // Pass the error message to the presenter
            throw new RuntimeException("Error fetching images");
        }

        // Otherwise, fetch images normally
        List<CommonImage> commonImages = fetchImageRepository.fetchImages(query);

        // If no images are found, present an error
        if (commonImages.isEmpty()) {
            FetchImageOutputData outputData = new FetchImageOutputData(null, "Invalid Search: No results found.");
            fetchImagePresenter.presentError(outputData);  // Pass the error message to the presenter
        } else {
            FetchImageOutputData outputData = new FetchImageOutputData(commonImages, null);
            fetchImagePresenter.presentImages(outputData);  // Pass the images to the presenter
        }

        return commonImages;
    }
}
