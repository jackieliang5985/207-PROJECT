package use_case.image;

import entity.CommonImage;
import interface_adapter.image.ImageRepository;
import interface_adapter.image.ImagePresenter;

import java.util.List;

public class ImageInteractor implements ImageInputBoundary {
    private final ImageRepository imageRepository;  // This is an abstraction (interface) for data access
    private final ImagePresenter imagePresenter;

    public ImageInteractor(ImageRepository imageRepository, ImagePresenter imagePresenter) {
        this.imageRepository = imageRepository;
        this.imagePresenter = imagePresenter;
    }

    @Override
    public List<CommonImage> searchImages(String query) throws Exception {
        // Simulate an error for a specific query, for example, "error"
        if ("error".equals(query)) {
            ImageOutputData outputData = new ImageOutputData(null, "Error fetching images");
            imagePresenter.presentError(outputData);  // Pass the error message to the presenter
            throw new RuntimeException("Error fetching images");
        }

        // Otherwise, fetch images normally
        List<CommonImage> commonImages = imageRepository.fetchImages(query);

        // If no images are found, present an error
        if (commonImages.isEmpty()) {
            ImageOutputData outputData = new ImageOutputData(null, "Invalid Search: No results found.");
            imagePresenter.presentError(outputData);  // Pass the error message to the presenter
        } else {
            ImageOutputData outputData = new ImageOutputData(commonImages, null);
            imagePresenter.presentImages(outputData);  // Pass the images to the presenter
        }

        return commonImages;
    }
}
