package use_case.image;
import entity.Image;

import java.util.List;


public class FetchImagesUseCase {
    private final ImageRepository imageRepository;

    public FetchImagesUseCase(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> fetchImages(String query) throws Exception {
        return imageRepository.searchImages(query);
    }
}
