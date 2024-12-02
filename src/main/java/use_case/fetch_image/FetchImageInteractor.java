package use_case.fetch_image;

import java.util.List;

import entity.CommonImage;
import interface_adapter.image.FetchImagePresenter;
import interface_adapter.image.FetchImageRepository;

public class FetchImageInteractor implements FetchImageInputBoundary {
    private final FetchImageRepository fetchImageRepository;
    private final FetchImagePresenter fetchImagePresenter;

    public FetchImageInteractor(FetchImageRepository fetchImageRepository, FetchImagePresenter fetchImagePresenter) {
        this.fetchImageRepository = fetchImageRepository;
        this.fetchImagePresenter = fetchImagePresenter;
    }

    @Override
    public List<CommonImage> execute(FetchImageInputData inputData) throws Exception {
        final String query = inputData.getQuery();

        if ("error".equals(inputData.getQuery())) {
            throw new RuntimeException("Error fetching images");
        }

        final List<CommonImage> commonImages = fetchImageRepository.fetchImages(query);

        // If no images are found, present an error
        if (commonImages.isEmpty()) {
            final FetchImageOutputData outputData = new FetchImageOutputData(null, "Invalid Search: No results found.");
            fetchImagePresenter.presentError(outputData);
        }
        else {
            final FetchImageOutputData outputData = new FetchImageOutputData(commonImages, null);
            fetchImagePresenter.presentImages(outputData);
        }

        return commonImages;
    }
}
