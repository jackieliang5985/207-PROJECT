package interface_adapter.fetch_image;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ImageViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private final List<ImageDisplayData> images = new ArrayList<>();
    private String errorMessage;

    public List<ImageDisplayData> getImages() {
        return images;
    }

    public void setImages(List<ImageDisplayData> images) {
        List<ImageDisplayData> oldImages = new ArrayList<>(this.images);
        this.images.clear();
        this.images.addAll(images);
        support.firePropertyChange("images", oldImages, images);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        String oldErrorMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldErrorMessage, errorMessage);
    }

    // Method to add a property change listener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Method to remove a property change listener
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // ImageDisplayData used for displaying the image in the UI
    public static class ImageDisplayData {
        private final String url;
        private final String description;

        public ImageDisplayData(String url, String description) {
            this.url = url;
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public String getDescription() {
            return description;
        }
    }
}
