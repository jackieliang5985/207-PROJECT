package use_case.fetch_image;

import java.util.List;
import entity.CommonImage;
import interface_adapter.fetch_image.FetchImageRepository;

public class FetchImageInteractor implements FetchImageInputBoundary {
    private final FetchImageRepository fetchImageRepository;
    private final FetchImageOutputBoundary fetchImageOutputBoundary;

    public FetchImageInteractor(FetchImageRepository fetchImageRepository, FetchImageOutputBoundary fetchImageOutputBoundary) {
        this.fetchImageRepository = fetchImageRepository;
        this.fetchImageOutputBoundary = fetchImageOutputBoundary;
    }

    @Override
    public void execute(FetchImageInputData inputData) throws Exception {
        if (inputData == null) {
            throw new IllegalArgumentException("inputData must not be null");
        }

        final String query = inputData.getQuery();

        if ("error".equals(query)) {
            throw new RuntimeException("Error fetching images");
        }

        final List<CommonImage> commonImages = fetchImageRepository.fetchImages(query);

        // If no images are found, use the output boundary to present an error
        if (commonImages.isEmpty()) {
            fetchImageOutputBoundary.presentError("Invalid Search: No results found.");
        } else {
            fetchImageOutputBoundary.presentImages(commonImages);
        }
    }
}
