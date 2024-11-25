package use_case.image;

import java.util.List;

import entity.CommonImage;

/**
 * An interface that defines the output boundary for presenting images or errors.
 * It acts as a communication point between the interactor and the presenter layer.
 */
public interface ImageOutputBoundary {

    /**
     * Presents a list of images to the output layer.
     *
     * @param images the list of {@link CommonImage} objects to be presented
     */
    void presentImages(List<CommonImage> images);

    /**
     * Presents an error message to the output layer.
     *
     * @param errorMessage the error message to be presented
     */
    void presentError(String errorMessage);
}
