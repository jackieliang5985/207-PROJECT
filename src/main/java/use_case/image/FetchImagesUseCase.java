package use_case.image;
import entity.CommonImage;

import java.util.List;


public class FetchImagesUseCase {
    private final ImageInputBoundary imageInputBoundary;

    public FetchImagesUseCase(ImageInputBoundary imageInputBoundary) {
        this.imageInputBoundary = imageInputBoundary;
    }

    public List<CommonImage> fetchImages(String query) throws Exception {
        return imageInputBoundary.searchImages(query);
    }
}
