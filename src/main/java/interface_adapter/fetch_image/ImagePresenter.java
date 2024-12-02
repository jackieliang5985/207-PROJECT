package interface_adapter.fetch_image;

import entity.CommonImage;
import use_case.image.ImageOutputData;

import java.util.ArrayList;
import java.util.List;

public class ImagePresenter {

    private final ImageViewModel imageViewModel;

    public ImagePresenter(ImageViewModel imageViewModel) {
        this.imageViewModel = imageViewModel;
    }

    // This method displays images
    public void presentImages(ImageOutputData outputData) {
        if (outputData.getImages() != null) {
            List<ImageViewModel.ImageDisplayData> displayData = new ArrayList<>();
            for (CommonImage commonImage : outputData.getImages()) {
                ImageViewModel.ImageDisplayData data = new ImageViewModel.ImageDisplayData(commonImage.getUrl(), commonImage.getDescription());
                displayData.add(data);
            }
            imageViewModel.setImages(displayData);
        }
    }

    // This method displays error messages
    public void presentError(ImageOutputData outputData) {
        imageViewModel.setErrorMessage(outputData.getErrorMessage());
    }

    public String getErrorMessage() {
        return imageViewModel.getErrorMessage();
    }
}
