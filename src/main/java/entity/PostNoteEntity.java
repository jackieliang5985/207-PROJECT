package entity;

/**
 * Represents an individual post-it note on the panel.
 */
public class PostNoteEntity {
    private int x;
    private int y;
    private int width;
    private int height;
    private MindMapEntity mindMap;

    public PostNoteEntity(int x, int y, int width, int height, MindMapEntity mindMap) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mindMap = mindMap;

    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MindMapEntity getMindMap() {
        return mindMap;
    }
}
