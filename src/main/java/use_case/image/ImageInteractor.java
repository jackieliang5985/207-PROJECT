package use_case.image;

import interface_adapter.image.ImageRepository;
import data_access.SimpleImage;
import interface_adapter.image.ImagePresenter;
import entity.CommonImage;

import java.util.List;
import java.util.ArrayList;

public class ImageInteractor implements ImageInputBoundary {
    private final ImageRepository imageRepository;
    private final ImagePresenter imagePresenter;

    public ImageInteractor(ImageRepository imageRepository, ImagePresenter imagePresenter) {
        this.imageRepository = imageRepository;
        this.imagePresenter = imagePresenter;
    }

    @Override
    public List<CommonImage> searchImages(String query) throws Exception {
        // Fetch images using the repository (returns SimpleImage)
        List<SimpleImage> simpleImages = imageRepository.fetchImages(query);

        // Convert SimpleImage to CommonImage
        List<CommonImage> commonImages = new ArrayList<>();
        for (SimpleImage simpleImage : simpleImages) {
            // Assuming SimpleImage has url, description, and id
            CommonImage commonImage = new CommonImage(simpleImage.getUrl(), simpleImage.getDescription(), simpleImage.getId());
            commonImages.add(commonImage);  // Add converted image
        }

        // Pass the converted images (CommonImage) to the presenter
        imagePresenter.presentImages(commonImages);

        return commonImages;  // Return the list of CommonImage objects
    }
}
