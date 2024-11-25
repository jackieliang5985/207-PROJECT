package entity;

/**
 * Represents an individual image post-it note on the panel.
 */
public class ImagePostNoteEntity extends PostNoteEntity {
    private String imageUrl;

    public ImagePostNoteEntity(int x, int y, MindMap mindMap, String imageUrl) {
        super(x, y, 0, 0, mindMap);
        this.imageUrl = imageUrl; // Start with 0 width and height, we'll set those later
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
