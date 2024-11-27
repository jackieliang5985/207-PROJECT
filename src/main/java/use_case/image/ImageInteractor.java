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
        // Fetch images using the repository (now returns CommonImage)
        List<CommonImage> commonImages = imageRepository.fetchImages(query);

        // If no images are found, show an error message
        if (commonImages.isEmpty()) {
            imagePresenter.presentError("Invalid Search: No results found.");
        } else {
            // Pass the CommonImage objects to the presenter
            imagePresenter.presentImages(commonImages);
        }

        return commonImages;  // Return the CommonImage list (this could be empty)
    }
}
