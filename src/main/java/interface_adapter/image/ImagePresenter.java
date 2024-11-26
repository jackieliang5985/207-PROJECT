package interface_adapter.image;

import data_access.SimpleImage;
import use_case.image.ImageOutputBoundary;
import entity.CommonImage;

import java.util.List;
import java.util.ArrayList;

public class ImagePresenter implements ImageOutputBoundary {
    private final ImageViewModel imageViewModel;

    public ImagePresenter(ImageViewModel imageViewModel) {
        this.imageViewModel = imageViewModel;
    }

    @Override
    public void presentImages(List<CommonImage> commonImages) {
        // Simply pass the images to the view model
        imageViewModel.setImages(commonImages);
    }

    @Override
    public void presentError(String errorMessage) {
        imageViewModel.setErrorMessage(errorMessage);
    }
}
