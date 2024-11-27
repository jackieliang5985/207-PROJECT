package interface_adapter.image;

import entity.CommonImage;
import use_case.image.ImageOutputBoundary;

import java.util.List;
import java.util.ArrayList;

public class ImagePresenter implements ImageOutputBoundary {
    private final ImageViewModel imageViewModel;

    public ImagePresenter(ImageViewModel imageViewModel) {
        this.imageViewModel = imageViewModel;
    }

    @Override
    public void presentImages(List<CommonImage> images) {
        // Map CommonImage to ImageDisplayData (view-friendly data)
        List<ImageViewModel.ImageDisplayData> displayData = new ArrayList<>();
        for (CommonImage commonImage : images) {
            ImageViewModel.ImageDisplayData data = new ImageViewModel.ImageDisplayData(commonImage.getUrl(), commonImage.getDescription());
            displayData.add(data);
        }

        // Pass the display data to the view model
        imageViewModel.setImages(displayData);
    }

    @Override
    public void presentError(String errorMessage) {
        imageViewModel.setErrorMessage(errorMessage);
    }

    public String getErrorMessage() {
        return imageViewModel.getErrorMessage();
    }
}
