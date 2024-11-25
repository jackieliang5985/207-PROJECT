package use_case.image;

import java.util.List;

import entity.CommonImage;

public class FetchImagesUseCase {
    private final ImageInputBoundary imageInputBoundary;

    public FetchImagesUseCase(ImageInputBoundary imageInputBoundary) {
        this.imageInputBoundary = imageInputBoundary;
    }

    public List<CommonImage> fetchImages(String query) throws Exception {
        return imageInputBoundary.searchImages(query);
    }
}
