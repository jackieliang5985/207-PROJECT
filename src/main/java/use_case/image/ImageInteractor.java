package use_case.image;

import interface_adapter.image.ImageRepository;
import entity.CommonImage;  // Use CommonImage instead of SimpleImage
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
            imagePresenter.presentError("Error fetching images");
            throw new RuntimeException("Error fetching images");
        }

        // Otherwise, fetch images normally
        List<CommonImage> commonImages = imageRepository.fetchImages(query);

        // If no images are found, present an error
        if (commonImages.isEmpty()) {
            imagePresenter.presentError("Invalid Search: No results found.");
        } else {
            imagePresenter.presentImages(commonImages);
        }

        return commonImages;
    }
}
