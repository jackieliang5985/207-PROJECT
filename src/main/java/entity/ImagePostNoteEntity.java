package entity;

/**
 * Represents an individual image post-it note on the panel.
 */
public class ImagePostNoteEntity extends PostNoteEntity {
    final private Image image;

    public ImagePostNoteEntity(int x, int y, MindMapEntity mindMap, Image image) {
        super(x, y, 0, 0, mindMap);
        this.image = image;
    }

    public String getImageUrl() {
        return image.getUrl();
    }
}
